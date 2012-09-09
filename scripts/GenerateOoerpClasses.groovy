/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Gant script that generate all classes of Open Object (OpenErp). 
 * The Script conect to OpenERP server using XML-RPC and obtain the
 * meta info about the classes of server. With this info create all 
 * classes in the ooerp dir.
 * 
 * @author Oscar Konno Sampaio (oscarks@gmail.com)
 *
 * @since 0.1
 */
import grails.util.GrailsNameUtils
import org.springframework.core.io.FileSystemResource

grailsHome = Ant.project.properties."environment.GRAILS_HOME"
includeTargets << grailsScript('_GrailsBootstrap')
includeTargets << grailsScript("_GrailsInit")
includeTargets << grailsScript("_GrailsCreateArtifacts")
includeTargets << grailsScript("_GrailsPackage")
includeTargets << new File ( "${grailsHome}/scripts/Package.groovy" )

target('default': "Generate Open Objects Model Classes") {
    	depends checkVersion, configureProxy, bootstrap 
	event("StatusUpdate", ["Preparing to create classes..."])
	generate()
}

target(generate:'generate Open Objects ERP') {
	def args=getParameters()

	def server=args.server
	def port=args.port
	def database=args.database
	def user=args.user
	def password=args.password
	def oocClass=classLoader.loadClass("grails.plugins.ooog.OpenObjectConnector")
	def ooc=oocClass.newInstance()
	println "Server  : ${server}:${port}"
	println "Database: ${database}"
	println "User    : ${user}/${password.replaceAll('.','*')}"

	ooc.server=server
	ooc.port=port
	ooc.database=database
	event('StatusUpdate', ["Login in ${ooc.server}..."])
	ooc.login(user,password)
	event('StatusUpdate', ["Getting models..."])
	def models=ooc.getModelObjects()

	def map=[:]
	models.each { 
		map[it.model]=ooc.modelKeyToClassName(it.model)
	}
	generateOOERPAtifacts(map)
}

def generateOOERPAtifacts(model) {
	def n=model.size()
	def artifactPath = "grails-app/ooerp"
	event('StatusUpdate', ["Removing old version of Open Objects classes, if exists ..."])
	ant.delete(dir:"${basedir}/${artifactPath}")
	event('StatusUpdate', ["Creating diretory ${artifactPath} ..."])
	ant.mkdir(dir: "${basedir}/${artifactPath}")
	event('StatusUpdate', ["Generating OO Model groovy class (${n} classes) ..."])
	model.each { k,v->
		event('StatusUpdate', ["Creating ${v} [${k}] ..."])
		createOOERPArtifact(k,v)
	}
	event('StatusUpdate', ["Generated ${n} classes in grails-app/ooerp dir !"])
}
def createOOERPArtifact(key,className) {
	def type = "Ooerp"
	def artifactPath = "grails-app/ooerp"
	def artifactFile = "${basedir}/${artifactPath}/${className}.groovy"

	if (new File(artifactFile).exists()) {
		if (!confirmInput("${type} ${className}.groovy already exists. Overwrite?","${className}.overwrite")) {
		    return
		}
	}
	def templatePath = 'templates/artifacts'
 	templateFile = new FileSystemResource("${basedir}/src/${templatePath}/${type}.groovy")
	if (!templateFile.exists()) {
		// now check for template provided by plugins
		def pluginTemplateFiles = resolveResources("file:${pluginsHome}/*/src/${templatePath}/${type}.groovy")
		if (pluginTemplateFiles) {
		    templateFile = pluginTemplateFiles[0]
		} else {
		    // template not found in application, use default template
		    templateFile = grailsResource("src/grails/${templatePath}/${type}.groovy")
		}
	}
	copyGrailsResource(artifactFile, templateFile)

	ant.replace(file: artifactFile,token: "@artifact.name@", value: "${className}")
	ant.replace(file: artifactFile,token: "@artifact.key@", value: "${key}")
	def pkg
	if (pkg) {
		ant.replace(file: artifactFile, token: "@artifact.package@", value: "package ${pkg}\n\n")
	} else {
		ant.replace(file: artifactFile, token: "@artifact.package@", value: "")
	}
	event("CreatedFile", [artifactFile])
	event("CreatedArtefact", [ artifactFile, className])

}

def getParameters() {
	def server
	def port
	def database
	def user
	def password

	def p1=/(.+)@(.+):(\d+)\/(.+)/
	def p2=/(.+)@(.+)\/(.+)/

	def s=args
	def m
	if (s ==~ p1) {
		m= (s=~p1)
		user=m[0][1]
		server=m[0][2]
		port=m[0][3].toInteger()
		database=m[0][4]
	} else if (s==~p2) {
		m= (s=~p2)
		user=m[0][1]
		server=m[0][2]
		database=m[0][3]
	}
	
	Ant.input(message: "Password:", addproperty:"password") 

        password = Ant.antProject.properties."password" 
    
	if (!server) server=grailsApp.config.grails.plugins.ooog.openerp.server
	if (!port) port=grailsApp.config.grails.plugins.ooog.openerp.port ?: 8069
	if (!database) database=grailsApp.config.grails.plugins.ooog.openerp.database
	if (!user) user=grailsApp.config.grails.plugins.ooog.openerp.user ?: 'admin'
	if (!password) password=grailsApp.config.grails.plugins.ooog.openerp.password

	if (!server || !database || !user || !password) {
		println """USAGE:
	
	generate-ooerp-classes user@server:port/database
	or
	generate-ooerp-classes user@server/database
	

Or configure the user, password, server, port and database in Config.groovy before run this script. Add the following keys: 

grails.plugins.ooog.openerp.server=<host where are running the server>
grails.plugins.ooog.openerp.port=<port where the server is listening. If not infomed is used the default (8069)>
grails.plugins.ooog.openerp.database=<database name>
grails.plugins.ooog.openerp.user=<user name>
grails.plugins.ooog.openerp.password=<password>
"""
		event('StatusError', ["Script aborted. Params not informed!"])
		System.exit(-1)
	}
	def params=[:]
	if (server) params.server=server
	if (user) params.user=user
	if (port) params.port=port
	if (database) params.database=database
	if (password) params.password=password
	params
}

setDefaultTarget("default")

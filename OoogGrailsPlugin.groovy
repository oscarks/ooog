/*
 * Copyright (c) 2012 the original author or authors.
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
import grails.plugins.ooog.annotations.OpenObject
import grails.plugins.ooog.OpenObjectConnector
import grails.plugins.ooog.OpenObjectErpClassArtefactHandler
import org.codehaus.groovy.grails.commons.GrailsDomainClass
import org.codehaus.groovy.grails.validation.GrailsDomainClassValidator
import org.springframework.beans.factory.config.MethodInvokingFactoryBean
import org.springframework.context.ApplicationContext
/**
 * Ooog Plugin main class
 * @author oscar
 *
 */
class OoogGrailsPlugin {
    // the plugin version
    def version = "0.2"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.0 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
      "grails-app/views/error.gsp",
      "grails-app/controllers/**", // they exist only for testing purposes
      "grails-app/conf/Config.groovy",
      "grails-app/ooerp/**",
    ]
    def loadAfter = ['core', 'controllers', 'domainClass']
    // TODO Fill in these fields
    def title = "Open Object On Grails Plugin" // Headline display name of the plugin
    def author = "Oscar Konno Sampaio"
    def authorEmail = "oscarks@gmail.com"
    def description = '''\
Implements access to OpenERP Open Objects like Gorm Domain objects in Grails
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/ooog"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
    def organization = [ name: "Acception technologies", url: "http://www.acception.com.br/" ]

    // Any additional developers beyond the author specified above.
    def developers = [  [ name: "Oscar Konno Sampaio", email: "oscarks@gmail.com" ],
						[ name: "Luiz Leao", email:"luiz_leao@yahoo.com"]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.grails-plugins.codehaus.org/browse/grails-plugins/" ]


	def artefacts = [grails.plugins.ooog.OpenObjectErpClassArtefactHandler]

	def watchedResources = [
	  'file:./grails-app/ooerp/**',
	]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
		println "Registering OpenObjectConnector in Spring... "
		openObjectConnector(OpenObjectConnector) { bean ->
			bean.autowire = 'constructor'
		}
		println "Registering open objects in Spring... "
		application.OpenObjectErpClasses.each { GrailsDomainClass dc ->
			
			"${dc.fullName}"(dc.clazz) { bean ->
				bean.singleton = false
				bean.autowire = "byName"
			}
			"${dc.fullName}DomainClass"(MethodInvokingFactoryBean) { bean ->
				targetObject = ref("grailsApplication", true)
				targetMethod = "getArtefact"
				bean.lazyInit = true
				arguments = [OpenObjectErpClassArtefactHandler.TYPE, dc.fullName]
			}
			"${dc.fullName}PersistentClass"(MethodInvokingFactoryBean) { bean ->
				targetObject = ref("${dc.fullName}DomainClass")
				bean.lazyInit = true
				targetMethod = "getClazz"
			}
			"${dc.fullName}Validator"(GrailsDomainClassValidator) { bean ->
				messageSource = ref("messageSource")
				bean.lazyInit = true
				domainClass = ref("${dc.fullName}DomainClass")
				grailsApplication = ref("grailsApplication", true)
			}
		}
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}

import org.codehaus.groovy.grails.commons.DomainClassArtefactHandler

includeTargets << grailsScript("_GrailsClean")

def ooogAstPath = "${ooogPluginDir}/src/groovy/grails/plugins/ooog/ast"
def ooogAstDest = "${projectWorkDir}/ast/ooog"
def ooogAnnotationPath="${ooogPluginDir}/src/java/grails/plugins/ooog/annotations"

println "${ooogAstPath},${ooogAnnotationPath} => ${ooogAstDest} "

eventCleanStart = {
		ant.delete(dir:ooogAstDest)
}

eventCompileStart = {
		ant.mkdir(dir:"${ooogAstDest}/META-INF")
		ant.groovyc(destdir: ooogAstDest, encoding: "UTF-8") {
						src(path: ooogAstPath)
                        src(path: ooogAnnotationPath)
                        javac() // to compile java classes using the javac compiler
		}

		ant.copy(todir:"${ooogAstDest}/META-INF") {
						fileset dir:"${ooogAstPath}/META-INF"
		}

		grailsSettings.compileDependencies << new File(ooogAstDest)
		classpathSet=false
		classpath()
}


/**
 * Make generate-* work
 */
def doinGenerateAction = false
def tweakForGenerate = {
  doinGenerateAction = true
}
// use following two events to mark that we are currently generating
eventUberGenerateStart = tweakForGenerate
eventGenerateForOneStart = tweakForGenerate

/**
 * register artefacts as Domains here, so that we are modifying the same
 * grailsApp that is used to find domainClasses for generation
 */
eventConfigureAppEnd = {
  if (doinGenerateAction) {
	grailsApp.OpenObjectErpClasses.each {
	  grailsApp.addArtefact(DomainClassArtefactHandler.TYPE, it)
	}
  }
}
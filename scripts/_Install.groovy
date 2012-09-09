//
// This script is executed by Grails after plugin was installed to project.
// This script is a Gant script so you can use all special variables provided
// by Gant (such as 'baseDir' which points on project base dir). You can
// use 'ant' to access a global instance of AntBuilder
//
// For example you can create directory under project tree:
//
//    ant.mkdir(dir:"${basedir}/grails-app/jobs")
//

/**
 * Gant script that creates Open Objects directory
 *
 * @author Oscar Konno Sampaio (oscarks@gmail.com)
 *
 * @since 0.1
 */
Ant.mkdir(dir:"${basedir}/grails-app/ooerp")
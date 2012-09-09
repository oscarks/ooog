import grails.plugins.ooog.OpenObjectConnector
def grailsApplication=ctx.getBean('grailsApplication')

def server
def port
def database
def user
def password

if (args) {
	server=args.server
	port=args.port
	database=args.database
	user=args.user
	password=args.password
}

println "Server  : ${server}:${port}"
println "Database: ${database}"
println "User    : ${user}/${password.replaceAll('.','*')}"

def ooc = new OpenObjectConnector()
ooc.server=server
ooc.port=port
ooc.database=database
println "Login in ${ooc.server}..."
ooc.login(user,password)
println "Getting models..."
def models=ooc.getModelObjects()
def map=[:]
models.each { 
	map[it.model]=ooc.modelKeyToClassName(it.model)
}
map


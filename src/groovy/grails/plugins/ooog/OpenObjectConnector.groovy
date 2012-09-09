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
package grails.plugins.ooog

import groovy.net.xmlrpc.*
/**
 * Connector to Open Object XML-RPC interface of OpenErp Plataform
 * Implements the basics methods to interact with Open Object 
 *
 * @author Oscar Konno Sampaio  (oscarks@gmail.com)
 * @since 0.1
 */
class OpenObjectConnector {

	def substMap=['Filters':'Filter']

	String database
	String user
	String password
	String server
	int port
	
	int uid
	def openErp
	

	
	def login(usr,pwd) {
		def url="http://${server}:${port}/xmlrpc/common"
		def urloo="http://${server}:${port}/xmlrpc/object"
		this.user=usr
		this.password=pwd
		def remote = new XMLRPCServerProxy(url)
		uid = remote.login(database,user,password)
		openErp = new XMLRPCServerProxy(urloo)
	}
	
	def getModelObjects = {
		def ids=openErp.execute(database, uid, password, 'ir.model', 'search', [])
		openErp.execute(database, uid, password, 'ir.model', 'read', ids)
	}
	def getRegister (obj, ids){
		openErp.execute(database, uid, password, 'ir.model', 'read', ids)
	}
	
	
	def modelKeyToClassName(model) {
		def className=model.replaceAll('_','.').tokenize('.').collect {
			it.substring(0,1).toUpperCase()+it.substring(1).toLowerCase()
		}.join()
		substMap.each { k,v->
		    if (className.endsWith(k)) {
			def len=className.size()-k.size()
			className=className.substring(0,len)+v
		    }
		}
		className
	}
}

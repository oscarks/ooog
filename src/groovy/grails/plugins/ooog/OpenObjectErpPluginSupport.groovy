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

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.context.ApplicationContext
import org.codehaus.groovy.grails.support.SoftThreadLocalMap

import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty
import org.springframework.beans.BeanUtils
import org.codehaus.groovy.grails.web.binding.DataBindingUtils
import org.codehaus.groovy.grails.web.binding.DataBindingLazyMetaPropertyMap

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

class OpenObjectErpPluginSupport {
	private static final Log log = LogFactory.getLog(OpenObjectErpPluginSupport.class);
	static final PROPERTY_INSTANCE_MAP = new SoftThreadLocalMap()
	
	public static final String EVENT_BEFORE_SAVE = "beforeSave"
	public static final String EVENT_AFTER_SAVE = "afterSave"
	public static final String EVENT_BEFORE_DELETE = "beforeDelete"
	public static final String EVENT_AFTER_DELETE = "afterDelete"
	
	static enhanceDomainClass(OpenObjectErpClass domainClass, GrailsApplication application, ApplicationContext ctx) {
		addGrailsDomainPluginMethods(application, domainClass, ctx)
	
		addStaticMethods(application, domainClass, ctx)
		addInstanceMethods(application, domainClass, ctx)
		addDynamicFinderSupport(application, domainClass, ctx)
		addInitMethods(application, domainClass, ctx)
	
		ensureIndices(application, domainClass, ctx)
		ensureIndicesDeprecated(application, domainClass, ctx)
	}
	static void addGrailsDomainPluginMethods(application, domainClass, ctx) {
		MetaClass metaClass = domainClass.metaClass
	
		metaClass.ident = {-> delegate[domainClass.identifier.name] }
		metaClass.constructor = { ->
		  if (ctx.containsBean(domainClass.fullName)) {
			ctx.getBean(domainClass.fullName)
		  }
		  else {
			BeanUtils.instantiateClass(domainClass.clazz)
		  }
		}
	
		metaClass.static.create = { ->
		  if (ctx.containsBean(domainClass.fullName)) {
			ctx.getBean(domainClass.fullName)
		  }
		  else {
			BeanUtils.instantiateClass(domainClass.clazz)
		  }
		}
	
		addValidationMethods(application, domainClass, ctx)
		addRelationshipManagementMethods(domainClass)
	  }
	
}

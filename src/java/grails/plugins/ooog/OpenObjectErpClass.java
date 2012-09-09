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
package grails.plugins.ooog;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.grails.commons.AbstractGrailsClass;
import org.codehaus.groovy.grails.commons.GrailsDomainClass;
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty;
import org.codehaus.groovy.grails.commons.GrailsDomainConfigurationUtil;
import org.codehaus.groovy.grails.validation.ConstrainedProperty;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.Validator;
import java.lang.reflect.Field;

/**
 * Base class for Open Erp classes. 
 * Extends AbstractGrailsClass and implements GrailsDomainClass
 * 
 * @author oscar
 *
 */
public class OpenObjectErpClass extends AbstractGrailsClass implements
		GrailsDomainClass {
	private static final Log log = LogFactory.getLog(OpenObjectErpClass.class);
	
	Map<String, GrailsDomainClassProperty> propertyMap = new HashMap<String, GrailsDomainClassProperty>();
	private GrailsDomainClassProperty[] propertiesArray;
	private GrailsDomainClassProperty[] persistentPropertyArray;
	
	private Map<String, ConstrainedProperty> constraints = new HashMap<String, ConstrainedProperty>();
	private Validator validator;
	
	private OpenObjectErpClassProperty identifier;
	private OpenObjectErpClassProperty version = null; // no versioning

	public OpenObjectErpClass(Class<?> clazz) {
		super(clazz, "");
		evaluateClassProperties(clazz);
	    try {
	        this.constraints = GrailsDomainConfigurationUtil.evaluateConstraints(getClazz(), this.persistentPropertyArray);
	    } catch (Exception e) {
	        log.error("Error reading class [" + getClazz() + "] constraints: " + e.getMessage(), e);
	    }
	}

	private void evaluateClassProperties(Class artefactClass) {
	    Map<String, GrailsDomainClassProperty> persistentProperties = new HashMap<String, GrailsDomainClassProperty>();
/*
	    Field[] classFields = ReflectionUtils.getDeclaredAndInheritedFields(artefactClass, true);
	    for (Field field : classFields) {
	      PropertyDescriptor descriptor = null;
	      try {
	        descriptor = new PropertyDescriptor(field.getName(), artefactClass);
	      } catch (IntrospectionException e) {
	        log.error("Could not create PropertyDescriptor for class " + artefactClass.getName() + " field " + field.getName());
	        continue;
	      }
	      if (GrailsDomainConfigurationUtil.isNotConfigurational(descriptor)) {
	        final OpenObjectErpClassProperty property = new OpenObjectErpClassProperty(this, field, descriptor);
	      }
	    }

	    // convert to arrays for optimization - as used by grails
	    propertiesArray = propertyMap.values().toArray(new GrailsDomainClassProperty[propertyMap.size()]);
	    persistentPropertyArray = persistentProperties.values().toArray(new GrailsDomainClassProperty[persistentProperties.size()]);
*/
	  }	
	
	@Override
	public boolean isOwningClass(Class domainClass) {
		return false;
	}

	@Override
	public GrailsDomainClassProperty[] getProperties() {
		return propertiesArray;
	}

	@Override
	public GrailsDomainClassProperty[] getPersistantProperties() {
		return getPersistentProperties();
	}

	@Override
	public GrailsDomainClassProperty[] getPersistentProperties() {
		return persistentPropertyArray;
	}

	@Override
	public GrailsDomainClassProperty getIdentifier() {
		return this.identifier;
	}

	@Override
	public GrailsDomainClassProperty getVersion() {
		return this.version;
	}

	@Override
	public Map getAssociationMap() {
		return Collections.EMPTY_MAP;
	}

	@Override
	public GrailsDomainClassProperty getPropertyByName(String name) {
		return propertyMap.get(name);
	}

	@Override
	public GrailsDomainClassProperty getPersistentProperty(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFieldName(String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOneToMany(String propertyName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isManyToOne(String propertyName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isBidirectional(String propertyName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Class getRelatedClassType(String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map getConstrainedProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Validator getValidator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValidator(Validator validator) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getMappingStrategy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRoot() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<GrailsDomainClass> getSubClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refreshConstraints() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasSubClasses() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map getMappedBy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPersistentProperty(String propertyName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setMappingStrategy(String strategy) {
		// TODO Auto-generated method stub

	}

}

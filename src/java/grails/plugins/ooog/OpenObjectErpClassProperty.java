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

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.codehaus.groovy.grails.commons.GrailsDomainClass;
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty;
/**
 * @author oscar
 *
 */
public class OpenObjectErpClassProperty implements GrailsDomainClassProperty {
    
	private Class ownerClass;
	private Field field;
	private String name;
	private Class type;
	private OpenObjectErpClass domainClass;
	private Method getter;
	private boolean persistent = true;
	private boolean identity = false;
	
	
	public OpenObjectErpClassProperty(OpenObjectErpClass domain, Field field, PropertyDescriptor descriptor) {
	    this.ownerClass = domain.getClazz();
	    this.domainClass = domain;
	    this.field = field;
	    this.name = descriptor.getName();
	    this.type = descriptor.getPropertyType();
	    this.getter = descriptor.getReadMethod();
	}
	
	@Override
	public int getFetchMode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getReferencedPropertyType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GrailsDomainClassProperty getOtherSide() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTypePropertyName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GrailsDomainClass getDomainClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPersistent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOptional() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isIdentity() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOneToMany() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isManyToOne() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isManyToMany() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isBidirectional() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOneToOne() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GrailsDomainClass getReferencedDomainClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAssociation() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnum() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getNaturalName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReferencedDomainClass(
			GrailsDomainClass referencedGrailsDomainClass) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOtherSide(GrailsDomainClassProperty referencedProperty) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isInherited() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOwningSide() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCircular() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getReferencedPropertyName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmbedded() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GrailsDomainClass getComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOwningSide(boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isBasicCollectionType() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHasOne() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDerived(boolean derived) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDerived() {
		// TODO Auto-generated method stub
		return false;
	}

}

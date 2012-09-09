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
package grails.plugins.ooog.ast

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.ast.ModuleNode
import org.codehaus.groovy.ast.ClassNode
import grails.plugins.ooog.annotations.OpenObject
import grails.plugins.ooog.annotations.Id
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty
import org.codehaus.groovy.ast.PropertyNode
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import java.lang.reflect.Modifier
import org.codehaus.groovy.ast.expr.ConstantExpression

@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
class OpenObjectErpDomainASTTransformation implements ASTTransformation {
	
	private static final Log log = LogFactory.getLog(OpenObjectErpDomainASTTransformation.class)
	
	private static final ClassNode OPEN_OBJECT = new ClassNode(OpenObject)
	private static final String IDENTITY = GrailsDomainClassProperty.IDENTITY
	private static final String VERSION = GrailsDomainClassProperty.VERSION
  
	private static final ClassNode OPEN_OBJECT_ID = new ClassNode(Id)
	private static final ClassNode OBJECTID_TYPE = new ClassNode(Long)
	
	
	@Override
	public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
		println "AST Open Object ERP"
		if (nodes.length != 1 || !(nodes[0] instanceof ModuleNode)) {
	      throw new RuntimeException("OpenObject AST error - Expecting [ModuleNode] but got: " + Arrays.asList(nodes))
	    }
	
	    // process all classes within grails-app/ooerp
	    boolean isOpenObjectDir = false
	    if (sourceUnit.name =~ /grails-app.ooerp/) {
	      isOpenObjectDir = true
	    }
	    nodes[0].getClasses().each { ClassNode owner -> 
	      if (!isOpenObjectDir && !owner.getAnnotations(OPEN_OBJECT)) return // do not process this class
	      injectOpenObjectType(owner)
	      injectIdProperty(owner)
	    }
	}
	private void injectOpenObjectType(ClassNode classNode) {
		// annotate with OpenObject if not already the case
		if (classNode.getAnnotations(OPEN_OBJECT).size() > 0) return; // already annotated
	
		AnnotationNode openObjectAnottation = new AnnotationNode(OpenObject)
		openObjectAnottation.setMember('noClassnameStored', ConstantExpression.TRUE) // by default, do not store class name
		classNode.addAnnotation(openObjectAnottation)
	}
	/*
	 * same classes of OpenErp dont has a id
	private void injectIdProperty(ClassNode classNode) {
		if (classNode.fields.findAll({ it.getAnnotations(OPEN_OBJECT_ID) }).size() > 0) {
		  // annotation already exists
		  return
		}
		// annotate node id if present, otherwise inject id property
		PropertyNode identity = getProperty(classNode, IDENTITY)
	
		if (!identity) { // there is no id property at all
		  log.debug("Adding property [" + IDENTITY + "] to class [" + classNode.getName() + "]")
		  identity = classNode.addProperty(IDENTITY, Modifier.PUBLIC, OBJECTID_TYPE, null, null, null)
		}
	
		// now add annotation
		identity.getField().addAnnotation(new AnnotationNode(OPEN_OBJECT_ID))
	  }
	  */
}

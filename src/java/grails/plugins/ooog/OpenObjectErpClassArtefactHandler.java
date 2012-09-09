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

import org.codehaus.groovy.grails.commons.ArtefactHandlerAdapter;
import org.codehaus.groovy.grails.commons.GrailsClass;
import grails.plugins.ooog.annotations.OpenObject;
/**
 * Handler for OpenObjectErp artifact. 
 * The verification if a class is a OpenObjectErp artifact is 
 * determined by OpenObject annotation
 * @author oscar
 *
 */
public class OpenObjectErpClassArtefactHandler extends ArtefactHandlerAdapter {
    public static String TYPE = "OpenObjectErp";

    public OpenObjectErpClassArtefactHandler() {
      super(TYPE, OpenObjectErpClass.class, OpenObjectErpClass.class, null);
    }

    public boolean isArtefactClass(Class clazz) {
        return isOpenObjectErpClass(clazz);
    }

    @SuppressWarnings("unchecked")
    public static boolean isOpenObjectErpClass(Class clazz) {
        return clazz != null && clazz.getAnnotation(OpenObject.class) != null;
    }

    public GrailsClass newArtefactClass(Class artefactClass) {
        return new OpenObjectErpClass(artefactClass);
    }
}

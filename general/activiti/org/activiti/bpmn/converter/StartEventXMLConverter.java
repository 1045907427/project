/**
 * @(#)StartEventXMLConverter.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-2-2 zhengziyong 创建版本
 */
package org.activiti.bpmn.converter;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.StartEvent;

/**
 * 
 * 
 * @author zhengziyong
 */
public class StartEventXMLConverter extends BaseBpmnXMLConverter {

	 public static String getXMLType() {
		    return ELEMENT_EVENT_START;
		  }
		  
		  public static Class<? extends BaseElement> getBpmnElementType() {
		    return StartEvent.class;
		  }
		  
		  @Override
		  protected String getXMLElementName() {
		    return ELEMENT_EVENT_START;
		  }
		  
		  @Override
		  protected BaseElement convertXMLToElement(XMLStreamReader xtr) {
		    StartEvent startEvent = new StartEvent();
		    startEvent.setInitiator(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_EVENT_START_INITIATOR));
		    startEvent.setFormKey(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_FORM_FORMKEY));
		    
		    parseChildElements(getXMLElementName(), startEvent, xtr);
		    
		    return startEvent;
		  }
		  
		  @Override
		  protected void writeAdditionalAttributes(BaseElement element, XMLStreamWriter xtw) throws Exception {
		    StartEvent startEvent = (StartEvent) element;
		    writeQualifiedAttribute(ATTRIBUTE_EVENT_START_INITIATOR, startEvent.getInitiator(), xtw);
		    writeQualifiedAttribute(ATTRIBUTE_FORM_FORMKEY, startEvent.getFormKey(), xtw);
		  }
		  
		  @Override
		  protected void writeAdditionalChildElements(BaseElement element, XMLStreamWriter xtw) throws Exception {
		    StartEvent startEvent = (StartEvent) element;
		    writeEventDefinitions(startEvent.getEventDefinitions(), xtw);
		    writeFormProperties(startEvent, xtw);
		  }

}


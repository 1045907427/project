/**
 * @(#)ModelSaveRestResource.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-2-1 zhengziyong 创建版本
 */
package org.activiti.editor.rest.model;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.hd.agent.activiti.model.Definition;
import com.hd.agent.activiti.service.impl.DefinitionServiceImpl;
import com.hd.agent.common.util.SpringContextUtils;

/**
 * 
 * 
 * @author zhengziyong
 */
public class ModelSaveRestResource extends ServerResource implements ModelDataJsonConstants {

    protected static final Logger LOGGER = Logger.getLogger(ModelSaveRestResource.class.getName());

    @Put
    public void saveModel(Form modelForm) {

        ObjectMapper objectMapper = new ObjectMapper();
        String modelId = (String) getRequest().getAttributes().get("modelId");
        DefinitionServiceImpl definitionServiceImpl = (DefinitionServiceImpl)SpringContextUtils.getBean("definitionServiceImpl");
        try {

            String jsonXml = modelForm.getFirstValue("json_xml");
            {
                JSONObject jsonObject = JSONObject.fromObject(jsonXml);
                JSONArray childShapes = jsonObject.getJSONArray("childShapes");
                for(int i = 0, size = childShapes.size(); i < size; i++) {
                    JSONObject childShape = childShapes.getJSONObject(i);
                    if(childShape.has("properties")
                            && childShape.getJSONObject("properties").has("multiinstance_sequential")
                            && "No".equals(childShape.getJSONObject("properties").getString("multiinstance_sequential"))) {
                        childShape.getJSONObject("properties").put("multiinstance_collection", "assigneeList");
                        childShape.getJSONObject("properties").put("multiinstance_variable", "assignee");
                        childShape.getJSONObject("properties").put("multiinstance_condition", "${signService.complete(execution) }");
                    }
                }
                jsonXml = jsonObject.toString();
            }

            JsonNode jsonNode = objectMapper.readTree(jsonXml);
            String process_id = jsonNode.get("properties").get("process_id").asText();

            // check流程标识是否已被占用
            int ret = definitionServiceImpl.isDefinitionExist(process_id, modelId);
            if(ret > 0) {
                throw new Exception("该流程标识已被占用！");
            }

            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(jsonNode);
            byte[] xmlBytes = xmlConverter.convertToXML(bpmnModel);
            String name = modelForm.getFirstValue("name");
            Definition definition = new Definition();
            definition.setName(name);
            definition.setUnkey(process_id);
            definition.setBytes(xmlBytes);
            definition.setModelid(modelId);
            definition.setIsmodify("1");
            definitionServiceImpl.addDefinition(definition);

	    	RepositoryService repositoryService = ProcessEngines.getDefaultProcessEngine().getRepositoryService();
	        Model model = repositoryService.getModel(modelId);
	        ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
	        modelJson.put(MODEL_NAME, modelForm.getFirstValue("name"));
	        modelJson.put(MODEL_DESCRIPTION, modelForm.getFirstValue("description"));
	        model.setMetaInfo(modelJson.toString());
	        model.setName(modelForm.getFirstValue("name"));
	        repositoryService.saveModel(model);
	        repositoryService.addModelEditorSource(model.getId(), jsonXml.getBytes("utf-8"));
	        repositoryService.addModelEditorSourceExtra(model.getId(), modelForm.getFirstValue("svg_xml").getBytes("utf-8"));
	      
	    } catch(Exception e) {

	    	LOGGER.log(Level.SEVERE, "Error saving model", e);
	    	setStatus(Status.SERVER_ERROR_INTERNAL);
	    }
	  }
}


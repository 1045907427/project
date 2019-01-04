package org.activiti.editor.rest.model;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.activiti.editor.constants.ModelDataJsonConstants;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

/**
 * xml→json REST
 * @author limin
 * @date May 20, 2015
 */
public class ModelEditorJsonResolveRestResource extends ServerResource
        implements ModelDataJsonConstants
{
    protected static final Logger LOGGER = Logger.getLogger(ModelEditorJsonResolveRestResource.class.getName());
    private ObjectMapper objectMapper = new ObjectMapper();

    @Put
    public JsonNode getEditorJson(Form modelForm) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {

            JsonNode jsonNode = objectMapper.readTree(modelForm.getFirstValue("json_xml"));
            return  jsonNode;

        } catch(Exception e) {

            LOGGER.log(Level.SEVERE, "Error resolve model", e);
            setStatus(Status.SERVER_ERROR_INTERNAL);
        }

        return null;
    }
}
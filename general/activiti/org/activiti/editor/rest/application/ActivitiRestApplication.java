package org.activiti.editor.rest.application;

import org.activiti.editor.rest.main.EditorRestResource;
import org.activiti.editor.rest.main.PluginRestResource;
import org.activiti.editor.rest.main.StencilsetRestResource;
import org.activiti.editor.rest.model.ModelEditorJsonResolveRestResource;
import org.activiti.editor.rest.model.ModelEditorJsonRestResource;
import org.activiti.editor.rest.model.ModelSaveRestResource;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class ActivitiRestApplication extends Application
{
    public synchronized Restlet createInboundRoot()
    {
        Router router = new Router(getContext());

        router.attach("/model/{modelId}/json", ModelEditorJsonRestResource.class);
        router.attach("/model/{modelId}/resolve", ModelEditorJsonResolveRestResource.class);
        router.attach("/model/{modelId}/save", ModelSaveRestResource.class);

        router.attach("/editor", EditorRestResource.class);
        router.attach("/editor/plugins", PluginRestResource.class);
        router.attach("/editor/stencilset", StencilsetRestResource.class);

        return router;
    }
}
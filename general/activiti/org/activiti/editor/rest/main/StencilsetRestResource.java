
package org.activiti.editor.rest.main;

import java.io.InputStream;
import org.restlet.data.MediaType;
import org.restlet.representation.InputRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class StencilsetRestResource extends ServerResource {
    public StencilsetRestResource() {
    }

    @Get
    public InputRepresentation getStencilset() {
//        InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("stencilset.json");
        InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("stencilset_zh.json");
        InputRepresentation stencilsetResultRepresentation = new InputRepresentation(stencilsetStream);
        stencilsetResultRepresentation.setMediaType(MediaType.APPLICATION_JSON);
        return stencilsetResultRepresentation;
    }
}

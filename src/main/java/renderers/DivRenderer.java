package renderers;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

public class DivRenderer extends Renderer {

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
    	String message = (String) component.getAttributes().get("message");
    	
    	ResponseWriter writer = context.getResponseWriter();
        writer.startElement("div", component);
        writer.writeText(message, null);
        writer.endElement("div");
    }
}

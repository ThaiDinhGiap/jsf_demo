package components;

import java.io.IOException;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import renderers.DivRenderer;

@FacesComponent(createTag = true, namespace = "http://customcomponents.com/jsf", tagName = "hello", value = "helloComponent")
public class HelloComponent extends UIComponentBase {

    private static final String DEFAULT_MESSAGE = "Chào mừng bạn đến với nền tảng OpenTalk! Hãy khám phá ngay."; 

    @Override
    public String getFamily() {
        return "custom.components";
    }

    @Override
    public String getRendererType() {
        return "divRenderer"; 
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        if (getAttributes().get("message") == null) {
            getAttributes().put("message", DEFAULT_MESSAGE);
        }

        DivRenderer divRenderer = (DivRenderer) context.getRenderKit().getRenderer(getFamily(), getRendererType());
        divRenderer.encodeBegin(context, this); 
    }
}
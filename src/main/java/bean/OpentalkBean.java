package bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;
import model.Opentalk;
import service.OpentalkService;

@Named
@ConversationScoped
public class OpentalkBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private OpentalkService opentalkService;
    
    @Inject
    private Conversation conversation;
    
    private List<Opentalk> all;
    private Opentalk selectedOpentalk, newOpentalk;
    private boolean showAddForm = false;
    
    public void startConversation() {
    	if (conversation.isTransient()) {
    		conversation.begin();
    	}
    }
    
    public void endConversation() {
    	if (!conversation.isTransient()) {
    		conversation.end();
    	}
    }
    
    public List<Opentalk> getAll() {
        if (all == null) {
            all = opentalkService.getAll();
        }
        return all;
    }
    
    public void updateShowAddForm() {
        showAddForm = !showAddForm; 
        if (showAddForm) {
            newOpentalk = new Opentalk();
        }
    }
    
    public String add() {
        opentalkService.add(newOpentalk); 
        all = opentalkService.getAll();
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", "Đã thêm OpenTalk mới!"));
        newOpentalk = new Opentalk(); 
        showAddForm = false; 
        endConversation();
        return null;
    }
    
    public String selectForUpdate(Opentalk opentalk) {
        this.selectedOpentalk = opentalk;
        return null; 
    }
    
    public String update() {
        if (selectedOpentalk == null) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Lỗi", "Không có dữ liệu để cập nhật!"));
            return null;
        }
        opentalkService.update(selectedOpentalk);
        all = opentalkService.getAll();
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", "Cập nhật thành công!"));
        selectedOpentalk = null;
        endConversation();
        return null;
    }
    
    public String delete(Opentalk opentalk) {
        opentalkService.delete(opentalk.getId());
        all = opentalkService.getAll(); 
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", "Đã xóa OpenTalk!"));
        return null;
    }
    
    public Opentalk getSelectedOpentalk() {
    	startConversation();
        return selectedOpentalk;
    }

    public void setSelectedOpentalk(Opentalk selectedOpentalk) {
        this.selectedOpentalk = selectedOpentalk;
    }
    
    public Opentalk getNewOpentalk() {
        return newOpentalk;
    }

    public void setNewOpentalk(Opentalk newOpentalk) {
        this.newOpentalk = newOpentalk;
    }

    public boolean getShowAddForm() {
        return showAddForm;
    }

    public void setShowAddForm(boolean showAddForm) {
        this.showAddForm = showAddForm;
    }
    
    public Converter<LocalDateTime> getLocalDateTimeConverter() {
        return new Converter<LocalDateTime>() {
            private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

			@Override
			public LocalDateTime getAsObject(FacesContext context, UIComponent component, String value) {
				// TODO Auto-generated method stub
		        if (value == null || value.trim().isEmpty()) return null;

		        try {
		            return LocalDateTime.parse(value, FORMATTER);
		        } catch (DateTimeParseException e) {
		            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Lỗi định dạng!", "Ngày giờ không đúng định dạng yyyy-MM-dd'T'HH:mm.");
		            throw new ConverterException(msg);
		        }
			}

			@Override
			public String getAsString(FacesContext context, UIComponent component, LocalDateTime value) {
				// TODO Auto-generated method stub
                if (value == null) {
                    return "";
                }
                return value.format(FORMATTER);
			}
        };
    }
}

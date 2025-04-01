package bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.validator.ValidatorException;
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
	private Opentalk selectedOpentalk, newOpentalk, editOpentalk;
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
	
	public Opentalk getSelectedOpentalk() {
		startConversation();
		return selectedOpentalk;
	}

	public void setSelectedOpentalk(Opentalk selectedOpentalk) {
		this.selectedOpentalk = selectedOpentalk;
	}
	
	public Opentalk getEditOpentalk() {
		return editOpentalk;
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

	public List<Opentalk> getAll() {
		if (all == null) {
			all = opentalkService.getAll();
		}
		return all;
	}

	public void showAddForm() {
		if (showAddForm) return;
		startConversation();
		showAddForm = true;
		newOpentalk = new Opentalk();
	}

	public String add() {
		opentalkService.add(newOpentalk);
		all = opentalkService.getAll();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", "Đã thêm OpenTalk mới thành công!"));
		
		cancelAdd();
		return null;
	}
	
	public void cancelAdd() {
		newOpentalk = null;
		showAddForm = false;
		endConversation();
	}

	public String selectForUpdate(Opentalk opentalk) {
		this.selectedOpentalk = opentalk;
		this.editOpentalk = new Opentalk(opentalk);
		return null;
	}

	public String update() {
		if (editOpentalk == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Lỗi", "Không có dữ liệu để cập nhật!"));
			return null;
		}
		
		if (!selectedOpentalk.equals(editOpentalk)) {
			selectedOpentalk.updateFrom(editOpentalk);
			opentalkService.update(selectedOpentalk);
			all = opentalkService.getAll();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", "Cập nhật thành công!"));
		}
		
		cancelUpdate();
		return null;
	}
	
	public void cancelUpdate() {
	    selectedOpentalk = null;
	    editOpentalk = null;
	    endConversation(); 
	}

	public String delete(Opentalk opentalk) {
		opentalkService.delete(opentalk.getId());
		all = opentalkService.getAll();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", "Đã xóa thành cồng!"));
		return null;
	}

	public Converter<LocalDateTime> getLocalDateTimeConverter() {
		return new Converter<LocalDateTime>() {
			private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

			@Override
			public LocalDateTime getAsObject(FacesContext context, UIComponent component, String value) {
				if (value == null || value.trim().isEmpty())
					return null;

				try {
					return LocalDateTime.parse(value, FORMATTER);
				} catch (DateTimeParseException e) {
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Lỗi định dạng!",
							"Ngày giờ không đúng định dạng yyyy-MM-dd HH:mm.");
					throw new ConverterException(msg);
				}
			}

			@Override
			public String getAsString(FacesContext context, UIComponent component, LocalDateTime value) {
				if (value == null) {
					return "";
				}
				return value.format(FORMATTER);
			}
		};
	}
	
	public void validateTitle(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	    if (value == null) {
	        return;
	    }

	    String title = value.toString().trim();
	    
	    if (title.length() < 5) {
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Lỗi tiêu đề", "Tiêu đề phải có tối thiểu 5 ký tự");
	        throw new ValidatorException(message);
	    }
	    
	    String capitalizedTitle = Arrays.stream(title.split("\\s+"))
	            .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
	            .collect(Collectors.joining(" "));

	    ((UIInput) component).setSubmittedValue(capitalizedTitle);
	}
}

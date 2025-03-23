package validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "titleValidator")
public class TitleValidator implements Validator<Object> {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
		String title = value.toString().trim();
		
		if (title.length() <= 5) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Lỗi tiêu đề", "Tiêu đề phải có tối thiểu 5 ký tự");
			throw new ValidatorException(message);
		}
	}

}

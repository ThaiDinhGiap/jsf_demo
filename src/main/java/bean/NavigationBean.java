package bean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class NavigationBean {
    public String goToHome() {
        return "home?faces-redirect=true"; 
    }
    
    public String goToManagement() {
        return "index?faces-redirect=true";
    }
}


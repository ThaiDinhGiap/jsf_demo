package bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

@Dependent
@Named
class Parent {
    @PostConstruct
    public void initParent() {
        System.out.println("Parent @PostConstruct called");
    }
    
    @PreDestroy
    public void cleanup() {
        System.out.println("Cleaning up resources...");
    }
    
    @PreDestroy
    public void cleanup2() {
        System.out.println("Cleaning up resources 3...");
    }
}

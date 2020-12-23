package decorators;

import beans.RegisterBean;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;

@Decorator
@Named
public abstract class RegisterDecorator implements Serializable, IRegisterBean {

    @Inject
    @Delegate
    @Any
    private RegisterBean registerBean;

    @Override
    public void save(){
        String[] values = {"user", "admin"};
        if(Arrays.stream(values).anyMatch(s -> getRole().equals(s))){
            registerBean.save();
        }
    }
}

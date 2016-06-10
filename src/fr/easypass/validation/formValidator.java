package fr.easypass.validation;

import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

public abstract class formValidator<T> {
	
	 public abstract T getObj();
	
	public ArrayList<String> isValid(){
		 
		 ArrayList<String> errors = new ArrayList<String>();
		    //do your custom validations if needed
		 Set<ConstraintViolation<T>> validation = Validation.buildDefaultValidatorFactory()
		            .getValidator().validate(getObj());
		 if(validation.size() > 0 ){
			 for (ConstraintViolation<T> error : validation) {
				errors.add(error.getPropertyPath() +" "+error.getMessage());
			 }
		 }
		 return errors;
	 }
}

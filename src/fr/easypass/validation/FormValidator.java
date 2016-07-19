package fr.easypass.validation;

import java.util.HashMap;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

public abstract class FormValidator<T> {

    public abstract T getObj();

    public HashMap<String, String> isValid() {

        HashMap<String, String> errors = new HashMap<>();
        // do your custom validations if needed
        Set<ConstraintViolation<T>> validation = Validation.buildDefaultValidatorFactory().getValidator()
                .validate(getObj());
        if (validation.size() > 0) {
            for (ConstraintViolation<T> error : validation) {
                errors.put(error.getPropertyPath().toString(), error.getMessage());
            }
        }
        return errors;
    }
}


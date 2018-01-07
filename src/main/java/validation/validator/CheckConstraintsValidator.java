package validation.validator;

import validation.constraints.CheckConstraintsIn;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class CheckConstraintsValidator implements ConstraintValidator<CheckConstraintsIn, String> {

    private String[] constraints;

    @Override
    public void initialize(CheckConstraintsIn constraintAnnotation) {
        this.constraints = constraintAnnotation.constraints();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && Arrays.asList(constraints).contains(value);
    }
}

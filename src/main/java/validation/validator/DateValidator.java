package validation.validator;

import validation.constraints.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class DateValidator implements ConstraintValidator<Date, String> {

    private String datePattern;

    @Override
    public void initialize(Date constraintAnnotation) {
        this.datePattern = constraintAnnotation.datePattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern = Pattern.compile(datePattern);
        return pattern.matcher(value).find();
    }
}
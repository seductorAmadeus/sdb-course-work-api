package validation.constraints;


import validation.validator.CheckConstraintsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckConstraintsValidator.class)
@Documented
public @interface CheckConstraintsIn {

    String message() default "{validation.constraints.CheckConstraintsIn.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] constraints() default {};

}


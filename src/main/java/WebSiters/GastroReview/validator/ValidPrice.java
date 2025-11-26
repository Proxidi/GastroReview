package WebSiters.GastroReview.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PriceValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPrice {
    String message() default "El precio debe ser mayor o igual a 0";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

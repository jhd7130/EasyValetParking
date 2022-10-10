package com.ohho.valetparking.global.common.annotations;

import com.ohho.valetparking.global.util.RangeLimitValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Role : Responsibility : Cooperation with :
 **/
@Documented
@Constraint(validatedBy = RangeLimitValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RangeLimit {
  String message() default "NOT IN RANGE";
  Class<?>[] group() default {};
  Class<? extends Payload>[] payload() default {};
}

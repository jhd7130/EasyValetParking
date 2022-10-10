package com.ohho.valetparking.global.util;

import com.ohho.valetparking.global.common.annotations.RangeLimit;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Role : Responsibility : Cooperation with :
 **/
public class RangeLimitValidator implements ConstraintValidator<RangeLimit, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return false;
  }
}

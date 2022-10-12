package com.ohho.valetparking.global.security.permission;

import com.ohho.valetparking.domains.member.enums.MemberType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @auth : Atom
 * @Role : Permission Flag
 */
@Documented
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionRequired {
  MemberType permission() default MemberType.ADMIN;
}

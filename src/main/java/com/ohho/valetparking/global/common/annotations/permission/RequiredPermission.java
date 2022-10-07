package com.ohho.valetparking.global.common.annotations.permission;

import com.ohho.valetparking.global.common.enums.PermissionLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @auth : Atom
 * @Role : Permission Flag
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredPermission {
    PermissionLevel target() default PermissionLevel.ADMIN;
}

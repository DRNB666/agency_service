package com.turing.code.common.annotation.repeat;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * @author base
 */
@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PreventRepeat {
}
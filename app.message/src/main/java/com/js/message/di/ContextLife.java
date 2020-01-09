package com.js.message.di;

import java.lang.annotation.Retention;
import javax.inject.Scope;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by huyg on 2018/8/22.
 */
@Scope
@Retention(RUNTIME)
public @interface ContextLife {
    String value() default "Application";
}

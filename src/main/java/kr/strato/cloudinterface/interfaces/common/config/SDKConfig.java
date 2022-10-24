package kr.strato.cloudinterface.interfaces.common.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SDKConfig {
    String type() default "";

    String authUri() default "";

    String tokenUri() default "";
    String authProviderUrl() default"";
    String zoneId() default "";
}

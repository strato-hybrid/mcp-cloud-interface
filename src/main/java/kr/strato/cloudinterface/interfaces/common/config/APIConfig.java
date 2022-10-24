package kr.strato.cloudinterface.interfaces.common.config;

import kr.strato.cloudinterface.core.naver.NaverApiV2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface APIConfig {
    String reqUrl() default "";

    String serviceName() default "";

    String method() default "GET";

    String scope() default  "";
}

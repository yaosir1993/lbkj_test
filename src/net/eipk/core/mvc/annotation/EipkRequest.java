package net.eipk.core.mvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.eipk.core.mvc.RequestMethod;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EipkRequest {

	String value() default "";
	RequestMethod method() default RequestMethod.GET;
	boolean ajax() default false;
}

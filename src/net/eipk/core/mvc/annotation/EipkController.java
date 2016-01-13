package net.eipk.core.mvc.annotation;

import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;

@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface EipkController {
  String value() default "";
}

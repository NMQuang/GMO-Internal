package jp.co.gzr_internal.api.web.rest.validator.annotation.date;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.validator.annotation.pattern.Pattern;

@Documented
@Constraint(validatedBy = {DateFormatValidator.class})
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface DateFormat {
    /**
     * Message.
     *
     * @return the string
     */
    String message() default Contants.CONST_STR_BLANK;

    /**
     * Error code.
     *
     * @return the string
     */
    String errorCode() default Contants.CONST_STR_BLANK;

    /**
     * Item name.
     *
     * @return the string
     */
    String itemName() default Contants.CONST_STR_BLANK;

    /**
     * Pattern.
     *
     * @return the string
     */
    String formatdate() default Contants.CONST_STR_BLANK;

    /**
     * Regex
     *
     * @return the regex
     */
    String regex() default Contants.CONST_STR_BLANK;

    /**
     * Groups.
     *
     * @return the class[]
     */
    Class<?>[] groups() default { };

    /**
     * Payload.
     *
     * @return the class<? extends payload>[]
     */
    Class<? extends Payload>[] payload() default { };

    /**
     * Defines several {@link Pattern} annotations on the same element.
     *
     * @see javax.validation.constraints.Pattern
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        /**
         * Value.
         *
         * @return the pattern[]
         */
        Date[] value();
    }

}


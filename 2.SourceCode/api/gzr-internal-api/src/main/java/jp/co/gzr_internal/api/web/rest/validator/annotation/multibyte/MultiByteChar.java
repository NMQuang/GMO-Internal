/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.validator.annotation.multibyte;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.validator.annotation.pattern.Pattern;

/**
 * Validator to check the string is contant double-byte character.
 *
 * @author datnt
 */
@Documented
@Constraint(validatedBy = {MultiByteCharValidator.class})
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface MultiByteChar {

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
    String pattern() default Contants.CONST_STR_BLANK;

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
        MultiByteChar[] value();
    }
}

/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.validator.annotation.max;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.co.gzr_internal.api.web.rest.commons.Contants;

/**
 * The Interface RangeNumber.
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {RangeNumberValidator.class})
public @interface RangeNumber {

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
     * Min.
     *
     * @return value the element must be lower or equal to
     */
    long min() default 0L;

    /**
     * Max.
     *
     * @return the long
     */
    long max() default 0L;

    /**
     * Defines several {@link RangeNumber} annotations on the same element.
     *
     * @see RangeNumber
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        /**
         * Value.
         *
         * @return the range number[]
         */
        RangeNumber[] value();
    }
}

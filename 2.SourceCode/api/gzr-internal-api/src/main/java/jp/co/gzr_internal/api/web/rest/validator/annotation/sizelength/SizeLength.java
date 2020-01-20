/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.validator.annotation.sizelength;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.validator.annotation.notnull.NotNull;

/**
 * The Interface SizeLength.
 * 
 * @author VuDA
 */
@Documented
@Constraint(validatedBy = {SizeLengthValidator.class})
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface SizeLength {

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
     * Partition.
     *
     * @return the string
     */
    String partition() default Contants.CONST_STR_BLANK;
    
    /**
     * Min length.
     *
     * @return the int
     */
    int minLength() default 0;
    
    /**
     * Max length.
     *
     * @return the int
     */
    int maxLength() default 0;

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
     * Defines several {@link NotNull} annotations on the same element.
     *
     * @see javax.validation.constraints.NotNull
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        /**
         * Value.
         *
         * @return the size length[]
         */
        SizeLength[] value();
    }
}

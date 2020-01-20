/*	
 * Aviva SMS Revamp
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: HungVT
 * Creation Date : Oct 16, 2019
 */
package jp.co.gzr_internal.api.web.rest.validator.annotation.project;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.co.gzr_internal.api.web.rest.commons.Contants;

/**
 * The Interface CreateBlockSMS.
 */
@Documented
@Constraint(validatedBy = {CreateProjectValidator.class})
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface CreateProject {

    /**
     * Message.
     *
     * @return the string
     */
    String message() default Contants.CONST_STR_BLANK;

    /**
     * Groups.
     *
     * @return the class[]
     */
    Class<?>[] groups() default {};

    /**
     * Payload.
     *
     * @return the class<? extends payload>[]
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * The Interface List.
     */
    @Target({METHOD, TYPE, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        /**
         * Value.
         *
         * @return the creates the block SM s[]
         */
        CreateProject[] value();
    }

}

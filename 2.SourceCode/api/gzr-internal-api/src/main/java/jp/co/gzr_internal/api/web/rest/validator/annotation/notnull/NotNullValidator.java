/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.validator.annotation.notnull;

import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;

import jp.co.gzr_internal.api.web.rest.commons.Contants;

/**
 * The Class NotNullValidator.
 * 
 * @author VuDA
 */
public class NotNullValidator implements HibernateConstraintValidator<NotNull, String> {

    /*
     * (non-Javadoc)
     * 
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && !Contants.CONST_STR_BLANK.equals(value.trim())) {
            return true;
        }
        return false;
    }

}

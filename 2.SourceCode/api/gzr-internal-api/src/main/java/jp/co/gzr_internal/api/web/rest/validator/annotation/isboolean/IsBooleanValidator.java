/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.validator.annotation.isboolean;

import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;

import jp.co.gzr_internal.api.web.rest.commons.Contants;

/**
 * The Class IsBooleanValidator.
 */
public class IsBooleanValidator implements HibernateConstraintValidator<IsBoolean, String> {

    /* (non-Javadoc)
     * @see org.hibernate.validator.constraintvalidation.HibernateConstraintValidator#initialize(javax.validation.metadata.ConstraintDescriptor, org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext)
     */
    @Override
    public void initialize(ConstraintDescriptor<IsBoolean> constraintDescriptor,
        HibernateConstraintValidatorInitializationContext initializationContext) {
    }

    /* (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        // Try parse value to Boolean
        try {
            // Check if value equal false then return true
            // otherwise try parse value to boolean
            if (Contants.CONT_STR_FALSE.equals(value.toLowerCase())) {
                return true;
            }
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            return false;
        }
    }
}

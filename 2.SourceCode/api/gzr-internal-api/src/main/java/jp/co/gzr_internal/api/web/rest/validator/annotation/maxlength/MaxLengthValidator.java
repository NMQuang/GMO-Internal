/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.validator.annotation.maxlength;

import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;

import jp.co.gzr_internal.api.web.rest.commons.Contants;

/**
 * The Class MaxLengthValidator.
 * 
 * @author VuDA
 */
public class MaxLengthValidator implements HibernateConstraintValidator<MaxLength, String> {

    /** The max length. */
    private int maxLength = 0;
    
    /* (non-Javadoc)
     * @org.hibernate.validator.constraintvalidation.HibernateConstraintValidator#initialize(javax.validation.metadata.ConstraintDescriptor, org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext)
     */
    @Override
    public void initialize(ConstraintDescriptor<MaxLength> constraintDescriptor,
        HibernateConstraintValidatorInitializationContext initializationContext) {
        
        this.maxLength = (int) constraintDescriptor.getAttributes().get(Contants.CONST_STR_MAX_LENGTH);
    }
    
    /* (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        
        if (value != null && value.trim().length() > maxLength) {
            return false;
        }
        return true;
    }

}

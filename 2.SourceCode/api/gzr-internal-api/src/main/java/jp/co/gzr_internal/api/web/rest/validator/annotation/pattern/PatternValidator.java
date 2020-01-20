/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.validator.annotation.pattern;

import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;

import jp.co.gzr_internal.api.web.rest.commons.Contants;

/**
 * The Class PatternValidator.
 * 
 * @author VuDA
 */
public class PatternValidator implements HibernateConstraintValidator<Pattern, String> {

    /** The pattern. */
    private String pattern;
    
    /* (non-Javadoc)
     * @see org.hibernate.validator.constraintvalidation.HibernateConstraintValidator#initialize(javax.validation.metadata.ConstraintDescriptor, org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext)
     */
    @Override
    public void initialize(ConstraintDescriptor<Pattern> constraintDescriptor,
        HibernateConstraintValidatorInitializationContext initializationContext) {
        this.pattern = (String) constraintDescriptor.getAttributes().get(Contants.CONST_STR_PATTERN);
    }
    
    /* (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        
        java.util.regex.Pattern pat = java.util.regex.Pattern.compile(this.pattern);
        if (value != null && !value.isEmpty() && !pat.matcher(value).matches()) {
            return false;
        }
        return true;
    }

}

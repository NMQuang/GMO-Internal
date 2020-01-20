/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.validator.annotation.multibyte;

import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;

import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.validator.StringValidation;

/**
 * The MultiByteCharValidator class.
 *
 * @author datnt
 */
public class MultiByteCharValidator implements HibernateConstraintValidator<MultiByteChar, String> {

    /** The pattern. */
    private String pattern;

    /* (non-Javadoc)
     * @see org.hibernate.validator.constraintvalidation.HibernateConstraintValidator#initialize(javax.validation.metadata.ConstraintDescriptor, org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext)
     */
    @Override
    public void initialize(ConstraintDescriptor<MultiByteChar> constraintDescriptor,
        HibernateConstraintValidatorInitializationContext initializationContext) {

        this.pattern = (String) constraintDescriptor.getAttributes().get(Contants.CONST_STR_PATTERN);
    }

    /* (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (StringValidation.isNullOrEmpty(value, this.pattern)) {
            return true;
        }

        return value.matches(pattern);
    }

}

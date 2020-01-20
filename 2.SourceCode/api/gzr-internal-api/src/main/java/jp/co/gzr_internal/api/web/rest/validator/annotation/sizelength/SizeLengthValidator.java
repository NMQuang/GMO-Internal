/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.validator.annotation.sizelength;

import java.util.Map;

import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;

import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.validator.StringValidation;

/**
 * The Class SizeLengthValidator.
 *
 * @author VuDA
 */
public class SizeLengthValidator implements HibernateConstraintValidator<SizeLength, String> {

    /** The min length. */
    private int minLength;

    /** The max length. */
    private int maxLength;

    /* (non-Javadoc)
     * @see org.hibernate.validator.constraintvalidation.HibernateConstraintValidator#initialize(javax.validation.metadata.ConstraintDescriptor, org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext)
     */
    @Override
    public void initialize(ConstraintDescriptor<SizeLength> constraintDescriptor,
        HibernateConstraintValidatorInitializationContext initializationContext) {
        Map<String, Object> attributes = constraintDescriptor.getAttributes();
        this.minLength = (int) attributes.get(Contants.CONST_STR_MIN_LENGTH);
        this.maxLength = (int) attributes.get(Contants.CONST_STR_MAX_LENGTH);
    }

    /* (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (!StringValidation.isNullOrEmpty(value)
            && (value.trim().length() < minLength || value.trim().length() > maxLength)) {
            return false;
        }
        return true;
    }

}

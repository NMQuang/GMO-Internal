/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.validator.annotation.max;

import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;

import jp.co.gzr_internal.api.web.rest.commons.Contants;

/**
 * The Class RangeNumberValidator.
 *
 * @author datnt
 */
public class RangeNumberValidator implements HibernateConstraintValidator<RangeNumber, Integer> {

    /** The max. */
    private long max;

    /** The min. */
    private long min;

    /* (non-Javadoc)
     * @see org.hibernate.validator.constraintvalidation.HibernateConstraintValidator#initialize(javax.validation.metadata.ConstraintDescriptor, org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext)
     */
    @Override
    public void initialize(ConstraintDescriptor<RangeNumber> constraintDescriptor,
        HibernateConstraintValidatorInitializationContext initializationContext) {
        // TODO Auto-generated method stub
        HibernateConstraintValidator.super.initialize(constraintDescriptor, initializationContext);

        this.max = (long) constraintDescriptor.getAttributes().get(Contants.CONST_STR_MAX);
        this.min = (long) constraintDescriptor.getAttributes().get(Contants.CONST_STR_MIN);
    }

    /* (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {

        if (value == null) {
            return false;
        }
        return value <= max && value >= min;
    }

}

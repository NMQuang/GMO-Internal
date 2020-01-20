/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.validator.annotation.number;

import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;

import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.validator.StringValidation;

/**
 * The NumberValidator class.
 *
 * @author datnt
 */
public class NumberValidator implements HibernateConstraintValidator<Number, String>{

    /** The type. */
    private NumberEnum type;

    /* (non-Javadoc)
     * @see org.hibernate.validator.constraintvalidation.HibernateConstraintValidator#initialize(javax.validation.metadata.ConstraintDescriptor, org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext)
     */
    @Override
    public void initialize(ConstraintDescriptor<Number> constraintDescriptor,
        HibernateConstraintValidatorInitializationContext initializationContext) {
        // TODO Auto-generated method stub
        HibernateConstraintValidator.super.initialize(constraintDescriptor, initializationContext);

        type = (NumberEnum) constraintDescriptor.getAttributes().get(Contants.CONST_STR_TYPE);
    }

    /* (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (StringValidation.isBlank(value)) {
            return true;
        }

        return value.matches(type.toString());
    }

}

/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.validator.annotation.date;

import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;

import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.util.Utils;
import jp.co.gzr_internal.api.web.rest.validator.DateValidation;
import jp.co.gzr_internal.api.web.rest.validator.StringValidation;

/**
 * The Class BirthDateValidator.
 */
public class BirthDateValidator implements HibernateConstraintValidator<BirthDate, String> {

    /** The pattern. */
    private String pattern;

    /** The regex. */
    private String regex;

    /* (non-Javadoc)
     * @see org.hibernate.validator.constraintvalidation.HibernateConstraintValidator#initialize(javax.validation.metadata.ConstraintDescriptor, org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext)
     */
    @Override
    public void initialize(ConstraintDescriptor<BirthDate> constraintDescriptor,
        HibernateConstraintValidatorInitializationContext initializationContext) {
        // TODO Auto-generated method stub
        HibernateConstraintValidator.super.initialize(constraintDescriptor, initializationContext);

        this.pattern = (String) constraintDescriptor.getAttributes().get(Contants.CONST_STR_PATTERN);
        this.regex   = (String) constraintDescriptor.getAttributes().get(Contants.CONST_STR_REGEX);
    }

    /* (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (StringValidation.isNullOrEmpty(value)) {
            return true;
        }

        java.util.Date date = Utils.stringToDate(value, pattern);
        java.util.Date currentDate = Utils.getCurrentDate(pattern);

        if (value.matches(this.regex) && DateValidation.isDateValid(value, this.pattern) && date != null
            && currentDate != null) {
            return date.getTime() <= currentDate.getTime();
        }

        return true;
    }

}

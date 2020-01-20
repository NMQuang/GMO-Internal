package jp.co.gzr_internal.api.web.rest.validator.annotation.date;

import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;

import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.validator.DateValidation;
import jp.co.gzr_internal.api.web.rest.validator.StringValidation;

public class DateExistValidator implements HibernateConstraintValidator<DateExist, String> {

    /** The pattern. */
    private String pattern;

    /** The regex. */
    private String regex;

    /* (non-Javadoc)
     * @see org.hibernate.validator.constraintvalidation.HibernateConstraintValidator#initialize(javax.validation.metadata.ConstraintDescriptor, org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext)
     */
    @Override
    public void initialize(ConstraintDescriptor<DateExist> constraintDescriptor,
        HibernateConstraintValidatorInitializationContext initializationContext) {

        HibernateConstraintValidator.super.initialize(constraintDescriptor, initializationContext);

        this.pattern = (String) constraintDescriptor.getAttributes().get(Contants.CONST_STR_PATTERN);
        this.regex   = (String) constraintDescriptor.getAttributes().get(Contants.CONST_STR_REGEX);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (StringValidation.isNullOrEmpty(value)) {
            return true;
        }
        return DateValidation.isDateValid(value, this.pattern);
    }

}

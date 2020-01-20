package jp.co.gzr_internal.api.web.rest.validator.annotation.date;

import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;

import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.validator.StringValidation;

public class DateFormatValidator implements HibernateConstraintValidator<DateFormat, String> {

    /** The pattern. */
    private String pattern;

    /** The regex. */
    private String regex;

    /* (non-Javadoc)
     * @see org.hibernate.validator.constraintvalidation.HibernateConstraintValidator#initialize(javax.validation.metadata.ConstraintDescriptor, org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext)
     */
    @Override
    public void initialize(ConstraintDescriptor<DateFormat> constraintDescriptor,
        HibernateConstraintValidatorInitializationContext initializationContext) {
        // TODO Auto-generated method stub
        HibernateConstraintValidator.super.initialize(constraintDescriptor, initializationContext);

        this.pattern = (String) constraintDescriptor.getAttributes().get(Contants.CONST_STR_PATTERN);
        this.regex   = (String) constraintDescriptor.getAttributes().get(Contants.CONST_STR_REGEX);
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        
        if (StringValidation.isNullOrEmpty(value)) {
            return true;
        }
        
        return value.matches(this.regex);
    }

}

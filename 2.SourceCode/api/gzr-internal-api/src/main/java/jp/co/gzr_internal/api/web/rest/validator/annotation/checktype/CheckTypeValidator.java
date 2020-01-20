/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.validator.annotation.checktype;

import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;

import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;

/**
 * The DateValidator class to define how the Date annotation check a string is valid date format.
 *
 * @author VuDA
 */
public class CheckTypeValidator implements HibernateConstraintValidator<CheckType, String> {

    /** The type. */
    private String type;
    
    /** The default value. */
    private String defaultValue;

    /* (non-Javadoc)
     * @see org.hibernate.validator.constraintvalidation.HibernateConstraintValidator#initialize(javax.validation.metadata.ConstraintDescriptor, org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext)
     */
    @Override
    public void initialize(ConstraintDescriptor<CheckType> constraintDescriptor,
        HibernateConstraintValidatorInitializationContext initializationContext) {

        this.type = (String) constraintDescriptor.getAttributes().get("type");
        this.defaultValue = (String) constraintDescriptor.getAttributes().get("defaultValue");
    }

    /* (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        // Set default value
        if(value == null) {
            value = this.defaultValue;
        }
        
        if(type.toLowerCase().equals("boolean") && 
            ("true".equals(value.toLowerCase()) || "false".equals(value.toLowerCase()))) {
            return true;
        } else if(type.toLowerCase().equals("number")) {
            return NumberUtils.isCreatable(value);
        }

        return false;
    }
}

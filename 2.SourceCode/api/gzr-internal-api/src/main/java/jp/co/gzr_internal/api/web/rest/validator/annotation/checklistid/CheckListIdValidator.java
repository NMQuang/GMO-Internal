/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.validator.annotation.checklistid;

import java.util.List;

import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;

/**
 * The CheckListIdValidator class to define how the Date annotation check a string is valid date format.
 *
 * @author VuDA
 */
public class CheckListIdValidator implements HibernateConstraintValidator<CheckListId, List<String>> {

    /* (non-Javadoc)
     * @see org.hibernate.validator.constraintvalidation.HibernateConstraintValidator#initialize(javax.validation.metadata.ConstraintDescriptor, org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext)
     */
    @Override
    public void initialize(ConstraintDescriptor<CheckListId> constraintDescriptor,
        HibernateConstraintValidatorInitializationContext initializationContext) {
    }

    /* (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {

        // Set default value
        if (values != null) {
            for (String id : values) {
                try {
                    Integer.parseInt(id);
                } catch (Exception e) {
                    return false;
                }
            }
        }

        return true;
    }
}

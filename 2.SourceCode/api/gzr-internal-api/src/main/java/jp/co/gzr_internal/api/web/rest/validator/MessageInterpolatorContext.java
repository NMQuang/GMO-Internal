/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.validator;

import javax.validation.MessageInterpolator.Context;
import javax.validation.metadata.ConstraintDescriptor;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;

/**
 * The Class MessageInterpolatorContext.
 * 
 * @author VuDA
 */
public class MessageInterpolatorContext implements Context {

    /** The violation. */
    private ConstraintViolationImpl<?> violation;

    /**
     * Instantiates a new message interpolator context.
     *
     * @param violation the violation
     */
    public MessageInterpolatorContext(ConstraintViolationImpl<?> violation) {
        this.violation = violation;
    }

    /* (non-Javadoc)
     * @see javax.validation.MessageInterpolator.Context#getConstraintDescriptor()
     */
    @Override
    public ConstraintDescriptor<?> getConstraintDescriptor() {
        return violation.getConstraintDescriptor();
    }

    /* (non-Javadoc)
     * @see javax.validation.MessageInterpolator.Context#getValidatedValue()
     */
    @Override
    public Object getValidatedValue() {
        return violation.getInvalidValue();
    }

    /* (non-Javadoc)
     * @see javax.validation.MessageInterpolator.Context#unwrap(java.lang.Class)
     */
    @Override
    public <T> T unwrap(Class<T> type) {
        try {
            return type.newInstance();
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        }
        return null;
    }

}

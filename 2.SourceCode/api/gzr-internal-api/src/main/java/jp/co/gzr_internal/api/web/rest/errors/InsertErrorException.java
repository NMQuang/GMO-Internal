/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import jp.co.gzr_internal.api.web.rest.commons.MessageContants;

/**
 * The Class InsertErrorException.
 */
public class InsertErrorException extends AbstractThrowableProblem {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new insert error exception.
     */
    public InsertErrorException() {
        super(ErrorConstants.DEFAULT_TYPE, MessageContants.CONST_ERROR_CODE_INSERT_FAILURE, Status.INTERNAL_SERVER_ERROR);
    }
}

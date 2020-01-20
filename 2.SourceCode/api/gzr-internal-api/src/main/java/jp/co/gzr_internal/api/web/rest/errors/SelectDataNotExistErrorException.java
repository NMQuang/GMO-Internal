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
 * The Class SelectDataNotExistErrorException.
 */
public class SelectDataNotExistErrorException extends AbstractThrowableProblem {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new select data not exist error exception.
     */
    public SelectDataNotExistErrorException() {
        super(ErrorConstants.DEFAULT_TYPE, MessageContants.CONST_ERROR_CODE_DATA_NOT_FOUND, Status.NOT_FOUND);
    }
}

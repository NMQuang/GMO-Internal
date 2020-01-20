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
 * The Class InsertDuplicateException.
 */
public class InsertDuplicateException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public InsertDuplicateException() {
        super(ErrorConstants.EMAIL_NOT_FOUND_TYPE, MessageContants.CONST_CODE_INSERT_DUPLICATE_EXCEPTION, Status.BAD_REQUEST);
    }
}

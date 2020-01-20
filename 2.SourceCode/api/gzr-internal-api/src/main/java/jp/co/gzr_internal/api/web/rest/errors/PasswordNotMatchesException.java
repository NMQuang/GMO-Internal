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
 * The PasswordNotMatchesException will throw when password and confirm password not matches together.
 *
 * @author datnt
 */
public class PasswordNotMatchesException extends AbstractThrowableProblem {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new password not matches exception.
     */
    public PasswordNotMatchesException() {
        super(ErrorConstants.DEFAULT_TYPE, MessageContants.CONST_ERROR_CODE_PASSWORD_AND_PASSWORD_CONFIRM_NOT_MATCH,
              Status.BAD_REQUEST);
    }
}

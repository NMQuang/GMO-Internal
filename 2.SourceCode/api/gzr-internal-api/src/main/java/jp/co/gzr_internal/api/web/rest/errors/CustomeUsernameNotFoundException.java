package jp.co.gzr_internal.api.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import jp.co.gzr_internal.api.web.rest.commons.MessageContants;

/**
 * The Class CustomeUsernameNotFoundException.
 */
public class CustomeUsernameNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public CustomeUsernameNotFoundException() {
        super(ErrorConstants.EMAIL_NOT_FOUND_TYPE, MessageContants.CONST_ERROR_CODE_EMAIL_OR_PASSWORD_NOT_MATCH, Status.UNAUTHORIZED);
    }
}

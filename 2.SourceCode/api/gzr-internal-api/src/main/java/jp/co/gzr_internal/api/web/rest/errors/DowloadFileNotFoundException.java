package jp.co.gzr_internal.api.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import jp.co.gzr_internal.api.web.rest.commons.MessageContants;

public class DowloadFileNotFoundException extends AbstractThrowableProblem {

    /** Default serial version UID */
    private static final long serialVersionUID = 1L;
    
    public DowloadFileNotFoundException() {
        super(ErrorConstants.DEFAULT_TYPE, MessageContants.CONST_DOWNLOAD_FILE_FAILURE, Status.NOT_FOUND);
    }
}

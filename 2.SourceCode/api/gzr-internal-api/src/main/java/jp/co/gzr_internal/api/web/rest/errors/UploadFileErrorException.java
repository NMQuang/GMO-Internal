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
 * The class UploadFileErrorException will throw when saving file on server get error.
 *
 * @author datnt
 */
public class UploadFileErrorException extends AbstractThrowableProblem {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new upload file error exception.
     */
    public UploadFileErrorException() {
        super(ErrorConstants.DEFAULT_TYPE, MessageContants.CONST_UPLOAD_FILE_FAILURE, Status.INTERNAL_SERVER_ERROR);
    }
}

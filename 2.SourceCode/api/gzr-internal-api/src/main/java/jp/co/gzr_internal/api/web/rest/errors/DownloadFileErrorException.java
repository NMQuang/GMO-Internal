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
 * The DownloadFileErrorException class will throw when can't download file or the file is not found.
 *
 * @author datnt
 */
public class DownloadFileErrorException extends AbstractThrowableProblem {

    /** Default serial version UID */
    private static final long serialVersionUID = 1L;

    public DownloadFileErrorException() {
        super(ErrorConstants.DEFAULT_TYPE, MessageContants.CONST_DOWNLOAD_FILE_FAILURE, Status.NOT_FOUND);
    }
}

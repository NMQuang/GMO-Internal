/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * The Class ApiResponseUtil.
 */
@JsonSerialize
@JsonInclude(Include.NON_NULL)
public class ApiResponseUtil {

    /** The result data. */
    @JsonProperty(value = "resultData", index = 1)
    private Object resultData;

    /** The error code. */
    @JsonProperty(value = "errorCode", index = 2)
    private String errorCode;

    /** The message. */
    @JsonProperty(value = "message", index = 3)
    private String message;

    /** The error data. */
    @JsonProperty(value = "errorData", index = 4)
    private List<?> errorData;

    /**
     * Gets the result data.
     *
     * @return the result data
     */
    public Object getResultData() {
        return resultData;
    }


    /**
     * Sets the result data.
     *
     * @param resultData the new result data
     */
    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }

    /**
     * Gets the error code.
     *
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the error code.
     *
     * @param errorCode the new error code
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message the new message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the error data.
     *
     * @return the error data
     */
    public List<?> getErrorData() {
        return errorData;
    }

    /**
     * Sets the error data.
     *
     * @param errorData the new error data
     */
    public void setErrorData(List<?> errorData) {
        this.errorData = errorData;
    }

}

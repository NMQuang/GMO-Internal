/*
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *
 * Author: TuanLD
 * Creation Date : Apr 19, 2019	
 */
package jp.co.gzr_internal.api.web.rest.errors;

import org.springframework.security.core.AuthenticationException;

/**
 * The Class UserNotLoginException will throw when can't get data from token.
 * 
 * @author TuanLD
 */
public class UserNotLoginException extends AuthenticationException{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new user not login exception.
     *
     * @param msg the msg
     */
    public UserNotLoginException(String msg) {
        super(msg);
    }

}

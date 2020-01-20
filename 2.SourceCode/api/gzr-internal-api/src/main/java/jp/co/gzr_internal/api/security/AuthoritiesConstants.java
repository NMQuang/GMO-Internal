/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    /** The Constant ADMIN. */
    public static final String ADMIN = "ROLE_ADMIN";

    /** The Constant USER. */
    public static final String USER = "ROLE_USER";

    /** The Constant ANONYMOUS. */
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}

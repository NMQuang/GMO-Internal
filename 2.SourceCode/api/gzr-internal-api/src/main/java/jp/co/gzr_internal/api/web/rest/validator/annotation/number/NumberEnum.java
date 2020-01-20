/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.validator.annotation.number;

/**
 * The NumberEnum.
 *
 * @author datnt
 */
public enum NumberEnum {

    /** The unsigned integer. */
    UNSIGNED_INTEGER("^[0-9]{1,8}$"),

    /** The signed integer. */
    SIGNED_INTEGER("^([+-]{1})?([0-9]{1,8})*$"),

    UNSIGNED_INTEGER_GREATER_THAN_0("^[1-9]([0-9]?){1,7}$"),

    NUMBER_FLOAT("^([-]?[0-9]+([.][0-9])?)$");

    private String regex;

    NumberEnum(String regex) {

        this.regex = regex;
    }
    
    @Override
    public String toString() {
        return regex;
    }
}

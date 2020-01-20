/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.validator;

/**
 * The Class StringValidation.
 */
public class StringValidation {

    /**
     * Check if the string input is null.
     *
     * @param str the string want to check.
     *
     * @return true if the string input is null. Else return false.
     */
    public static boolean isNull(String str) {
        return str == null;
    }

    /**
     * Check if the string input is empty.
     *
     * @param str the string want to check.
     *
     * @return true if the string input is not null and empty. Else return false;
     */
    public static boolean isEmpty(String str) {
        return !isNull(str) && str.length() == 0;
    }

    /**
     * Check if the string is null or empty.
     *
     * @param str the string want to check.
     *
     * @return true if the string is null or empty. Else return false.
     */
    public static boolean isNullOrEmpty(String str) {
        return isNull(str) || isEmpty(str);
    }

    /**
     * Checks if is null or empty.
     *
     * @param strings the strings
     * @return true, if is null or empty
     */
    public static boolean isNullOrEmpty(String...strings) {

        boolean isNullOrEmptyAll = false;

        if (strings.length == 0) {
            return true;
        }

        for (String s : strings) {
            if (isNullOrEmpty(s)) {
                isNullOrEmptyAll = true;
            }
        }

        return isNullOrEmptyAll;
    }

    /**
     * Check if the string is whitespace or empty or null.
     *
     * @author datnt
     * @param strings the strings
     * @return true if the string is whitespace or empty or null. else return false.
     */
    public static boolean isBlank(String...strings) {

        boolean isBlank = true;

        String blankRegex = "^[\u0009\u0020\u3000]*$";

        if (strings == null || strings.length == 0) {
            return isBlank;
        }

        for (String s : strings) {
            if (!(isNullOrEmpty(s) || s.matches(blankRegex))) {
                isBlank = false;
            }
        }

        return isBlank;
    }

    /**
     * Check if length of the string input is valid.
     *
     * @param str the string want to check.
     * @param minLength the min length of the string.
     * @param maxLength the max length of the string.
     *
     * @return true if length of the string valid.<br>
     *         false if the string is null or empty or invalid.
     */
    public static boolean isLengthValid(String str, int minLength, int maxLength) {

        if (isNullOrEmpty(str) || minLength < 0 || maxLength < 0) {
            return false;
        }

        return ((str.length() >= minLength) && (str.length() <= maxLength));
    }

    /**
     * Check if the string input is just contain single-byte character.
     *
     * @param str the string want to check.
     *
     * @return true if the string is just contain single-byte character.
     *         false if the string is null or empty or invalid.
     */
    public static boolean isSingleByteCharOnly(String str) {

        String regex = "^[\\u0000-\\u00ff]*$";

        if (isNullOrEmpty(str)) {
            return false;
        }

        return str.matches(regex);
    }

    /**
     * Check if the string input is just contain double-byte character.
     *
     * @param str the string want to check.
     *
     * @return true if the string is just contain double-byte character.
     *         false if the string is null or empty or invalid.
     */
    public static boolean isDoubleByteCharOnly(String str) {

        String regex = "^[\\u0100-\\uffff]*$";

        if (isNullOrEmpty(str)) {
            return false;
        }

        return str.matches(regex);
    }

    /**
     * Checks if is number.
     *
     * @param str the str
     * @return true, if is number
     */
    public static boolean isNumber(String str) {

        String regex = "^[0-9]*$";

        if (isBlank(str)) {
            return false;
        }

        return str.matches(regex);
    }

    /**
     * To check the string input is matches with pattern input.
     *
     * @author datnt
     * @param str the str
     * @param pattern the pattern
     * @return true if the string input is matches with pattern, else return false.
     */
    public static boolean checkValidWithPattern(String str, String pattern) {

        if (isNullOrEmpty(str, pattern)) {
            return false;
        }

        return str.matches(pattern);
    }
}

/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * The class DataValidation contain methods to check format date time.
 *
 * @author datnt
 */
public class DateValidation {

    /**
     * Check if the string is valid date format with pattern.
     *
     * @author datnt
     * @param str the string want to check.
     * @param pattern the pattern to format.
     *
     * @return true if the string input is valid date format.
     *         false if not.
     */
    public static boolean isDateValid(String str, String pattern) {

        if (StringValidation.isNullOrEmpty(str) || StringValidation.isNullOrEmpty(pattern)
            || str.length() != pattern.length()) {
            return false;
        }

        try {

            DateFormat dateFormat = new SimpleDateFormat(pattern);
            dateFormat.setLenient(false);
            dateFormat.parse(str);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}

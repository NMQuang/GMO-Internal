/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.validator.common;

import java.util.regex.Pattern;

/**
 * The Class ValidateCommon.
 *
 * @author VuDA
 */
public class ValidateCommon {

    /** The Constant EMAIL_REGEX. */
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@"
        + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";

    /** The Constant PASSWORD_REGEX. */
    public static final String PASSWORD_REGEX = "^[a-zA-Z0-9@%+\\/'!#$^?:.(){}\\[\\]~\\-_.]*$";

    /** The Constant SEX_REGEX. */
    public static final String SEX_REGEX = "^[123]{1}$";

    /** The Constant NUMBER_REGEX. */
    public static final String NUMBER_REGEX = "^\\d*$";

    /** The Constant BIRTHDAY_REGEX. */
    public static final String BIRTHDAY_REGEX = "^[0-9]{4}(/)(((0)[1-9])|((1)[0-2]))(/)"
        + "([0-2][1-9]|[1-2][0-9]|(3)[0-1])$";

    /** The Constant ZIP_CODE_REGEX. */
    public static final String ZIP_CODE_REGEX = "^[0-9]{0,7}$";

    /** The Constant USER_STATUS_REGEX. */
    public static final String USER_STATUS_REGEX = "^[12]{1}$";

    /** The Constant TAG_HTML_START_REGEX. */
    public final static String TAG_HTML_START_REGEX = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)\\>";

    /** The Constant TAG_HTML_END_REGEX. */
    public final static String TAG_HTML_END_REGEX = "\\</\\w+\\>";

    /** The Constant TAG_SELF_CLOSING_REGEX. */
    public final static String TAG_SELF_CLOSING_REGEX = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)/\\>";

    /** The Constant HTML_ENTITY_REGEX. */
    public final static String HTML_ENTITY_REGEX = "&[a-zA-Z][a-zA-Z0-9]+;";

    /** The Constant HTML_REGEX. */
    public final static Pattern HTML_REGEX = Pattern.compile("(" + TAG_HTML_START_REGEX + ".*" + TAG_HTML_END_REGEX
        + ")|(" + TAG_SELF_CLOSING_REGEX + ")|(" + HTML_ENTITY_REGEX + ")", Pattern.DOTALL);

    /** The Constant PHONE_REGEX. */
    public static final String PHONE_REGEX = "^[0-9]*$";

    /** The Constant INDUSTRY_REGEX. */
    public static final String INDUSTRY_REGEX = "^([1-9])|(1[0-9])|(2[0-3])$";
    
    public static final String SCALE_REGEX = "(^[1-3]$)";

    /** The Constant USER_UPDATE_TIME_REGEX. */
    public static final String USER_UPDATE_TIME_REGEX = "^(\\d{4})(-)((0)[1-9]|(1)[0-2])(-)((0)[1-9]|[1-2][0-9]|(3)[0-1])([ ]{1})"
        + "([0-1][0-9]|(2)[0-4])(:)([0-5][0-9])(:)([0-5][0-9])(.)[0-9]{1}$";

    /** The Constant GRADUATION_REGEX. */
    public static final String GRADUATION_REGEX = "^[0-9]{4}(/)(((0)[1-9])|((1)[0-2]))$";

    /** The Constant FILE_DATA_REGEX. */
    public static final String FILE_DATA_REGEX = "^[\\w\\d/+=.;,:-]*$";

    /** The Constant FILE_NAME_REGEX. */
    public static final String FILE_NAME_REGEX = "^[^\\u005C\\u002F\\u003A\\u002A\\u003F\\u0022\\u003C\\u003E\\u007C]{1,250}$";

    /** The Constant FILE_EXTENSION_REGEX. */
    public static final String FILE_EXTENSION_REGEX = "^(pdf)|(doc)|(docx)|(xls)|(xlsx)|(ppt)|(pptx)$";
    
    public static final String IMG_EXTENSION_REGEX = "^(jpg)|(JPG)|(JPEG)|(png)|(PNG)$";

    /** The Constant EMOJ_REGEX. */
    public static final String EMOJ_REGEX = "^[一-龯ぁ-んァ-ン！：／ｧ-ﾝﾞﾟ０-９Ａ-ｚ。（ ）\\s\\w+^~`!@#$%^&()_={}\\[\\]:;,.<>+*\\\\/?\\-ー\"'（「～`！＠＃＄＾＆＊（）＿＝｛｝「￥／」：；’”＜＞＋－・、．）〈〉【】《》〔〕？々]+$";

    public static final String DATE_TIME_REGEX                    = "^[0-9]{4}(/)(((0)[1-9])|((1)[0-2]))(/)([0-2][1-9]|[1-2][0-9]|(3)[0-1]) (00|0[0-9]|1[0-9]|2[0-3])$";
    /** The Constant SINGLE_BYTE_CHARACTER. */
    public static final String SINGLE_BYTE_CHARACTER = "^[\\u0000-\\u00ff]*$";

    public static final String DATE_REGEX = "^[0-9]{4}(-)(((0)[1-9])|((1)[0-2]))(-)"
                                            + "([0-2][1-9]|[1-2][0-9]|(3)[0-1])$";

    public static final String BLOCK_SPECIAL_CHARACTER = "^[^~`!@#$%^&()_={}[\\\\]:;,.<>+\\/?-]*$";

    public static final String URL_REGEX = "^https?://.+$";
    
    public static final String  PATTERN_OFFER_KBN = "^[1|2|3|9]$";
    
    public static final String PATTERN_MAIL_FLAG = "^[1|2]$";
    
    /** The Constant AREA_REGEX. */
    public static final String AREA_REGEX = "^([1-9])|(1[0-9])|(2[0-9])|(3[0-9])|(4[0-7])$";
    
    /** The Constant HOPE_REGEX. */
    public static final String HOPE_REGEX = "^([1-9])|(1[0-9])|(2[0-9])|(3[0-9])|(4[0-8])$";
    /**
     * Check is valid XSS.
     *
     * @param value the value
     * @return true, if successful
     */
    public static boolean checkIsValidXSS(String value) {

        if (value != null && !value.isEmpty() && HTML_REGEX.matcher(value).find()) {
            return false;
        }
        return true;
    }

    public static final String YYYY_SLASH_MM_PATTERN = "^([0-9]{4}(\\/)((0)[1-9]{1}|(1)[0-2]{1}))?$";
    
    /** The Constant CONTENT_STATUS_REGEX. */
    public static final String CONTENT_STATUS_REGEX = "(^[1-3]$)";
    
    /** The Constant CONTENT_COMP_MAIL_FLAG. */
    public static final String CONTENT_COMP_MAIL_FLAG = "(^[1-2]$)";
}

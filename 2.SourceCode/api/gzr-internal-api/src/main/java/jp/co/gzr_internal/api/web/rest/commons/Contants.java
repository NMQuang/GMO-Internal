/*  
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *  
 * Author: VuDA 
 * Creation Date : Apr 11, 2019 
 */
package jp.co.gzr_internal.api.web.rest.commons;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * The Class Contants.
 */
public class Contants {

    /** The Constant CONST_STR_RESPONSE. */
    public static final String CONST_STR_RESPONSE = "response";

    /** The Constant CONST_STR_ANGLE_BRACKET. */
    public static final String CONST_STR_ANGLE_BRACKET = "{";

    /** The Constant CONST_STR_ERROR_CODE. */
    public static final String CONST_STR_ERROR_CODE = "errorCode";

    /** The Constant CONST_STR_ITEM_NAME_MESSAGE MUser. */
    public final static String CONST_STR_ITEM_NAME_MESSAGE = "{itemName}";

    /** The Constant CONST_STR_BLANK. */
    public static final String CONST_STR_BLANK = "";

    /** The Constant CONST_STR_TIMEZONE_JP. */
    public static final String CONST_STR_TIMEZONE_VN = "Asia/Ho_Chi_Minh";

    /** The Constant CONST_STR_ENCODING_UTF8. */
    public static final String CONST_STR_ENCODING_UTF8 = "UTF-8";

    /** The Constant CONST_STR_PATTERN. */
    public static final String CONST_STR_PATTERN = "pattern";

    /** The Constant CONST_STR_REGEX. */
    public final static String CONST_STR_REGEX = "regex";

    /** The Constant CONT_STR_FALSE. */
    public static final String CONT_STR_FALSE = "false";

    /** The Constant CONST_STR_MAX. */
    public final static String CONST_STR_MAX = "max";

    /** The Constant CONST_STR_MIN. */
    public final static String CONST_STR_MIN = "min";

    /** The Constant CONST_STR_MAX_LENGTH. */
    public static final String CONST_STR_MAX_LENGTH = "maxLength";

    /** The constant CONST_STR_NUMBER_TYPE to define type parameter for Number annotation. */
    public final static String CONST_STR_TYPE = "type";

    /** The Constant CONST_STR_MIN_LENGTH. */
    public static final String CONST_STR_MIN_LENGTH = "minLength";

    /** The Constant CONST_STR_PATTERN_YYYYMMDD */
    public final static String CONST_STR_PATTERN_YYYYMMDD = "yyyy-MM-dd";

    /** flag delete_flg with value is false. */
    public static final Integer CONST_INT_DELETE_FLAG_FALSE = 0;

    /** The Constant CONST_STR_DEFAULT_STATUS MUser. */
    public final static Integer CONST_STR_DEFAULT_STATUS = 1;

    /** The Constant CONST_STR_PATTERN_UPDATE_TIME. */
    public static final String CONST_STR_PATTERN_UPDATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /** The Constant CONST_STR_BEARER. */
    public static final String CONST_STR_BEARER = "Bearer ";

    /** The Constant AUTHORIZATION_HEADER. */
    public static final String AUTHORIZATION_HEADER = "Authorization";

    /** The Constant CONST_STR_HASH_TAG. */
    public static final String CONST_STR_HASH_TAG = "#";

    /** The Constant CONST_STR_PATTERN_YYYYMMDDHHMMSS. */
    public static final String CONST_STR_PATTERN_YYYYMMDDHHMMSS = "yyyy/MM/dd HH:mm:ss";

    /** The Constant CONST_STR_ZERO. */
    public static final String CONST_STR_ZERO = "0";

    /** The Constant CONST_STR_CONTENT_TYPE_JSON. */
    public static final String CONST_STR_CONTENT_TYPE_JSON = "application/json";

    /** The Constant CONST_STR_TEMPLATE_CREATION. */
    public static final String CONST_STR_TEMPLATE_CREATION = "mail/creationEmail";

    /** The Constant CONST_STR_TEMPLATE_PASSWORD_RESET. */
    public static final String CONST_STR_TEMPLATE_PASSWORD_RESET = "mail/passwordResetEmail";

    /** The Constant CONST_STR_TEMPLATE_REQUEST_OT. */
    public static final String CONST_STR_TEMPLATE_REQUEST_OT = "mail/requestOTEmail";
    
    public static final String CONST_STR_TEMPLATE_CREATE_PROJECT = "mail/createProjectEmail";

    public static final String CONST_STR_TEMPLATE_APPROVAL_OT = "mail/approvalOTEmail";
    
    public static final String CONST_STR_TEMPLATE_VERIFY_OT = "mail/verifyOTEmail";
    
    /** The Constant CONST_STR_TITLE_PASSWORD_RESET. */
    public static final String CONST_STR_TITLE_PASSWORD_RESET = "Reset Your Password";
    
    /** The Constant CONST_STR_TITLE_REQUEST_OT. */
    public static final String CONST_STR_TITLE_REQUEST_OT = "Request overtime for project ";

    public static final String CONST_STR_TITLE_APPROVAL_OT = "Approved request overtime for project ";
    
    public static final String CONST_STR_TITLE_VERIFY_OT = "Verify request overtime for project ";
    
    /** The Constant CONST_STR_MAIL_INFO. */
    public static final String CONST_STR_MAIL_INFO = "mailInfo";

    /** The Constant CONST_STR_EMPLOYEE_NAME. */
    public static final String CONST_STR_EMPLOYEE_NAME = "employeeName";

    /** The Constant CONST_STR_NEW_PASSWORD. */
    public static final String CONST_STR_NEW_PASSWORD = "newPassword";

    public static final String CONST_STR_PERCENT_SIGN                                                           = "%";
    
    public static final String CONST_STR_OPTION_SEARCH                                                          = "1";
    
    public static final String CONST_STR_OPTION_EXPORT                                                          = "0";
    
    public static final String CONST_STR_OPTION_EXPORT_ALL_COLUMN                                               = "1";
    
    public static final String CONST_STR_OPTION_EXPORT_SPECIFIED_COLUMN                                         = "0";
    
    public static final LinkedHashMap<String, List<String>> CONST_STR_HEADER = initialHeader();

    private static LinkedHashMap<String, List<String>> initialHeader() {
        LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();
        map.put("Mã nhân viên (*)", Arrays.asList("employee_code", "employeeCode"));
        map.put("Họ và tên (*)", Arrays.asList("employee_name", "employeeName"));
        map.put("Giới tính", Arrays.asList("gender", "gender"));
        map.put("Ngày sinh", Arrays.asList("birthday", "birthday"));
        map.put("Chỗ ở hiện nay", Arrays.asList("address", "address"));
        map.put("ĐT di động", Arrays.asList("phone_number", "phoneNumber"));
        map.put("Email cơ quan", Arrays.asList("email", "email"));
        map.put("Nơi sinh", Arrays.asList("birthplace", "birthplace"));
        map.put("Tỉnh/Thành phố (hộ khẩu thường trú)", Arrays.asList("province_pr", "provincePr"));
        map.put("Quận/Huyện (Hộ khẩu thường trú)", Arrays.asList("district_pr", "districtPr"));
        map.put("Xã/Phường (Hộ khẩu thường trú)", Arrays.asList("ward_pr", "wardPr"));
        map.put("Quốc gia (chỗ ở hiện nay)", Arrays.asList("nation_ca", "nationCa"));
        map.put("Quận/Huyện (Chỗ ở hiện nay)", Arrays.asList("district_ca", "districtCa"));
        map.put("Xã/Phường (Chỗ ở hiện nay)", Arrays.asList("ward_ca", "wardCa"));
        map.put("Ngày thử việc", Arrays.asList("probationary_day", "probationaryDay"));
        map.put("Ngày chính thức", Arrays.asList("official_day", "officialDay"));
        map.put("Địa điểm làm việc", Arrays.asList("work_location", "workLocation"));
        map.put("Loại hợp đồng", Arrays.asList("type_contact_name", "typeContract"));
        map.put("Trạng thái lao động (*)", Arrays.asList("unit_name", "status"));
        map.put("Lý do nghỉ", Arrays.asList("reason_leave", "reasonLeave"));
        map.put("Ngày nghỉ việc", Arrays.asList("day_off", "dayOff"));
        map.put("Tên vị trí công việc", Arrays.asList("division_name", "division"));
        map.put("Tên đơn vị công tác", Arrays.asList("position_name", "position"));
        
        return map;
    }
    
    
    public static final String CONST_STR_PATTERN_DDMMYYYY                                                       = "dd/MM/yyyy";
    
    public final static Integer CONST_ROLE_ADMIN = 1;
    
    public final static Integer CONST_ROLE_SUB_ADMIN = 2;
    
    public final static Integer CONST_ROLE_USER = 3;
    
    public final static Integer CONST_ROLE_QA = 4;
    
    public final static Integer CONST_PROJECT_TEAMLEAD = 6;
    
    public final static Integer CONST_PROJECT_BRSE = 4;
    
    public final static Integer CONST_PROJECT_PROJECTMANAGEMENT = 3;
}

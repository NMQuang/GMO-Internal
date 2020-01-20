/*
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VuDA
 * Creation Date : Apr 11, 2019
 */
package jp.co.gzr_internal.api.web.rest.util;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

import jp.co.gzr_internal.api.security.SecurityUtils;
import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.commons.ResponseCommon;
import jp.co.gzr_internal.api.web.rest.errors.UserNotLoginException;
import jp.co.gzr_internal.api.web.rest.validator.StringValidation;

// TODO: Auto-generated Javadoc
/**
 * The Class Utils.
 *
 * @author datnt
 */
public class Utils
{
    /**
     * Get timezone of JP.
     *
     * @param pattern the pattern
     * @return the full current date string
     */
    public static String getCuretntDateString(String pattern) {

        String currentDay = Contants.CONST_STR_BLANK;

        if (pattern != null && !pattern.isEmpty()) {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(Contants.CONST_STR_TIMEZONE_VN));
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            sdf.setTimeZone(TimeZone.getTimeZone(Contants.CONST_STR_TIMEZONE_VN));
            currentDay = sdf.format(cal.getTime());
        }

        return currentDay;
    }

    /**
     * Get time current.
     *
     * @param pattern the pattern
     * @return the full current date string
     * @throws Exception the exception
     */
    public static LocalDateTime getFullCurrentDateString(String pattern) throws Exception {
        String currentDay = Contants.CONST_STR_BLANK;
        LocalDateTime dateTime = null;
        if (pattern != null && !pattern.isEmpty()) {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(Contants.CONST_STR_TIMEZONE_VN));
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            sdf.setTimeZone(TimeZone.getTimeZone(Contants.CONST_STR_TIMEZONE_VN));
            currentDay = sdf.format(cal.getTime());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            dateTime = LocalDateTime.parse(currentDay, formatter);
        }
        return dateTime;
    }

    /**
     * Convert string to date.
     *
     * @author GiangTT
     *
     * @param str the string want to convert.
     * @param pattern the pattern will be convert string to,
     *
     * @return date
     */
    public static Date stringToDate(String str, String pattern) {

        if (StringValidation.isNullOrEmpty(str, pattern)) {
            return null;
        }

        try {

            DateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.parse(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDateTime stringToLocalDateTime(String datetime, String pattern) {
        if (StringValidation.isNullOrEmpty(datetime, pattern)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.parse(datetime, formatter);
        return localDateTime;
    }
    
    public static LocalDateTime timeStampToLocalDateTime(Timestamp datetime, String pattern) {
        return LocalDateTime
            .ofInstant(datetime.toInstant(), ZoneId.of(Contants.CONST_STR_TIMEZONE_VN));
    }
    
    /**
     * Get timezone of JP.
     *
     * @param pattern the pattern
     * @return the full current date string
     */
    public static Date getCurrentDate(String pattern) {

        Date date = null;
        if (pattern != null && !pattern.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            try {
                date = sdf.parse(getCuretntDateString(pattern));
            } catch (ParseException e) {
            }
        }
        return date;
    }

    /**
     * Gets the message with code.
     *
     * @author GiangTT
     * @param messageCode the message code
     * @return the message with code
     * @throws Exception the exception
     */
    public static String getMessageWithCode(String messageCode) throws Exception {

        InputStream utf8in = ResponseCommon.class.getClassLoader().getResourceAsStream("i18n/messages.properties");
        Reader reader = new InputStreamReader(utf8in, Contants.CONST_STR_ENCODING_UTF8);
        Properties props = new Properties();
        props.load(reader);

        return props.getProperty(messageCode);
    }


    /**
     * Compare current date.
     *
     * @param date the date
     * @param pattern the pattern
     * @return true, if successful
     */
    public static boolean compareCurrentDate(String date, String pattern) {
        Date dateFrom = stringToDate(date, pattern);
        Date dateNow = getCurrentDate(pattern);
        if (dateFrom.compareTo(dateNow) < 0) {
            return false;
        }
        return true;
    }

    /**
     * Convert date to string.
     *
     * @param date the date
     * @param pattern the pattern
     * @return the string
     */
    public static String convertDateToString(Date date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        String strDate = dateFormat.format(date);
        return strDate;
    }

    /**
     * Gets the user id from credential.
     *
     * @return the user id from credential
     */
    public static String getEmployeeCodeFromLogin() {
        Optional<String> credentials = SecurityUtils.getCurrentUserLogin();
        if (credentials.isPresent()) {
            String[] arrs = credentials.get().split(Contants.CONST_STR_HASH_TAG);

            if (arrs != null && arrs.length >= 1) {
                return arrs[0];
            }
            return Contants.CONST_STR_BLANK;
        } else {
            throw new UserNotLoginException("User not login exception.");
        }
    }
    
    /**
     * Gets the role from credential.
     *
     * @return the role from credential
     */
    public static Integer getRoleFromLogin() {
        Optional<String> credentials = SecurityUtils.getCurrentUserLogin();
        if (credentials.isPresent()) {
            String[] arrs = credentials.get().split(Contants.CONST_STR_HASH_TAG);
            if (arrs != null && arrs.length >= 1) {
                return Integer.valueOf(arrs[1]);
            }
            return null;
        } else {
            throw new UserNotLoginException("User not login exception.");
        }
    }
    
    /**
     * Gets the mail from credential.
     *
     * @return the mail from credential
     */
    public static String getEmailFromLogin() {
        Optional<String> credentials = SecurityUtils.getCurrentUserLogin();
        if (credentials.isPresent()) {
            String[] arrs = credentials.get().split(Contants.CONST_STR_HASH_TAG);
            if (arrs != null && arrs.length >= 2) {
                return arrs[2];
            }
            return null;
        } else {
            throw new UserNotLoginException("User not login exception.");
        }
    }
    
    public static String getValueOfFile(Cell cell) {

        String value = Contants.CONST_STR_BLANK;
        switch (cell.getCellType()) {
        case STRING:
            value = cell.getStringCellValue();
            break;
        case NUMERIC:
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                value = Utils.convertDateToString(cell.getDateCellValue(),Contants.CONST_STR_PATTERN_YYYYMMDD);
            } else {
                value = String.valueOf(Integer.valueOf((int) cell.getNumericCellValue()).intValue());
            }
            break;
        default:
            value = cell.getStringCellValue();
        }
        return value;
    }
    
    public static long getUnallocateSpace(String dir) {

        if (StringValidation.isNullOrEmpty(dir)) {
            return 0;
        }

        try {

            Path path = Paths.get(dir);

            FileStore fileStore = Files.getFileStore(path);

            return fileStore.getUnallocatedSpace();

        } catch (Exception e) {
            return 0;
        }
    }
    
    public static boolean uploadFile(String parent, String fileName, byte[] bytes) {

        if (StringValidation.isBlank(parent, fileName) || bytes == null || bytes.length == 0) {
            return false;
        }

        try {
            Path path = Paths.get(parent, fileName);

            File folder = new File(parent);

            if (!folder.isDirectory()) {
                folder.mkdirs();
            }

            if (bytes.length < getUnallocateSpace(parent)) {
                FileUtils.writeByteArrayToFile(path.toFile(), bytes);

                if (!System.getProperty("os.name").toLowerCase().contains("win")) {
                    // using PosixFilePermission to set file permissions 777
                    Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();

                    // add others permissions
                    perms.add(PosixFilePermission.OTHERS_READ);
                    perms.add(PosixFilePermission.OTHERS_WRITE);
                    perms.add(PosixFilePermission.OTHERS_EXECUTE);

                    Files.setPosixFilePermissions(Paths.get(parent), perms);
                    Files.setPosixFilePermissions(path, perms);

                    return path.toFile().isFile();
                }

                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
    

    
    public static LocalDate convertStringToLocalDate(String date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(date, formatter);
    }
    
    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now(ZoneId.of(Contants.CONST_STR_TIMEZONE_VN));
    }
    
    // Function to add x in arr 
    public static String[] addX(int n, String arr[], String x) 
    { 
        int i; 
  
        // create a new array of size n+1 
        String newarr[] = new String[n + 1]; 
  
        // insert the elements from 
        // the old array into the new array 
        // insert all elements till n 
        // then insert x at n+1 
        for (i = 0; i < n; i++) 
            newarr[i] = arr[i]; 
  
        newarr[n] = x; 
  
        return newarr; 
    }
    
    public static String upperCaseWords(String sentence) {
        String words[] = sentence.replaceAll("\\s+", " ").trim().split(" ");
        String newSentence = "";
        for (String word : words) {
            for (int i = 0; i < word.length(); i++)
                newSentence = newSentence + ((i == 0) ? word.substring(i, i + 1).toUpperCase(): 
                    (i != word.length() - 1) ? word.substring(i, i + 1).toLowerCase() : word.substring(i, i + 1).toLowerCase().toLowerCase() + " ");
        }

        return newSentence;
    }
}

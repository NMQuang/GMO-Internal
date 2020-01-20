/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.validator.StringValidation;

/***********************************************************************
 * The PropertiesUtil class to read properties from system.properties.
 *
 * @author datnt
 *
 ***********************************************************************/
@Configuration
@PropertySource(ignoreResourceNotFound = true, value = "classpath:system.properties")
public class PropertiesUtil {

    @Autowired
    private Environment environment;

    /**
     * Get value by key.
     *
     * @author datnt
     * @param key the key
     * @return value
     */
    public String getValue(String key) {

        StringBuilder builder = new StringBuilder();

        builder.append(environment.getProperty(key));

        return builder.toString();
    }

    /**
     * Get value number by key.
     *
     * @author datnt
     * @param key the key
     * @return integer value
     */
    public Integer getNumberValue(String key) {

        String value = environment.getProperty(key);

        if (StringValidation.isNumber(value)) {
            try {
                return Integer.parseInt(value);
            } catch (Exception e) {}
        }

        return 0;
    }

    /**
     * Get list of value by key.
     *
     * @author datnt
     * @param key the key
     * @param regex the regex
     * @return list of value.
     */
    public List<String> getListValue(String key, String regex) {

        List<String> values = new ArrayList<String>();

        if (StringValidation.isNullOrEmpty(key, regex)) {
            return values;
        }

        String value = getValue(key);

        if (!StringValidation.isNullOrEmpty(value)) {
            String[] array = value.split(regex);
            for (String s : array) {
                values.add(s.trim());
            }
        }

        return values;
    }

    /**
     * Get map value by key.
     *
     * @author datnt
     * @param key the key
     * @param regex1 the regex 1
     * @param regex2 the regex 2
     * @return hasmap
     */
    public Map<String, String> getMapValue(String key, String regex1, String regex2) {

        Map<String, String> mapValues = new LinkedHashMap<String, String>();

        if (StringValidation.isNullOrEmpty(key, regex1, regex2)) {
            return mapValues;
        }

        List<String> values = getListValue(key, regex1);

        for (String value : values) {
            String[] array = value.split(regex2);

            if (array.length == 1) {
                mapValues.put(array[0], Contants.CONST_STR_BLANK);
            } else if (array.length == 2) {
                mapValues.put(array[0], array[1]);
            }
        }

        return mapValues;
    }

    /**
     * Gets the majors.
     *
     * @param key the key
     * @param regex1 the regex 1
     * @param regex2 the regex 2
     * @return the majors
     */
    public List<Map<String, String>> getMajors(String key, String regex1, String regex2) {

        String id = "majorId";
        String name = "majorName";

        List<Map<String, String>> majors = new ArrayList<>();

        Map<String, String> majorMap = getMapValue(key, regex1, regex2);

        for (String k : majorMap.keySet()) {

            Map<String, String> m = new HashMap<>();
            m.put(id, k);
            m.put(name, majorMap.get(k));
            majors.add(m);
        }

        return majors;
    }
}

package vn.uni.medico.shared.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class StringUtil {
    public static boolean isNullOrEmpty(final Object value) {
        return value == null || (value instanceof String && ((String) value).isEmpty());
    }

    public static boolean isEmpty(String text) {
        return Objects.isNull(text) || text.trim().length() == 0;
    }

    public static String valueOf(String value, String defaultValue) {
        return StringUtils.isEmpty(value) ? defaultValue : value;
    }

    public static String[] valueOf(String[] value, String[] defaultValue) {
        return value == null || ArrayUtils.isEmpty(value) ? defaultValue : value;
    }

    public static String getFirstName(String fullName) {
        if (StringUtils.isEmpty(fullName)) return "";
        int index = fullName.indexOf(' ');
        if (index > 0) return fullName.substring(0, index);
        return fullName;
    }

    public static String getLastName(String fullName) {
        if (StringUtils.isEmpty(fullName)) return "";
        int index = fullName.indexOf(' ');
        if (index > 0) return fullName.substring(index);
        return "";
    }

//    public static boolean hasNonAsciiCharacter(String value){
//        if(StringUtils.isEmpty(value)) return false;
//        return !value.equalsIgnoreCase(CharMatcher.ascii().retainFrom(value));
//    }
}

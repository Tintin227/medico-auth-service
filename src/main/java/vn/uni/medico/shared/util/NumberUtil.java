package vn.uni.medico.shared.util;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Collections;
import java.util.List;

public class NumberUtil {

    public static Integer valueOf(Integer value, Integer defaultValue) {
        return value == null || value == 0 ? defaultValue : value;
    }

    public static Integer[] valueOf(Integer[] value, Integer[] defaultValue) {
        return value == null || ArrayUtils.isEmpty(value) ? defaultValue : value;
    }

    public static Byte valueOf(Byte value, Byte defaultValue) {
        return value == null || value == 0 ? defaultValue : value;
    }

    public static Byte[] valueOf(Byte[] value, Byte[] defaultValue) {
        return value == null || ArrayUtils.isEmpty(value) ? defaultValue : value;
    }

    public static Short valueOf(Short value, Short defaultValue) {
        return value == null || value == 0 ? defaultValue : value;
    }

    public static Short[] valueOf(Short[] value, Short[] defaultValue) {
        return value == null || ArrayUtils.isEmpty(value) ? defaultValue : value;
    }

    public static Long valueOf(Long value, Long defaultValue) {
        return value == null || value == 0L ? defaultValue : value;
    }

    public static Long[] valueOf(Long[] value, Long[] defaultValue) {
        return value == null || ArrayUtils.isEmpty(value) ? defaultValue : value;
    }

    public static Double valueOf(Double value, Double defaultValue) {
        return value == null || value == 0.0D ? defaultValue : value;
    }

    public static Double[] valueOf(Double[] value, Double[] defaultValue) {
        return value == null || ArrayUtils.isEmpty(value) ? defaultValue : value;
    }

    public static Float valueOf(Float value, Float defaultValue) {
        return value == null || value == 0.0F ? defaultValue : value;
    }

    public static Float[] valueOf(Float[] value, Float[] defaultValue) {
        return value == null || ArrayUtils.isEmpty(value) ? defaultValue : value;
    }

    public static boolean isZero(Integer value) {
        return value == null || value == 0;
    }

    public static boolean isZero(Byte value) {
        return value == null || value == 0;
    }

    public static boolean isZero(Short value) {
        return value == null || value == 0;
    }

    public static boolean isZero(Long value) {
        return value == null || value == 0L;
    }

    public static boolean isZero(Double value) {
        return value == null || value == 0.0D;
    }

    public static boolean isZero(Float value) {
        return value == null || value == 0.0F;
    }

    public static boolean isNullOrZero(final Object value) {
        return value == null || (value instanceof Integer && (Integer) value == 0)
                || (value instanceof Byte && (Byte) value == 0) || (value instanceof Short && (Short) value == 0)
                || (value instanceof Long && (Long) value == 0) || (value instanceof Double && (Double) value == 0)
                || (value instanceof Float && (Float) value == 0);
    }

    public static Float add(Float a, Float b) {
        return (a == null ? 0.0F : a) + (b == null ? 0.0F : b);
    }

    public static Long add(Long a, Long b) {
        return (a == null ? 0L : a) + (b == null ? 0L : b);
    }

    public static Float avg(List<Float> values, Boolean ignoreZero) {
        if (values == null || values.isEmpty())
            return 0.0F;
        Float sum = 0.0F;
        Integer num = 0;
        for (Float value : values) {
            if (!Boolean.TRUE.equals(ignoreZero) || (value != null && value != 0.0F)) {
                sum += value;
                num++;
            }
        }
        return num > 0 ? sum / num : 0.0F;
    }

    public static Float max(List<Float> values, Float ratio) {
        if (values == null || values.isEmpty() || ratio < 0.0F || ratio > 1.0F)
            return 0.0F;
        values.sort(Collections.reverseOrder());
        int size = values.size();
        int index = Math.round(size * (1.0F - ratio));
        return values.get(index >= size ? size - 1 : index);
    }

    public static Float getNormalValue(Float value, Float maxValue) {
        if (maxValue != null && maxValue > 0.0F && value != null && value > 10 * maxValue)
            return 0.0F;
        return value;
    }

    public static boolean isNumeric(String input) {
        try {
            Long.parseLong(input);
            return true;
        } catch (Exception e) {
        }
        return false;
    }
}

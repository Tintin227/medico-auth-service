package vn.uni.medico.shared.util;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListUtil {
    private ListUtil() {
    }

    @SafeVarargs
    public static <T> List<T> concat(List<T>... items) {
        if (items == null || ArrayUtils.isEmpty(items)) return List.of();
        return Arrays.stream(items).flatMap(List::stream).distinct().collect(Collectors.toList());
    }

    public static <T> Boolean isEmpty(List<T> value) {
        return value == null || value.isEmpty();
    }
}

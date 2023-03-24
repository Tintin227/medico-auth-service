package vn.uni.medico.shared.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BinaryUtil {
    public static List<Integer> getBitPositions(String binaryString, Integer byteNumber) {
        if (StringUtils.isEmpty(binaryString)) return new ArrayList<>();
        List<Integer> bitPositions = new ArrayList<>();
        int length = binaryString.length() + (byteNumber == null ? 0 : byteNumber) * 8;
        Matcher m = Pattern.compile("[1]").matcher(binaryString);
        while (m.find()) {
            bitPositions.add(length - m.start() - 1);
        }
        return bitPositions;
    }

    public static Integer getFirstBitPosition(String binaryString, Integer byteNumber) {
        if (StringUtils.isEmpty(binaryString)) return null;
        int length = binaryString.length() + (byteNumber == null ? 0 : byteNumber) * 8;
        Matcher m = Pattern.compile("[1]").matcher(binaryString);
        if (m.find()) {
            return length - m.start() - 1;
        }
        return null;
    }

    public static List<Integer> getBitPositions(String[] binaryStrings) {
        if (ArrayUtils.isEmpty(binaryStrings)) return new ArrayList<>();
        List<Integer> bitPositions = new ArrayList<>();
        for (int i = 0; i < binaryStrings.length; i++) {
            bitPositions.addAll(getBitPositions(binaryStrings[i], binaryStrings.length - i - 1));
        }
        return bitPositions;
    }

    public static Integer getFirstBitPosition(String[] binaryStrings) {
        if (ArrayUtils.isEmpty(binaryStrings)) return null;
        for (int i = 0; i < binaryStrings.length; i++) {
            Integer firstBitPosition = getFirstBitPosition(binaryStrings[i], binaryStrings.length - i - 1);
            if (firstBitPosition != null) return firstBitPosition;
        }
        return null;
    }
}

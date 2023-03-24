package vn.uni.medico.shared.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ErrorBuilder {
    private static final String INPUT_REQUIRED = "INPUT_REQUIRED";
    private static final String WRONG_FORMAT = "WRONG_FORMAT";
    private static final String INPUT_EXISTED = "INPUT_EXISTED";


    private final Map<String, List<String>> errors = new HashMap<>();

    public static String buildErrorMessage(Map<String, List<String>> errors, Map<String, String> fieldAliases, Map<String, String> errorCodes) {
        if (errors == null || errors.isEmpty()) return "";
        StringBuilder errorMessageBuilder = new StringBuilder();
        errors.forEach((errorCode, fieldNames) -> {
            String errorCodeFormat = errorCodes.getOrDefault(errorCode, errorCode + ": %s");
            errorMessageBuilder.append(String.format(errorCodeFormat, fieldNames.stream().map(v -> fieldAliases.getOrDefault(v, v)).collect(Collectors.joining(", "))));
            errorMessageBuilder.append("; ");
        });

        return errorMessageBuilder.toString();
    }

    public Map<String, List<String>> build() {
        return errors;
    }

    public String buildErrorMessage(Map<String, String> fieldAliases, Map<String, String> errorCodes) {
        return buildErrorMessage(errors, fieldAliases, errorCodes);
    }

    public void validateInputRequired(String value, String fieldName) {
        if (StringUtils.isEmpty(value)) {
            putInputRequired(fieldName);
        }
    }

    public void putInputRequired(String fieldName) {
        put(INPUT_REQUIRED, fieldName);
    }

    public void putWrongFormat(String fieldName) {
        put(WRONG_FORMAT, fieldName);
    }

    public void putInputExisted(String fieldName) {
        put(INPUT_EXISTED, fieldName);
    }

    public void put(String errorCode, String fieldName) {
        List<String> fieldNames = errors.getOrDefault(errorCode, new ArrayList<>());
        fieldNames.add(fieldName);
        errors.put(errorCode, fieldNames);
    }
}

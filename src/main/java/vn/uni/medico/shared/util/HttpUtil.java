package vn.uni.medico.shared.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {
    private static final String[] IP_HEADERS = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    private HttpUtil() {
    }

    public static Map<String, String> getRequestIpAddresses(HttpServletRequest request) {
        Map<String, String> ipAddresses = new HashMap<>();
        ipAddresses.put("REMOTE_IP_ADDRESS", request.getRemoteAddr());
        for (String header : IP_HEADERS) {
            String value = request.getHeader(header);
            if (value == null || value.isEmpty()) {
                continue;
            }
            String ipAddress = Arrays.stream(value.split("\\s*,\\s*")).findFirst().orElse(null);
            if (!StringUtils.isEmpty(ipAddress)) {
                ipAddresses.put(header, ipAddress);
            }
        }
        return ipAddresses;
    }
}

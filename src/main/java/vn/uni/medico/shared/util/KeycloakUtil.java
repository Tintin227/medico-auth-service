package vn.uni.medico.shared.util;

import org.keycloak.common.util.Base64Url;
import org.keycloak.util.JsonSerialization;

import java.io.IOException;

public class KeycloakUtil {
    public static  <T> T parseToken(String encoded, Class<T> clazz) throws IOException {
        if (encoded == null) {
            return null;
        } else {
            String[] parts = encoded.split("\\.");
            if (parts.length >= 2 && parts.length <= 3) {
                byte[] bytes = Base64Url.decode(parts[1]);
                return JsonSerialization.readValue(bytes, clazz);
            } else {
                throw new IllegalArgumentException("Parsing error");
            }
        }
    }
}

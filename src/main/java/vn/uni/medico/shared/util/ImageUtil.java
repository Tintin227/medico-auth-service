package vn.uni.medico.shared.util;

import org.springframework.http.MediaType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ImageUtil {
//    public static byte[] base64Decode(String base64ImageString) {
//         return Base64.getDecoder()
//                .decode(base64ImageString.getBytes(StandardCharsets.UTF_8));
//    }

    private static final String JPEG_FORMAT = "data:image/jpeg;base64,";
    private static final String PNG_FORMAT = "data:image/png;base64,";

    public static InputStream base64Decode(String base64ImageString) {
        return new ByteArrayInputStream(Base64.getDecoder()
                .decode(getBase64String(base64ImageString).getBytes(StandardCharsets.UTF_8)));
    }

    public static String getBase64String(String base64ImageString) {
        if (base64ImageString.startsWith(JPEG_FORMAT)) return base64ImageString.substring(JPEG_FORMAT.length());
        if (base64ImageString.startsWith(PNG_FORMAT)) return base64ImageString.substring(PNG_FORMAT.length());
        return base64ImageString;
    }

    public static String getMediaType(String base64ImageString) {
        if (base64ImageString.startsWith(JPEG_FORMAT)) return MediaType.IMAGE_JPEG_VALUE;
        if (base64ImageString.startsWith(PNG_FORMAT)) return MediaType.IMAGE_PNG_VALUE;
        return MediaType.IMAGE_JPEG_VALUE;
    }

}

package vn.uni.medico.shared.middleware;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;
import vn.uni.medico.shared.util.JsonUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class RestApiClient {
    private static final okhttp3.MediaType JSON_MEDIA_TYPE
            = okhttp3.MediaType.parse("application/json; charset=utf-8");
    private static final okhttp3.MediaType APPLICATION_FORM_URLENCODED_MEDIA_TYPE
            = okhttp3.MediaType.parse("application/x-www-form-urlencoded");
    //    private static final Logger logger = LoggerFactory.getLogger(RestApiClient.class);
    @Resource
    private okhttp3.OkHttpClient okHttpClient;

    public <T> Response<T> post(String url, Map<String, String> headers, Map<String, String> payload, Class<T> targetType) {
        log.info("[POST] " + url);
        FormBody.Builder builder = new FormBody.Builder();
        payload.forEach(builder::addEncoded);

        okhttp3.Request httpRequest = new okhttp3.Request.Builder()
                .url(url)
                .headers(okhttp3.Headers.of(headers == null || headers.isEmpty() ? new HashMap<>() : headers))
                .post(builder.build())
                .build();
        return execute(httpRequest, targetType);
    }

    public <T> Response<T> post(String url, Map<String, String> headers, Object payload, Class<T> targetType) {
        log.info("[POST] " + url);
        okhttp3.Request httpRequest = new okhttp3.Request.Builder()
                .url(url)
                .headers(okhttp3.Headers.of(headers == null || headers.isEmpty() ? new HashMap<>() : headers))
                .post(okhttp3.RequestBody.create(JSON_MEDIA_TYPE, JsonUtil.toJsonString(payload)))
                .build();
        return execute(httpRequest, targetType);
    }

    public <T> Response<T> get(String url, Map<String, String> headers, Class<T> targetType) {
        log.info("[GET] " + url);
        okhttp3.Request httpRequest = new okhttp3.Request.Builder()
                .url(url)
                .headers(okhttp3.Headers.of(headers == null || headers.isEmpty() ? new HashMap<>() : headers))
                .get()
                .build();
        return execute(httpRequest, targetType);
    }

    private <T> Response<T> execute(okhttp3.Request httpRequest, Class<T> targetType) {
        okhttp3.Response httpResponse = null;
        Response<T> response = new Response<>();
        try {
            httpResponse = okHttpClient.newCall(httpRequest).execute();
            response
                    .setUrl(httpRequest.url().uri().toString())
                    .setMethod(httpRequest.method())
                    .setCode(httpResponse.code())
                    .setMessage(httpResponse.message())
                    .setContent(httpResponse.body() == null ? null : JsonUtil.toObject(Objects.requireNonNull(httpResponse.body()).string(), targetType));

        } catch (Exception e) {
            String error = ExceptionUtils.getStackTrace(e);
            log.error("okhttp post error >> ex = {}", error);
            response
                    .setUrl(httpRequest.url().uri().toString())
                    .setMethod(httpRequest.method())
                    .setCode(500)
                    .setMessage(error)
                    .setContent(null);
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
        }

        log.info(httpRequest.url() + " >> status code: " + response.getCode() + " :: " + response.getMessage());
        return response;
    }

    @lombok.Data
    @lombok.experimental.Accessors(chain = true)
    public static class Response<T> {
        private String url;
        private String method;
        private Integer code;
        private String message;
        private T content;

        public static <T> Response<T> serverError(String message) {
            return new Response<T>().setCode(500).setMessage(message);
        }

        public static <T> Response<T> clientError(String message) {
            return new Response<T>().setCode(400).setMessage(message);
        }

        public boolean isSuccessful() {
            return code != null && code >= 200 && code < 300;
        }

        public boolean isSuccessfulAndHasContent() {
            return code != null && code >= 200 && code < 300 && content != null;
        }
    }
}

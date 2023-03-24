package vn.uni.medico.auth.adapter.out.keycloak.middleware;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vn.uni.medico.auth.adapter.out.keycloak.dto.AuthResponseDto;
import vn.uni.medico.auth.adapter.out.keycloak.dto.UserInfoResponseDto;
import vn.uni.medico.shared.middleware.RestApiClient;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
@Service
@Slf4j
public class OAuthClient {
    @Value("${oauth.token-url}")
    private String tokenUrl;

    @Value("${oauth.client-id}")
    private String clientId;

    @Value("${oauth.client-secret}")
    private String clientSecret;

    @Value("${oauth.scope}")
    private String scope;

    @Value("${oauth.user-info-url}")
    private String userInfoUrl;

    @Value("${oauth.user-logout-url}")
    private String userLogoutUrl;

    @Resource
    private RestApiClient restApiClient;

    public RestApiClient.Response<AuthResponseDto> genToken(Map<String, String> headers, Map<String, String> payload) {
        payload.put("grant_type", payload.containsKey("refresh_token") ? "refresh_token" : "password");
        payload.put("client_id", clientId);
        payload.put("client_secret", clientSecret);
        payload.put("scope", scope);

        return restApiClient.post(tokenUrl, headers, payload, AuthResponseDto.class);
    }

    public RestApiClient.Response<AuthResponseDto> login(String username, String password) {
        Map<String, String> payload = new HashMap<>();
        payload.put("grant_type", "password");
        payload.put("client_id", clientId);
        payload.put("client_secret", clientSecret);
        payload.put("scope", scope);
        payload.put("username", username);
        payload.put("password", password);

        return restApiClient.post(tokenUrl, null, payload, AuthResponseDto.class);
    }


    public RestApiClient.Response<AuthResponseDto> revokeRefreshToken(String token) {
        Map<String, String> payload = new HashMap<>();
        payload.put("client_id", clientId);
        payload.put("client_secret", clientSecret);
        payload.put("token", token);
        payload.put("token_type_hint", "refresh_token");

        return restApiClient.post(tokenUrl, new HashMap<>(), payload, AuthResponseDto.class);
    }


    public RestApiClient.Response<UserInfoResponseDto> getUserInfo(Map<String, String> headers) {
        return restApiClient.get(userInfoUrl, headers, UserInfoResponseDto.class);
    }

//    public RestApiClient.Response<Object> logout(Map<String, String> headers) {
//        return restApiClient.get(userLogoutUrl, headers, Object.class);
//    }
}

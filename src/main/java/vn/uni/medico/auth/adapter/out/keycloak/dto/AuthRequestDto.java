package vn.uni.medico.auth.adapter.out.keycloak.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Accessors(chain = true)
public class AuthRequestDto {
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("grant_type")
    private String grantType;
    @JsonProperty("client_secret")
    private String clientSecret;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
}

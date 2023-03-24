package vn.uni.medico.auth.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import vn.uni.medico.auth.domain.command.ChangePasswordCmd;

import java.util.Map;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
@Getter
@Setter
@Accessors(chain = true)
public class UserDto {
    private String id;
    private String name;
    private String code;
    private String email;
    private String phoneNumber;
    private String position;
    private String username;
    private Integer gender;
    private String password;
    private String retypePassword;


    private Map<String, String> exts;
    private String accessToken;
    private String refreshToken;

    private String fullName;
    private String roleName;
    private Long tenantId;
    private Long branchId;


    public ChangePasswordCmd toChangePasswordCmd() {
        return ChangePasswordCmd.builder()
                .username(username)
                .password(password)
                .retypePassword(retypePassword)
                .build();

    }
}

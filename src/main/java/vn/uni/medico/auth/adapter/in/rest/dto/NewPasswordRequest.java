package vn.uni.medico.auth.adapter.in.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString(includeFieldNames = true)
public class NewPasswordRequest {

    private String newPassword;
    private String otpToken;
}

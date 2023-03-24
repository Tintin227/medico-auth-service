package vn.uni.medico.auth.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class LoginDto {

    @NotBlank(message = "{common.error.must.be.not.null}")
    private String username;
    @NotBlank(message = "{common.error.must.be.not.null}")
    private String password;
}

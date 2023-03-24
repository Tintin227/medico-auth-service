package vn.uni.medico.auth.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.uni.medico.shared.validator.SelfValidating;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ChangePasswordCmd extends SelfValidating<ChangePasswordCmd> {

    String username;
    String password;
    String retypePassword;
}

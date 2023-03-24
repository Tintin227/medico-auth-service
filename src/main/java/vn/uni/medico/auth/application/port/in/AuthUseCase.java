package vn.uni.medico.auth.application.port.in;

import vn.uni.medico.auth.adapter.in.rest.dto.LoginDto;
import vn.uni.medico.auth.adapter.in.rest.dto.UserDto;
import vn.uni.medico.auth.domain.command.ChangePasswordCmd;

public interface AuthUseCase {

    UserDto login(LoginDto loginDto);

    UserDto refreshToken(String token);

    void logout(String username);

    void changePassword(ChangePasswordCmd changePasswordCmd);

    UserDto getUserInfo(String username);

    UserDto updateUserInfo(String username, UserDto player);


    void revokeRefreshToken(String token);
}

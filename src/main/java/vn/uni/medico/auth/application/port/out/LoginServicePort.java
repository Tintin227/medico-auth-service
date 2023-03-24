package vn.uni.medico.auth.application.port.out;

import vn.uni.medico.auth.adapter.in.rest.dto.UserDto;

public interface LoginServicePort {

    UserDto login(String username, String password);

    UserDto refreshToken(String token);

    void logout(String username);


    UserDto getUserInfo(String username);

    UserDto update(UserDto userDto);

    void changePassword(String username, String password);

    boolean hasSession(String username, String sessionId);

    void revokeRefreshToken(String token);
}

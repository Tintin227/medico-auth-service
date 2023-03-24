package vn.uni.medico.auth.application.port.in.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import vn.uni.medico.auth.application.port.in.AuthUseCase;
import vn.uni.medico.auth.adapter.in.rest.dto.LoginDto;
import vn.uni.medico.auth.adapter.in.rest.dto.UserDto;
import vn.uni.medico.auth.application.port.out.LoginServicePort;
import vn.uni.medico.auth.domain.command.ChangePasswordCmd;
import vn.uni.medico.shared.application.port.out.CacheService;
import vn.uni.medico.shared.util.JsonUtil;

@Service
@RequiredArgsConstructor
public class AuthUseCaseImpl implements AuthUseCase {
    private final CacheService cacheService;
    private final LoginServicePort loginServicePort;


    @Override
    public UserDto login(LoginDto loginDto) {
        // login with keycloak
        var entity = loginServicePort.login(loginDto.getUsername(), loginDto.getPassword());

        //
        return entity;
    }


    @Override
    public UserDto refreshToken(String token) {
        return loginServicePort.refreshToken(token);
    }

    @Override
    public void logout(String username) {
        Assert.hasText(username, "PLAYER_NOT_FOUND");
        loginServicePort.logout(username);
    }

    @Override
    public void changePassword(ChangePasswordCmd changePasswordCmd) {
        validateRegisterOrChangePassword(changePasswordCmd);
        loginServicePort.changePassword(changePasswordCmd.getUsername(), changePasswordCmd.getPassword());
    }

    @Override
    public UserDto getUserInfo(String username) {
        return loginServicePort.getUserInfo(username);
    }

    @Override
    public UserDto updateUserInfo(String username, UserDto playerDto) {
        var existedEntity = loginServicePort.getUserInfo(username);

        loginServicePort.update(existedEntity);

        return existedEntity;
    }


    public UserDto getSocialLoginPayload(String topicId) {
        var payloadInJson = cacheService.get("TOPIC_LOGIN_" + topicId);
        if (!StringUtils.hasText(payloadInJson)) return null;
        return JsonUtil.toObject(payloadInJson, vn.uni.medico.auth.adapter.in.rest.dto.UserDto.class);
    }


    private void validateRegisterOrChangePassword(ChangePasswordCmd changePasswordCmd) {
    }


    @Override
    public void revokeRefreshToken(String token) {
        loginServicePort.revokeRefreshToken(token);
    }
}

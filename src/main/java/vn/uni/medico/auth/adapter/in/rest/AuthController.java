package vn.uni.medico.auth.adapter.in.rest;


import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.uni.medico.auth.application.port.in.AuthUseCase;
import vn.uni.medico.auth.adapter.in.rest.dto.LoginDto;
import vn.uni.medico.auth.adapter.in.rest.dto.UserDto;
import vn.uni.medico.auth.constant.AuthConstant;
import vn.uni.medico.auth.domain.command.ChangePasswordCmd;
import vn.uni.medico.shared.adapter.in.rest.SecurityController;
import vn.uni.medico.shared.adapter.in.rest.dto.BaseResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController extends SecurityController {
    private final AuthUseCase authUseCase;


    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(BaseResponse.builder()
                .code(AuthConstant.RESPONSE_CODE.OK)
                .data(authUseCase.login(loginDto))
                .build());
    }

    @GetMapping(value = "/logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        authUseCase.logout(getUsername());
        return ResponseEntity.ok(BaseResponse.builder()
                .code(AuthConstant.RESPONSE_CODE.OK)
                .build());
    }

    @GetMapping(value = "/token/refresh")
    public ResponseEntity refreshToken(@NotBlank(message = "{common.error.must.be.not.null}") String token) {
        ;
        return ResponseEntity.ok(BaseResponse.builder()
                .code(AuthConstant.RESPONSE_CODE.OK)
                .data(authUseCase.refreshToken(token))
                .build());
    }

    @GetMapping(value = "/token/refresh/revoke")
    public ResponseEntity revokeRefreshToken(@NotBlank(message = "{common.error.must.be.not.null}") String token) {
        authUseCase.revokeRefreshToken(token);
        return ResponseEntity.ok(BaseResponse.builder()
                .code(AuthConstant.RESPONSE_CODE.OK)
                .build());
    }

    @PostMapping(value = "/change-password")
    public ResponseEntity changePassword(@RequestBody UserDto userDto) {
        ChangePasswordCmd changePasswordCmd = userDto.toChangePasswordCmd();
        authUseCase.changePassword(changePasswordCmd);
        return ResponseEntity.ok(BaseResponse.builder()
                .code(AuthConstant.RESPONSE_CODE.OK)
                .build());
    }


}

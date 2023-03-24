package vn.uni.medico.auth.adapter.in.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.uni.medico.auth.application.port.in.AuthUseCase;
import vn.uni.medico.auth.adapter.in.rest.dto.UserDto;
import vn.uni.medico.auth.constant.AuthConstant;
import vn.uni.medico.shared.adapter.in.rest.SecurityController;
import vn.uni.medico.shared.adapter.in.rest.dto.BaseResponse;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/userinfo")
public class UserInfoController extends SecurityController {
    private final AuthUseCase authUseCase;

    @GetMapping()
    public ResponseEntity getUserInfo() {
        return ResponseEntity.ok(BaseResponse.builder()
                .code(AuthConstant.RESPONSE_CODE.OK)
                .data(authUseCase.getUserInfo(getUsername()))
                .build());
    }

    @PostMapping()
    public ResponseEntity updateUserInfo(@RequestBody UserDto playerDto) {
        return ResponseEntity.ok(BaseResponse.builder()
                .code(AuthConstant.RESPONSE_CODE.OK)
                .data(authUseCase.updateUserInfo(getUsername(), playerDto))
                .build());
    }

}

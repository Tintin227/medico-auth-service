package vn.uni.medico.auth.adapter.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.uni.medico.auth.adapter.in.rest.dto.NewPasswordRequest;
import vn.uni.medico.auth.application.port.in.OtpUseCase;
import vn.uni.medico.auth.adapter.in.rest.dto.OtpRequestDto;
import vn.uni.medico.auth.adapter.in.rest.dto.OtpVerifyDto;
import vn.uni.medico.auth.constant.AuthConstant;
import vn.uni.medico.shared.adapter.in.rest.dto.BaseResponse;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/forget-password")
public class ForgetPasswordController {

    private final OtpUseCase useCase;

    //Test
    @PostMapping(value = "/otp")
    public ResponseEntity sendOtp(@RequestBody OtpRequestDto dto) {
        useCase.senOtpViaMailOrPhone(dto);
        return ResponseEntity.ok(BaseResponse.builder()
                .code(AuthConstant.RESPONSE_CODE.OK)
                .build());
    }

    @PostMapping(value = "/step1")
    public ResponseEntity verifyOtp(@RequestBody OtpVerifyDto dto) {
        return ResponseEntity.ok(BaseResponse.builder()
                .code(AuthConstant.RESPONSE_CODE.OK)
                .data(useCase.verifyOtp(dto))
                .build());
    }

    @Operation(summary = "Đặt lại mật khẩu")
    @PostMapping(value = "/step2")
    public ResponseEntity resetPassword(@Valid @RequestBody NewPasswordRequest newPasswordRequest) {
        useCase.resetPassword(newPasswordRequest);
        return ResponseEntity.ok(BaseResponse.builder()
                .code(AuthConstant.RESPONSE_CODE.OK)
                .build());
    }
}

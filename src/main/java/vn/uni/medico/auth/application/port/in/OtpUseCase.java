package vn.uni.medico.auth.application.port.in;

import vn.uni.medico.auth.adapter.in.rest.dto.NewPasswordRequest;
import vn.uni.medico.auth.adapter.in.rest.dto.OtpRequestDto;
import vn.uni.medico.auth.adapter.in.rest.dto.OtpVerifyDto;

public interface OtpUseCase {
    void senOtpViaMailOrPhone(OtpRequestDto otpRequest);

    OtpVerifyDto verifyOtp(OtpVerifyDto otpVerify);

    void resetPassword(NewPasswordRequest newPasswordRequest);
}

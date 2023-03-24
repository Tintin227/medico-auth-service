package vn.uni.medico.middleware.adapter.out.ForgetPassword;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import vn.uni.medico.shared.application.port.out.ForgetPassword.VerifyOtpCodeService;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class VerifyOtpCodeAdapter implements VerifyOtpCodeService {

    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void veryfyOtp(String otpInsert,String otp) {
        Assert.hasText(otp, "OTP_EXPIRED");
        Assert.isTrue(otp.equals(otpInsert), "WRONG_OTP");
    }

    @Override
    public String createToken() {
        String token = "OTP_" + UUID.randomUUID();
        return token;
    }

}

package vn.uni.medico.shared.application.port.out.ForgetPassword;

import org.springframework.stereotype.Service;
import vn.uni.medico.auth.adapter.in.rest.dto.DataCache;
import vn.uni.medico.auth.adapter.in.rest.dto.OtpVerifyDto;

import java.util.Map;

@Service
public interface VerifyOtpCodeService {
     void veryfyOtp(String otpInsert,String otp);
     String createToken();
}

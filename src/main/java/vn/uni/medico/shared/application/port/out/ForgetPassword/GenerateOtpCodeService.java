package vn.uni.medico.shared.application.port.out.ForgetPassword;

import org.springframework.stereotype.Service;
import vn.uni.medico.auth.adapter.in.rest.dto.OtpRequestDto;
@Service
public interface GenerateOtpCodeService {
    String generateOtp();
}

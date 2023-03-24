package vn.uni.medico.middleware.adapter.out.ForgetPassword;

import com.bastiaanjansen.otp.HMACAlgorithm;
import com.bastiaanjansen.otp.SecretGenerator;
import com.bastiaanjansen.otp.TOTP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vn.uni.medico.auth.adapter.in.rest.dto.OtpRequestDto;
import vn.uni.medico.auth.adapter.out.postgre.repo.booking.NotificationMailRepository;
import vn.uni.medico.auth.adapter.out.postgre.repo.booking.NotificationMessageRepository;
import vn.uni.medico.auth.domain.entity.booking.NotificationMail;
import vn.uni.medico.auth.domain.entity.booking.NotificationMessage;
import vn.uni.medico.auth.domain.service.StaffQueryService;
import vn.uni.medico.shared.application.port.out.CacheService;
import vn.uni.medico.shared.application.port.out.ForgetPassword.GenerateOtpCodeService;
import vn.uni.medico.shared.application.port.out.MailService;
import vn.uni.medico.shared.application.port.out.SmsService;
import vn.uni.medico.shared.application.port.out.booking.NotificationMailService;
import vn.uni.medico.shared.application.port.out.booking.NotificationMessageService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class GenerateOtpCodeAdapter implements GenerateOtpCodeService {

    private static final TOTP totp = new TOTP.Builder(SecretGenerator.generate())
            .withPasswordLength(6)
            .withAlgorithm(HMACAlgorithm.SHA512)
            .withPeriod(Duration.ofSeconds(60)).build();

    private final CacheService cacheService;
    @Override
    public String generateOtp() {
        String otp = totp.now().substring(1, 5);
        return otp;
    }
}

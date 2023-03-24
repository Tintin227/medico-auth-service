package vn.uni.medico.auth.application.port.in.impl;

import com.bastiaanjansen.otp.HMACAlgorithm;
import com.bastiaanjansen.otp.SecretGenerator;
import com.bastiaanjansen.otp.TOTP;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import vn.uni.medico.auth.adapter.in.rest.dto.DataCache;
import vn.uni.medico.auth.adapter.in.rest.dto.NewPasswordRequest;
import vn.uni.medico.auth.application.port.in.OtpUseCase;
import vn.uni.medico.auth.adapter.in.rest.dto.OtpRequestDto;
import vn.uni.medico.auth.adapter.in.rest.dto.OtpVerifyDto;
import vn.uni.medico.auth.application.port.out.LoginServicePort;
import vn.uni.medico.auth.domain.service.StaffQueryService;
import vn.uni.medico.shared.application.port.out.CacheService;
import vn.uni.medico.shared.application.port.out.ForgetPassword.CheckRequestPasswordService;
import vn.uni.medico.shared.application.port.out.ForgetPassword.GenerateOtpCodeService;
import vn.uni.medico.shared.application.port.out.ForgetPassword.VerifyOtpCodeService;
import vn.uni.medico.shared.application.port.out.booking.NotificationMailService;
import vn.uni.medico.shared.application.port.out.booking.NotificationMessageService;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class OtpUseCaseImpl implements OtpUseCase {

    private final GenerateOtpCodeService generateOtpCodeService;
    private final VerifyOtpCodeService verifyOtpCodeService;
    private final StaffQueryService staffQueryService;
    private final NotificationMailService notificationMailService;
    private final NotificationMessageService notificationMessageService;
    private final LoginServicePort loginServicePort;
    private final CheckRequestPasswordService checkRequestPasswordService;
    private final CacheService cacheService;

    public void senOtpViaMailOrPhone(OtpRequestDto otpRequestDto) {
        String userName= otpRequestDto.getUserName();
        String email=otpRequestDto.getEmail();
        String phone =otpRequestDto.getPhoneNumber();

        //Kiểm tra thông tin tài khoản

        if(!staffQueryService.checkUserByMailOrPhone(userName,email,phone)){
            throw new IllegalArgumentException("Invalid information entered");
        }
        //Tạo mã otp và lưu vào cache
        String otp= generateOtpCodeService.generateOtp();
        cacheService.set("OTP_"+userName, otp, 60);

        if (StringUtils.hasText(email)) {
            //Lưu thông tin vào bảng notification_emails và notification_email_details
            notificationMailService.saveMail(email,otp);
        }
        else if(StringUtils.hasText(phone)){
            // Lưu thông tin vào bảng notification_messages
            notificationMessageService.saveMessage(phone,otp);
        }
    }

    @Override
    public OtpVerifyDto verifyOtp(OtpVerifyDto otpVerify) {
        String userName= otpVerify.getUserName();
        String email=otpVerify.getEmail();
        String phone =otpVerify.getPhoneNumber();
        String otp= otpVerify.getOtp();

        //Xác thực thông tin tài khoản
        if(!staffQueryService.checkUserByMailOrPhone(userName,email,phone)){
            throw new IllegalArgumentException("Invalid information entered");
        }

        String otpCheck = cacheService.get("OTP_"+userName);

        //Xác thực mã otp
        verifyOtpCodeService.veryfyOtp(otp,otpCheck);

        //Tạo Token
        String token =verifyOtpCodeService.createToken();

        //Lưu vào cacheHash
        cacheService.set(token, userName, 60*60);

        return new OtpVerifyDto().setOtpToken(token);
    }

    public void resetPassword(NewPasswordRequest newPasswordRequest) {
        String token = newPasswordRequest.getOtpToken();
        String newPassword= newPasswordRequest.getNewPassword();
        String userName =cacheService.get(token);

        // Kiểm tra userName
        checkRequestPasswordService.checkUserName(userName);

        // Kiểm tra password
        checkRequestPasswordService.checkPassword(newPassword);

        // Đặt lại mật khẩu mới và lưu lại lên keycloak
        loginServicePort.changePassword(userName,newPassword);
    }
}

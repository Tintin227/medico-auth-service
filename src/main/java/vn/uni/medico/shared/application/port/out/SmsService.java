package vn.uni.medico.shared.application.port.out;

import org.springframework.stereotype.Service;
import vn.uni.medico.middleware.domain.entity.Sms;

@Service
public interface SmsService {
    void send(String phone,String otp);
}

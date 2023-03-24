package vn.uni.medico.shared.application.port.out.booking;

import org.springframework.stereotype.Service;
import vn.uni.medico.auth.domain.entity.booking.NotificationMail;

@Service
public interface NotificationMailService {
    void saveMail(String mail, String otp);
}

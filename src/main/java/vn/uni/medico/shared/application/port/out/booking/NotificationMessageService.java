package vn.uni.medico.shared.application.port.out.booking;

import vn.uni.medico.auth.domain.entity.booking.NotificationMessage;

public interface NotificationMessageService {
    void saveMessage(String phone, String otp);
}

package vn.uni.medico.shared.adapter.out.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import vn.uni.medico.auth.adapter.out.postgre.repo.booking.NotificationMessageRepository;
import vn.uni.medico.auth.domain.entity.booking.NotificationMessage;
import vn.uni.medico.shared.application.port.out.booking.NotificationMessageService;

@Repository
@RequiredArgsConstructor
public class NotificationMessageAdapter implements NotificationMessageService {

    private  final NotificationMessageRepository notificationMessageRepository;

    @Override
    public void saveMessage(String phone, String otp) {
        NotificationMessage notificationMessage= new NotificationMessage();
        notificationMessage.setMessage(otp);
        notificationMessage.setReceiverAddress(phone);
        notificationMessageRepository.save(notificationMessage);
    }
}

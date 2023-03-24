package vn.uni.medico.shared.adapter.out.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import vn.uni.medico.auth.adapter.out.postgre.repo.booking.NotificationMailDetailRepository;
import vn.uni.medico.auth.adapter.out.postgre.repo.booking.NotificationMailRepository;
import vn.uni.medico.auth.domain.entity.booking.NotificationMail;
import vn.uni.medico.auth.domain.entity.booking.NotificationMailDetail;
import vn.uni.medico.shared.application.port.out.booking.NotificationMailService;

@Repository
@RequiredArgsConstructor
public class NotificationMailAdapter implements NotificationMailService {

    private  final NotificationMailRepository notificationMailRepository;

    private  final NotificationMailDetailRepository notificationMailDetailRepository;

    @Override
    public void saveMail(String mail, String otp) {

        // Lưu vào bảng NotificationMail
        NotificationMail notificationMail = new NotificationMail();
        notificationMail.setReceiverAddress(mail);
        notificationMailRepository.save(notificationMail);


        //Lưu vào bảng NotificationMailDetail
        NotificationMailDetail notificationMailDetail = new NotificationMailDetail();
        notificationMailDetail.setMessage(otp);
        notificationMailDetail.setEmailId(notificationMail.getId());
        notificationMailDetailRepository.save(notificationMailDetail);
    }
}

package vn.uni.medico.auth.adapter.out.postgre.repo.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.uni.medico.auth.domain.entity.booking.NotificationMailDetail;

public interface NotificationMailDetailRepository  extends JpaRepository<NotificationMailDetail, Long> {
}

package vn.uni.medico.auth.adapter.out.postgre.repo.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.uni.medico.auth.domain.entity.booking.NotificationMessage;

@Repository
public interface NotificationMessageRepository extends JpaRepository<NotificationMessage, Long> {

}

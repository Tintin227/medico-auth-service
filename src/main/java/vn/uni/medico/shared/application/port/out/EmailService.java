package vn.uni.medico.shared.application.port.out;

import vn.uni.medico.middleware.domain.entity.Email;

public interface EmailService {
    void send(Email mail);
}

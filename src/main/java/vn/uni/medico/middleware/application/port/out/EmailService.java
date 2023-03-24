package vn.uni.medico.middleware.application.port.out;

import vn.uni.medico.middleware.domain.entity.Email;

public interface EmailService {
    void send(Email mail);
}

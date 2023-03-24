package vn.uni.medico.shared.application.port.out.ForgetPassword;

import org.springframework.stereotype.Service;
import vn.uni.medico.auth.adapter.in.rest.dto.DataCache;
import vn.uni.medico.auth.adapter.in.rest.dto.NewPasswordRequest;

@Service
public interface CheckRequestPasswordService {
    void checkUserName(String userName);
    void checkPassword(String password);
}

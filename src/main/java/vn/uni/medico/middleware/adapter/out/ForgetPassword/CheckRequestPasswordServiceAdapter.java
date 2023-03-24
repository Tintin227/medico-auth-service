package vn.uni.medico.middleware.adapter.out.ForgetPassword;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import vn.uni.medico.shared.application.port.out.CacheService;
import vn.uni.medico.shared.application.port.out.ForgetPassword.CheckRequestPasswordService;


@Service
@RequiredArgsConstructor
public class CheckRequestPasswordServiceAdapter implements CheckRequestPasswordService {


    @Override
    public void checkUserName(String userName) {
        Assert.hasText(userName, "Invalid information entered");
    }

    @Override
    public void checkPassword(String password) {
        Assert.hasText(password, "NEW_PASSWORD_IS_EMPTY");
    }
}

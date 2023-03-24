package vn.uni.medico.middleware.application.port.out;


public interface SmsService {
    void send(String toPhoneNumber, String otp);
}

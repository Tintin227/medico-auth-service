package vn.uni.medico.middleware.adapter.out.sms;


import org.springframework.stereotype.Component;
import vn.uni.medico.shared.application.port.out.SmsService;

@Component
public class SmsAdapter implements SmsService {

    private String accountSid="ACa62ae4bc24971147782a942a0f01f6dc";
    private String authToken="852191d53bbfa33e0b11fca3c599adb7";
    private String phoneNumber="+12762779785";

    public void send(String toPhoneNumber, String otp) {
//        Twilio.init(accountSid, authToken);
//
//        Message message = Message.creator(
//                new PhoneNumber(),
//                new PhoneNumber(phoneNumber), "Your OTP is: " + otp)
//                .create();
    }
}

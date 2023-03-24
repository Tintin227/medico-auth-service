package vn.uni.medico.middleware.adapter.out.sms.middleware;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import vn.uni.medico.middleware.adapter.out.sms.dto.SmsDto;

@FeignClient(name = "MEDICO-SMS-NOTIFICATION")
@Headers({
        "Accept: application/json; charset=utf-8",
        "Content-Type: application/json" })
public interface NotificationServiceClient {
    @PostMapping("/sms/send")
    SmsDto sendSms(@RequestBody SmsDto sms);
}

package vn.uni.medico.middleware.adapter.out.sms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
@Getter
@Setter
@Accessors(chain = true)
public class SmsDto {
    private String id;
    private String phoneNumber;
    private String message;
    private String source;
    private String purpose;
}

//package vn.uni.medico.auth.application.port.in.mapper;
//
//import org.mapstruct.Mapper;
//import vn.uni.medico.auth.adapter.in.rest.dto.OtpRequestDto;
//import vn.uni.medico.middleware.domain.entity.Sms;
//
//@Mapper(componentModel = "spring")
//public interface SmsOtpRequestDtoMapper {
//
//    default Sms toEntity(OtpRequestDto dto) {
//        return new Sms()
//                .setPhoneNumber(dto.getMobile())
//                .setSource("PLAYER.AUTH")
//                .setPurpose("REQUEST_OTP")
//                ;
//    }
//}

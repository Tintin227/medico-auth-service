package vn.uni.medico.middleware.adapter.out.sms.mapper;

import org.mapstruct.Mapper;
import vn.uni.medico.middleware.adapter.out.sms.dto.SmsDto;
import vn.uni.medico.middleware.domain.entity.Sms;

@Mapper(componentModel = "spring")
public interface SmsMapper {

    Sms toEntity(SmsDto dto);

    SmsDto toDto(Sms entity);
}

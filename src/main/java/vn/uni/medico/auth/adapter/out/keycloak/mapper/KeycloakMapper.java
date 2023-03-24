package vn.uni.medico.auth.adapter.out.keycloak.mapper;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.keycloak.representations.idm.SocialLinkRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.springframework.util.StringUtils;
import vn.uni.medico.auth.adapter.out.keycloak.middleware.KeycloakClient;
import vn.uni.medico.auth.adapter.in.rest.dto.UserDto;
import vn.uni.medico.shared.util.StringUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface KeycloakMapper {

    default UserRepresentation toDto(UserDto entity) {
        UserRepresentation user = new UserRepresentation();

        return user;
    }

    default UserDto toEntity(UserRepresentation dto) {

        UserDto userDto = new UserDto()
                .setId(dto.getId())
                .setUsername(dto.getUsername())
                .setName(StringUtils.hasText(dto.getLastName()) ? dto.getFirstName() + " " + dto.getLastName() : dto.getFirstName())
                .setEmail(dto.getEmail());

        if (dto.getAttributes() != null) {
            var gender = Integer.parseInt(dto.getAttributes().getOrDefault("gender", List.of()).stream().findFirst().orElse("0"));
            var birthdayTime = Long.parseLong(dto.getAttributes().getOrDefault("birthday", List.of()).stream().findFirst().orElse("0"));
            userDto.setGender(gender > 0 ? gender : null)
            ;
        }

        return userDto;
    }
}

package vn.uni.medico.auth.adapter.out.keycloak;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import vn.uni.medico.auth.adapter.out.keycloak.dto.AuthResponseDto;
import vn.uni.medico.auth.adapter.out.keycloak.mapper.KeycloakMapper;
import vn.uni.medico.auth.adapter.out.keycloak.middleware.KeycloakClient;
import vn.uni.medico.auth.adapter.out.keycloak.middleware.OAuthClient;
import vn.uni.medico.auth.adapter.in.rest.dto.UserDto;
import vn.uni.medico.auth.adapter.out.postgre.repo.admin.StaffRepo;
import vn.uni.medico.auth.adapter.out.postgre.repo.admin.TenantParamRepo;
import vn.uni.medico.auth.adapter.out.postgre.repo.booking.CustomersRepository;
import vn.uni.medico.auth.application.port.out.LoginServicePort;
import vn.uni.medico.auth.constant.AuthConstant;
import vn.uni.medico.auth.domain.entity.admin.TenantParam;
import vn.uni.medico.shared.middleware.RestApiClient;
import vn.uni.medico.shared.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@Slf4j
public class KeycloakServiceAdapter implements LoginServicePort {

    private final OAuthClient oAuthClient;
    private final KeycloakClient keycloakClient;

    private final KeycloakMapper mapper;
    private final StaffRepo staffRepo;
    private final CustomersRepository customersRepository;
    private final TenantParamRepo tenantParamRepo;


    @Override
    public UserDto login(String username, String password) {
        // login via keycloak
        RestApiClient.Response<AuthResponseDto> response = oAuthClient.login(username, password);
        if (!response.isSuccessful() || ObjectUtils.isEmpty(response.getContent())) {
            throw new BadCredentialsException("invalid.user.or.password");
        }
        String accessToken = response.getContent().getAccessToken();

        // get more info from keycloak
        String fullName = "";
        String roleName = "";
        var existedUser = findAllUsersByUsername(username);
        var attribute = existedUser.getAttributes();
        Long tenantId = Long.valueOf(existedUser.getAttributes().get("tenantId").get(0));
        if (attribute.containsKey("userType")) {
            String userType = existedUser.getAttributes().get("userType").get(0);
            if (!StringUtil.isNullOrEmpty(userType) && AuthConstant.Role.STAFF.equals(userType)) {
                var staff = staffRepo.findFirstByUsername(existedUser.getUsername().toUpperCase());
                if (!ObjectUtils.isEmpty(staff)) {
                    fullName = staff.getName();
                    String parName = tenantParamRepo.findParNameByTenantIdAndParTypeAndParValue(tenantId,AuthConstant.PAR_TYPE.CHUC_VU,staff.getPosition().toString());
                    roleName = !StringUtil.isNullOrEmpty(parName) ? parName : AuthConstant.Role.STAFF;
                }
            } else if (!StringUtil.isNullOrEmpty(userType) && AuthConstant.Role.CUSTOMER.equals(userType)) {
                var customer = customersRepository.findFirstByCustomerCode(existedUser.getUsername().toUpperCase());
                if (!ObjectUtils.isEmpty(customer)) {
                    fullName = customer.getName();
                    roleName = AuthConstant.Role.CUSTOMER;
                }
            }
        }
        return mapper.toEntity(existedUser)
                .setAccessToken(response.getContent().getAccessToken())
                .setRefreshToken(response.getContent().getRefreshToken())
                .setFullName(fullName)
                .setRoleName(roleName)
                .setTenantId(attribute.containsKey("tenantId") ? Long.valueOf(attribute.get("tenantId").get(0)) : null)
                .setBranchId(attribute.containsKey("branchId") ? Long.valueOf(attribute.get("branchId").get(0)) : null)
                ;

    }


    @Override
    public UserDto refreshToken(String token) {
        // login via keycloak
        Map<String, String> headers = new HashMap<>();
        Map<String, String> payloads = new HashMap<>();
        payloads.put("refresh_token", token);
        RestApiClient.Response<AuthResponseDto> response = oAuthClient.genToken(headers, payloads);
        if (!response.isSuccessful() || ObjectUtils.isEmpty(response.getContent())) {
            throw new BadCredentialsException("invalid.refresh.token");
        }

        // get more info from keycloak
        UserDto userDto = new UserDto();
        return userDto
                .setAccessToken(response.getContent().getAccessToken())
                .setRefreshToken(response.getContent().getRefreshToken())
                ;

    }


    private UserRepresentation findAllUsersByUsername(String username) {
        var existed = keycloakClient.findAllUsersByUsername(username).stream().filter(u -> u != null && u.isEnabled()).findFirst().orElse(null);
        if (ObjectUtils.isEmpty(existed)) {
            throw new BadCredentialsException("invalid.user.or.password");
        }
        return existed;
    }

    @Override
    public void logout(String username) {
        Assert.hasText(username, "user.not.found");
        keycloakClient.deleteUserSessions(username);
    }

    @Override
    public UserDto getUserInfo(String username) {
        Assert.hasText(username, "invalid.username");
        var existedUser = findAllUsersByUsername(username);
        return mapper.toEntity(existedUser);
    }

    @Override
    public UserDto update(UserDto userDto) {
        Assert.notNull(userDto, "invalid.user");
        var user = mapper.toDto(userDto);
        keycloakClient.updateUser(user);

        var existedUser = findAllUsersByUsername(user.getUsername());
        return mapper.toEntity(existedUser);
    }


    @Override
    public void changePassword(String username, String password) {
        keycloakClient.changePassword(username, password);
    }

    @Override
    public boolean hasSession(String username, String sessionId) {
        return keycloakClient.getUserSession(username, sessionId) != null;
    }

    @Override
    public void revokeRefreshToken(String token) {
        RestApiClient.Response<AuthResponseDto> response = oAuthClient.revokeRefreshToken(token);
        if (!response.isSuccessful() || ObjectUtils.isEmpty(response.getContent())) {
            throw new BadCredentialsException("invalid.refresh.token");
        }
    }
}

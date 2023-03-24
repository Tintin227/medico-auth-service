package vn.uni.medico.shared.adapter.in.rest;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import vn.uni.medico.auth.application.port.out.LoginServicePort;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public abstract class SecurityController {

    @Resource
    private LoginServicePort keycloakService;

    protected List<String> getUserRoles() {
        return new ArrayList<>(getAvailableAccessToken().getRealmAccess().getRoles());
    }

    protected String getUsername() {
//        return StringUtils.isEmpty(getAvailableAccessToken().getPreferredUsername()) ? getAvailableAccessToken().getSubject() : getAvailableAccessToken().getPreferredUsername();
        return getAvailableAccessToken().getPreferredUsername();
    }

//    protected String getUserInfo() {
//        AccessToken token = getSecurityContext().getToken();
//        return (StringUtils.isEmpty(token.getPreferredUsername()) ? token.getSubject() : token.getPreferredUsername()) + " (" + token.getName() + ")";
//    }

    private KeycloakSecurityContext getSecurityContext() {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        if (ObjectUtils.isEmpty(request.getAttribute(KeycloakSecurityContext.class.getName()))) {
            throw new SecurityException("not.found.valid.authentication");
        }
        return (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
    }

    protected AccessToken getAvailableAccessToken(){
        var token = getSecurityContext().getToken();
        Assert.isTrue(keycloakService.hasSession(token.getPreferredUsername(), token.getSessionId()), "INVALID_TOKEN");
        return token;
    }
}

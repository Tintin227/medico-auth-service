package vn.uni.medico.auth.adapter.out.keycloak.middleware;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.UserSessionRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Service
@Slf4j
public class KeycloakClient {
    @Resource
    private Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    public List<UserRepresentation> listUsers() {
        try {
            return keycloak
                    .realm(realm)
                    .users()
                    .list();
        } catch (Exception ex) {
            log.error("listUsers::Exception---------------");
            log.error(ex.getMessage(), ex);
            return List.of();
        }

    }

    public List<UserRepresentation> findAllUsersByUsername(String username) {
        try {
            return keycloak
                    .realm(realm)
                    .users()
                    .search(username);
        } catch (ProcessingException ex) {
            log.error("findAllUsersByUsername::Exception---------------");
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            return List.of();
        } catch (BadRequestException ex) {
            log.error("findAllUsersByUsername::Exception---------------");
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
            return List.of();
        }
    }

    public void enableUser(String username, Boolean enabled) {
        try {
            UsersResource users = keycloak
                    .realm(realm)
                    .users();
            List<UserRepresentation> userRepresentations = users.search(username);
            if (userRepresentations == null || userRepresentations.isEmpty()) return;

            for (UserRepresentation userRepresentation : userRepresentations) {
                userRepresentation.setEnabled(enabled);
                users.get(userRepresentation.getId()).update(userRepresentation);
            }
        } catch (Exception ex) {
            log.error("enableUser::Exception---------------");
            log.error(ex.getMessage(), ex);
        }

    }

    public void logoutUser(String username) {
        try {
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource users = realmResource.users();

            List<UserRepresentation> userRepresentations = users.search(username);
            if (userRepresentations == null || userRepresentations.isEmpty()) return;
            for (UserRepresentation userRepresentation : userRepresentations) {
                users.get(userRepresentation.getId()).logout();
            }
        } catch (Exception ex) {
            log.error("logoutUser::Exception---------------");
            log.error(ex.getMessage(), ex);
        }

    }

    public void deleteUserSessions(String username) {
        try {
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource users = realmResource.users();
            List<UserRepresentation> userRepresentations = users.search(username);
            if (userRepresentations == null || userRepresentations.isEmpty()) return;
            for (UserRepresentation userRepresentation : userRepresentations) {
                UserResource user = users.get(userRepresentation.getId());
                List<UserSessionRepresentation> userSessions = user.getUserSessions().stream().filter(userSession -> {
                    Map<String, String> clients = userSession.getClients();
                    return clients.containsKey(clientId) || clients.containsValue(clientId);
                }).collect(Collectors.toList());
                for (UserSessionRepresentation userSession : userSessions) {
                    log.info("deleteSession >> " + userSession.getId());

                    realmResource.deleteSession(userSession.getId());
                }

//            users.get(userRepresentation.getId()).logout();
            }
        } catch (Exception ex) {
            log.error("deleteUserSessions::Exception---------------");
            log.error(ex.getMessage(), ex);
        }

    }

    public List<UserSessionRepresentation> getUserSessions(String username) {
        try {
            List<UserSessionRepresentation> userSessions = new ArrayList<>();
            UsersResource users = keycloak
                    .realm(realm)
                    .users();
            List<UserRepresentation> userRepresentations = users.search(username);
            if (userRepresentations == null || userRepresentations.isEmpty()) return userSessions;
            for (UserRepresentation userRepresentation : userRepresentations) {
                userSessions.addAll(users.get(userRepresentation.getId()).getUserSessions().stream().filter(userSession -> {
                    Map<String, String> clients = userSession.getClients();
                    return clients.containsKey(clientId) || clients.containsValue(clientId);
                }).collect(Collectors.toList()));
            }
            return userSessions;
        } catch (Exception ex) {
            log.error("getUserSessions::Exception---------------");
            log.error(ex.getMessage(), ex);
            return List.of();
        }

    }

    public UserSessionRepresentation getUserSession(String username, String sessionId) {
        try {
            UsersResource users = keycloak
                    .realm(realm)
                    .users();
            List<UserRepresentation> userRepresentations = users.search(username);
            if (userRepresentations == null || userRepresentations.isEmpty()) return null;
            for (UserRepresentation userRepresentation : userRepresentations) {
                UserSessionRepresentation userSession = users.get(userRepresentation.getId()).getUserSessions().stream().filter(e -> e.getId().equals(sessionId)).findFirst().orElse(null);
                if (userSession != null) return userSession;
            }
            return null;
        } catch (Exception ex) {
            log.error("getUserSession::Exception---------------");
            log.error(ex.getMessage(), ex);
            return null;
        }

    }

    public boolean hasUserSession(String username) {
        try {
            UsersResource users = keycloak
                    .realm(realm)
                    .users();
            List<UserRepresentation> userRepresentations = users.search(username);
            if (userRepresentations == null || userRepresentations.isEmpty()) return false;
            for (UserRepresentation userRepresentation : userRepresentations) {
                List<UserSessionRepresentation> userSessions = users.get(userRepresentation.getId()).getUserSessions().stream().filter(userSession -> {
                    Map<String, String> clients = userSession.getClients();
                    return clients.containsKey(clientId) || clients.containsValue(clientId);
                }).collect(Collectors.toList());

                if (!userSessions.isEmpty()) return true;
            }
            return false;
        } catch (Exception ex) {
            log.error("hasUserSession::Exception---------------");
            log.error(ex.getMessage(), ex);
            return false;
        }
    }


    public void createUser(UserRepresentation userRepresentation, boolean overrideIfExists) {
        try {
            UsersResource users = keycloak
                    .realm(realm)
                    .users();
            List<UserRepresentation> existedUserRepresentations = users.search(userRepresentation.getUsername());
            if (existedUserRepresentations == null || existedUserRepresentations.isEmpty()) {
                try (Response response = users
                        .create(userRepresentation)) {
                    log.info("Keycloark:createUser [ " + response.getStatusInfo().getStatusCode() + " ] >> " + response.getStatusInfo().getReasonPhrase());
                    Assert.isTrue(response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL, "CREATE_USER_FAILED");
                }
            } else {
                if (overrideIfExists) {
                    for (UserRepresentation existedUserRepresentation : existedUserRepresentations) {
                        existedUserRepresentation.setEnabled(userRepresentation.isEnabled());
                        existedUserRepresentation.setFirstName(userRepresentation.getFirstName());
                        existedUserRepresentation.setLastName(userRepresentation.getLastName());
                        existedUserRepresentation.setEmail(userRepresentation.getEmail());
                        existedUserRepresentation.setAttributes(userRepresentation.getAttributes());
                        existedUserRepresentation.setSocialLinks(userRepresentation.getSocialLinks());
                        existedUserRepresentation.setCredentials(userRepresentation.getCredentials());
                        UserResource userResource = users.get(existedUserRepresentation.getId());
                        if (userResource != null) {
                            userResource.update(existedUserRepresentation);
                            userResource.resetPassword(existedUserRepresentation.getCredentials().stream().findFirst().orElse(null));
                            userResource.logout();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            log.error("createUser::Exception---------------");
            log.error(ex.getMessage(), ex);
        }
    }

    public void updateUser(UserRepresentation userRepresentation) {
        UsersResource resource = keycloak.realm(realm).users();

        if (resource == null)
            return;
        List<UserRepresentation> users = resource.search(userRepresentation.getUsername());
        if (users == null || users.isEmpty())
            return;
        users.forEach(user -> {
            user.setFirstName(userRepresentation.getFirstName());
            user.setLastName(userRepresentation.getLastName());
            user.setEmail(userRepresentation.getEmail());
            user.getAttributes().putAll(userRepresentation.getAttributes());
            resource.get(user.getId()).update(user);
        });
    }

    public boolean changePassword(String username, String password) {
        try {
            UsersResource users = keycloak
                    .realm(realm)
                    .users();
            List<UserRepresentation> userRepresentations = users.search(username);
            if (userRepresentations == null || userRepresentations.isEmpty()) return false;

            for (UserRepresentation userRepresentation : userRepresentations) {
                userRepresentation.setCredentials(List.of(KeycloakClient.credentialOf(password)));
                UserResource userResource = users.get(userRepresentation.getId());
                if (userResource != null) {
                    userRepresentation.setEnabled(true);
                    userResource.update(userRepresentation);
                    userResource.resetPassword(userRepresentation.getCredentials().stream().findFirst().orElse(null));
                    userResource.logout();
                }
            }
            return true;
        } catch (Exception ex) {
            log.error("changePassword::Exception---------------");
            log.error(ex.getMessage(), ex);
            return false;
        }

    }

    public void resetPassword(UserRepresentation userRepresentation) {
        try {
            CredentialRepresentation credential = userRepresentation.getCredentials().stream().findFirst().orElse(null);
            UserResource userResource = keycloak
                    .realm(realm)
                    .users()
                    .get(userRepresentation.getId());
            if (userResource != null) {
                userResource.resetPassword(credential);
                userResource.logout();
            }
        } catch (Exception ex) {
            log.error("resetPassword::Exception---------------");
            log.error(ex.getMessage(), ex);
        }
    }


    public List<RoleRepresentation> fillAllRolesByUsername(String username) {
        try {
            List<RoleRepresentation> roles = new ArrayList<>();
            UsersResource usersResource = keycloak
                    .realm(realm)
                    .users();
            List<UserRepresentation> users = usersResource.search(username);
            users.forEach(user -> roles.addAll(usersResource
                    .get(user.getId())
                    .roles()
                    .realmLevel()
                    .listAll()));
            return roles.stream()
                    .distinct()
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            log.error("fillAllRolesByUsername::Exception---------------");
            log.error(ex.getMessage(), ex);
            return List.of();
        }
    }

    public List<RoleRepresentation> listRoles() {
        try {
            return keycloak
                    .realm(realm)
                    .roles()
                    .list();
        } catch (Exception ex) {
            log.error("listRoles::Exception---------------");
            log.error(ex.getMessage(), ex);
            return List.of();
        }

    }

    public RoleRepresentation findRoleByName(String roleName) {
        try {
            return keycloak
                    .realm(realm)
                    .roles()
                    .get(roleName)
                    .toRepresentation();
        } catch (Exception ex) {
            log.error("findRoleByName::Exception---------------");
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

    public static CredentialRepresentation credentialOf(String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);

        return credential;
    }


}

package vn.uni.medico.auth.constant;

import java.util.List;
import java.util.stream.Collectors;

public class AuthConstant {
    private AuthConstant() {
    }

    public static class WebSocket {
        public static final String ATTR_SESSION_ID = "simpSessionId";
        public static final String TOPIC_AUTH = "/topic/auth";
    }

    public static class ActiveStatus {
        public static final int ACTIVE = 1;
        public static final int NOT_ACTIVE = 0;
        public static final int REMOVED = -1;
    }

    public static class Role {
        public static final String ADMIN = "admin";
        public static final String SUB_ADMIN = "sub_admin";
        public static final String TECH = "tech";
        public static final String SALE = "sale";
        public static final String CUSTOMER = "customer";
        public static final String VIEW = "view";
        public static final String STAFF = "staff";

        private Role() {
        }
    }

    public static class Function {

        private Function() {
        }

        public static Boolean inRoles(List<String> roles, String function) {
            return inRoles(roles, List.of(function));
        }

        public static Boolean inRoles(List<String> roles, List<String> functions) {
            return roles != null && !roles.isEmpty();
//            return roles.stream().anyMatch(role -> inRole(role, functions));
        }

        public static Boolean inRole(String role, String function) {
            return inRole(role, List.of(function));
        }

        public static Boolean inRole(String role, List<String> functions) {
            return functions != null && !functions.isEmpty();
//            return ofRole(role).stream().anyMatch(functions::contains);
        }

        public static List<String> ofRoles(List<String> roles) {
            if (roles == null || roles.isEmpty())
                return List.of();
            return roles.stream().map(Function::ofRole).flatMap(List::stream).distinct().collect(Collectors.toList());
        }

        public static List<String> ofRole(String role) {
            switch (role) {
                case Role.ADMIN:
                case Role.SUB_ADMIN:
                case Role.TECH:
                case Role.SALE:
                case Role.CUSTOMER:
                case Role.VIEW:
                default:
                    return List.of("*");
            }
        }
    }

    public static class ErrorCode {
        public static final String NO_ROLE = "NO_ROLE";
        public static final String NO_FUNCTION = "NO_FUNCTION";
        public static final String NO_USER = "NO_USER";
        public static final String NO_PERMISSION = "NO_PERMISSION";

        public static final String SYSTEM_ERROR = "SYSTEM_ERROR";
        public static final String PHONE_NUMBER_NOT_EMPTY = "PHONE_NUMBER_NOT_EMPTY";
        public static final String MESSAGE_NOT_EMPTY = "MESSAGE_NOT_EMPTY";

        private ErrorCode() {
        }

    }

    public static class DeleteStatus {
        public static final Integer DELETED = 1;
        public static final Integer NOT_DELETED = 0;

        private DeleteStatus() {
        }
    }

    public static class TimeFormat {
        public static final String DATE_TIME = "yyyy/MM/dd HH:mm:ss";
        public static final String DATE = "yyyy/MM/dd";
        public static final String DATE_TIME_NO_SEPERATOR = "yyyyMMddHHmmss";
    }

    public static class RESPONSE_CODE {
        public static final String RESPONSE_SUCCESS = "000";

        public static final Integer OK = 200;
        public static final Integer BAD_REQUEST = 400;
        public static final Integer NOT_FOUND = 404;
        public static final Integer SERVER_ERROR = 500;
        public static final Integer MISSING_FIELD_IN_REQUEST = 4001;
        public static final Integer NOT_FOUND_ENTITY = 4002;
        public static final Integer UNEXPECTED = 5001;
        public static final String INVALID_CUSTOMER = "4003 - invalid.customer";
        public static final String VALIDATION_ERROR = "6001-error.validation";


        private RESPONSE_CODE() {
        }
    }

    public static class PAR_TYPE {
        public static final String CHUC_VU = "CHUC_VU";
    }

}

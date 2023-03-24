package vn.uni.medico.shared.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpErrorException extends RuntimeException {
    private Integer status;
    private String message;

    public HttpErrorException(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}

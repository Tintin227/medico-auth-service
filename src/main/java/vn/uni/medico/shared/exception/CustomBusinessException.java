package vn.uni.medico.shared.exception;

import lombok.Getter;
import lombok.Setter;
import vn.uni.medico.shared.util.ExceptionUtil;
import vn.uni.medico.shared.util.MessageUtils;

@Getter
@Setter
public class CustomBusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String message;
    private int code;

    public CustomBusinessException(String errorCode, String... args) {
        this.code = ExceptionUtil.getCodeError(errorCode);
        this.message = MessageUtils.getMessage(ExceptionUtil.getMessageError(errorCode));
    }

    public CustomBusinessException(String errorCode) {
        this.code = ExceptionUtil.getCodeError(errorCode);
        this.message = MessageUtils.getMessage(ExceptionUtil.getMessageError(errorCode));
    }


}

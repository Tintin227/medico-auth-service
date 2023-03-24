package vn.uni.medico.shared.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.function.Supplier;

@Getter
@Setter
public class EntityNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String message;

    public EntityNotFoundException(Class<?> entity, Serializable id) {
        this.message = String.format("Entity [%s] not found with key [%s] ", entity.getSimpleName(), id);
    }

    public static Supplier<EntityNotFoundException> throwException(Class<?> clazz, Serializable id) {
        return () -> new EntityNotFoundException(clazz, id);
    }

}

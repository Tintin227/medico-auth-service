package vn.uni.medico.shared.adapter.in.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ValidationError {
    private Map<String, String> validateDetails = new HashMap<>();
}

package vn.uni.medico.shared.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImportCriteriaDto {
    private Map<String, String> headers;
    private Map<String, Map<String, Map<String, String>>> transforms;
    private Map<String, String> errorCodes;
}


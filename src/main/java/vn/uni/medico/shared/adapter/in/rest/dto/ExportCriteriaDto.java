package vn.uni.medico.shared.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExportCriteriaDto<T> {
    private String fileName;
    private String fileNameFormat;
    private String title;
    private String titleFormat;
    private String fileType;
    private T filter;
    private Map<String, String> headers;
    private Map<String, String> summaryHeaders;
    private Map<String, Map<String, Map<String, String>>> transforms;
    private Map<String, Map<String, Map<String, String>>> summaryTransforms;

}


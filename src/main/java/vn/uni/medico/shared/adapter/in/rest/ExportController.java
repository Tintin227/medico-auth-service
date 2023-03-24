package vn.uni.medico.shared.adapter.in.rest;

import org.springframework.web.bind.annotation.PostMapping;
import vn.uni.medico.shared.adapter.in.rest.dto.ExportCriteriaDto;

import javax.servlet.http.HttpServletResponse;


public interface ExportController<F> extends IOController {
    @PostMapping("/export")
    void exportData(HttpServletResponse response, ExportCriteriaDto<F> exportCriteria, Integer page, Integer size, String asc, String desc);
}

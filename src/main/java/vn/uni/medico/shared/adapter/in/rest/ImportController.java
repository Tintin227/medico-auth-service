package vn.uni.medico.shared.adapter.in.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import vn.uni.medico.shared.adapter.in.rest.dto.ImportCriteriaDto;

import javax.servlet.http.HttpServletResponse;

public interface ImportController extends IOController {
    @PostMapping("/import")
    void importData(MultipartFile file, ImportCriteriaDto importCriteria, HttpServletResponse response);

    @GetMapping("/import/template")
    void getImportDataTemplate(HttpServletResponse response);
}

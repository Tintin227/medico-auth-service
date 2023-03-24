package vn.uni.medico.shared.adapter.in.rest;

import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.function.Function;

public interface IOController {
    @SneakyThrows
    default Comparable<Boolean> exportFile(HttpServletResponse response, String fileName, Function<OutputStream, Comparable<Boolean>> writeDataToOutputStreamFunction) {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);

        try (OutputStream outputStream = response.getOutputStream()) {
            return writeDataToOutputStreamFunction.apply(outputStream);
        }
    }

    @SneakyThrows
    default <O> List<O> importFile(MultipartFile file, Function<InputStream, List<O>> readDataFromInputStreamFunction) {
        try (InputStream inputStream = file.getInputStream()) {
            return readDataFromInputStreamFunction.apply(inputStream);
        }
    }
}

package vn.uni.medico.shared.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class ResourceUtil {
    private static final AtomicReference<ResourceLoader> resourceLoader = new AtomicReference<>();

    @Autowired
    private ResourceUtil(ResourceLoader resourceLoader) {
        ResourceUtil.resourceLoader.set(resourceLoader);
    }

    public static File getFile(String folderName, String fileName, String fileExtension) {
        try {
            return resourceLoader.get().getResource("classpath:" + folderName + "/" + fileName + "." + fileExtension).getFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File getTemplateFile(String fileName) {
        return getFile("templates", fileName, FileExtension.JSON);
    }

    public static File getReportFile(String fileName) {
        return getFile("reports", fileName, FileExtension.JSON);
    }

    public static class Template {
        public static final String IMPORT_LOGGER = "IMPORT_LOGGER";
    }

    public static class Report {
        public static final String DAILY_REPORT = "DAILY_REPORT";
        public static final String WEEKLY_REPORT = "WEEKLY_REPORT";
        public static final String MONTHLY_REPORT = "MONTHLY_REPORT";
        public static final String YEARLY_REPORT = "YEARLY_REPORT";
    }

    public static class FileExtension {
        private static final String JSON = "json";

        private FileExtension() {
        }
    }

}

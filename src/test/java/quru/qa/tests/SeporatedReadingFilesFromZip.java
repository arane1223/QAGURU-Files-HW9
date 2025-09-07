package quru.qa.tests;

import com.codeborne.pdftest.PDF;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import quru.qa.files.data.File;

import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static quru.qa.files.FilesZip.*;
import static com.codeborne.pdftest.assertj.Assertions.assertThat;


@DisplayName("Отдельные тесты по работе с файлами внутри zip")
public class SeporatedReadingFilesFromZip {

    private ClassLoader cl = SeporatedReadingFilesFromZip.class.getClassLoader();

    @DisplayName("Чтение pdf из zip")
    @Test
    void successfulPdfReadingFromZipTest() throws Exception{
        ZipFile zf = new ZipFile(new java.io.File(ZIP_FULL_PATH));
        ZipInputStream is = new ZipInputStream(cl.getResourceAsStream(ZIP));
        ZipEntry entry;
        while((entry = is.getNextEntry()) != null) {
            assertThat(entry.getName().equals(File.PDF.fileName));
            try (InputStream inputStream = zf.getInputStream(entry)) {
                PDF pdf = new PDF(inputStream);
                assertThat(pdf).containsText("Тестовый PDF-документ");;
            }
        }

    }

    @DisplayName("Чтение xlsx из zip")
    @Test
    void successfulXlsxReadingFromZipTest() throws Exception{

    }

    @DisplayName("Чтение csv из zip")
    @Test
    void successfulCsvReadingFromZipTest() throws Exception{

    }
}

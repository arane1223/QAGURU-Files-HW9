package quru.qa.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import quru.qa.files.data.File;

import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static quru.qa.files.FilesZip.*;
import static com.codeborne.pdftest.assertj.Assertions.assertThat;


@DisplayName("Отдельные тесты по работе с файлами внутри zip")
public class SeporatedReadingFilesFromZip {

    private ClassLoader cl = SeporatedReadingFilesFromZip.class.getClassLoader();

    @DisplayName("Чтение pdf из zip")
    @Test
    void successfulPdfReadingFromZipTest() throws Exception{
        ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream(ZIP_TEST));
        ZipEntry entry;
        boolean fileFind = false;
        while((entry = zis.getNextEntry()) != null) {
            if (entry.getName().endsWith(File.PDF.fileType)) {
                checkPdfData(zis);
                fileFind = true;
            }
        }
        assertThat(fileFind).isTrue();
    }

    @DisplayName("Чтение xlsx из zip")
    @Test
    void successfulXlsxReadingFromZipTest() throws Exception{
        try (InputStream is = cl.getResourceAsStream(ZIP_TEST)) {
            try (ZipInputStream zis = new ZipInputStream(is)) {
                ZipEntry entry;
                boolean fileFind = false;
                while((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().endsWith(File.XLSX.fileType)) {
                        checkXlsxData(zis);
                        fileFind = true;
                    }
                }
                assertThat(fileFind).isTrue();
            }
        }
    }

    @DisplayName("Чтение csv из zip")
    @Test
    void successfulCsvReadingFromZipTest() throws Exception{
        try (InputStream is = cl.getResourceAsStream(ZIP_TEST)) {
            try (ZipInputStream zis = new ZipInputStream(is)) {
                ZipEntry entry;
                boolean fileFind = false;
                while((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().endsWith(File.CSV.fileType)) {
                        checkCsvData(zis);
                        fileFind = true;
                    }
                }
                assertThat(fileFind).isTrue();
            }
        }
    }
}

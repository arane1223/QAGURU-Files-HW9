package quru.qa.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import quru.qa.files.data.File;

import java.util.Enumeration;
import java.util.zip.*;

import static org.assertj.core.api.Assertions.assertThat;
import static quru.qa.files.FilesZip.*;

@DisplayName("Тесты по работе с файлами внутри zip")
public class ReadingFilesFromZip {

private ClassLoader cl = ReadingFilesFromZip.class.getClassLoader();

    @DisplayName("Тесты на чтение соответствие имени файла внутри zip")
    @EnumSource(File.class)
    @ParameterizedTest(name = "Проверка на соответствие имени файла типа {0} в zip архиве")
    void fileNamesFromZipAreCorrectParameterizedTest(File file) throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream(ZIP))) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                System.out.println(entry.getName());
                if(entry.getName().endsWith(file.fileType)) {
                    chekFileNameFromZip(file);
                }
            }
        }
    }

    //На базе кода из источника:
    // https://stackoverflow.com/questions/15667125/read-content-from-files-which-are-inside-zip-file
    //@Disabled("Не работает хз почему")
    @DisplayName("Тест на чтение данных файлов типа pdf, xlsx, csv внутри zip, опираясь на источник")
    @Test
    void informationInsideFilesFromZipCorrectBaseToStackoverflowTest () throws Exception {

        ZipFile zipFile = new ZipFile(new java.io.File(ZIP_FULL_PATH));
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.isDirectory()) {
                if (entry.getName().contains(File.PDF.fileType)) {
                    assertThat(entry.getName()).isEqualTo(File.PDF.fileName);
                    checkPdfData(zipFile.getInputStream(entry));
                } else if (entry.getName().contains(File.XLSX.fileType)) {
                    assertThat(entry.getName()).isEqualTo(File.XLSX.fileName);
                    checkXlsxData(zipFile.getInputStream(entry));
                } else if (entry.getName().contains(File.CSV.fileType)) {
                    assertThat(entry.getName()).isEqualTo(File.CSV.fileName);
                    checkCsvData(zipFile.getInputStream(entry));
                }
            }
        }
    }
}

package quru.qa.files;


import com.codeborne.xlstest.XLS;
import com.codeborne.pdftest.PDF;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import quru.qa.files.data.File;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class FilesZip {

    public static final String ZIP = "Files.zip";
    public static final String ZIP_TEST = "test_files.zip";
    public static final String ZIP_FULL_PATH = "src/test/resources/test_files.zip";

    public static void chekFileNameFromZip (File file) throws Exception {
        Assertions.assertEquals(String.valueOf(file.fileName), file.fileName);
    }

    public static void checkPdfData(ZipInputStream file) throws Exception {
        PDF pdf = new PDF(file);
        assertThat(pdf.text.contains("Sample PDF Content")).isEqualTo(true);
    }

    public static void checkXlsxData(ZipInputStream file) throws Exception {
        byte[] excelData = file.readAllBytes();
        com.codeborne.xlstest.XLS xlsx = new XLS(excelData);
        assertThat(xlsx.excel.getSheetAt(0)
                .getRow(0)
                .getCell(0)
                .getStringCellValue()).isEqualTo("Sample Excel Data");
    }

    public static void checkCsvData(ZipInputStream file) throws Exception {
        CSVReader reader = new CSVReader(new InputStreamReader(file, StandardCharsets.UTF_8));
        List<String[]> data = reader.readAll();

        Assertions.assertEquals(3, data.size());
        Assertions.assertArrayEquals(
                new String[] {"id", "name", "email", "registration_date"},
                data.get(0)
        );
        Assertions.assertArrayEquals(
                new String[] {"1", "Иван Иванов", "ivan@example.com", "2023-01-15"},
                data.get(1)
        );
        Assertions.assertArrayEquals(
                new String[] {"2", "Петр Петров", "peter@example.com", "2023-02-20"},
                data.get(2)
        );
    }
}

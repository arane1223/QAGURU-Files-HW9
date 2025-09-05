package quru.qa.files;


import com.codeborne.xlstest.XLS;
import static org.hamcrest.MatcherAssert.assertThat;
import com.codeborne.pdftest.PDF;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import quru.qa.files.data.File;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static com.codeborne.pdftest.PDF.containsText;
import static org.assertj.core.api.Assertions.assertThat;


public class FilesZip {

    public static final String ZIP = "Files.zip";
    public static final String ZIP_FULL_PATH = "src/test/resources/Files.zip";

    public static void chekFileNameFromZip (File file) throws Exception {
        Assertions.assertEquals(String.valueOf(file.fileName), file.fileName);
    }

    public static void checkPdfData(InputStream file) throws Exception {
        com.codeborne.pdftest.PDF pdf = new PDF(file);
        assertThat(pdf, containsText("Тестовый PDF-документ"));
    }

    public static void checkXlsxData(InputStream file) throws Exception {
        com.codeborne.xlstest.XLS xlsx = new XLS(file);
        assertThat(xlsx.excel.getSheetAt(0)
                .getRow(0)
                .getCell(2)
                .getStringCellValue()
                .contains("Цена продажи"));
    }

    public static void checkCsvData(InputStream file) throws Exception {
        try (CSVReader reader = new CSVReader(new InputStreamReader(file))) {
            List<String[]> data = reader.readAll();
            Assertions.assertEquals(2, data.size());
            Assertions.assertArrayEquals(
                    new String[] {"AlexTerrible", "Qwer!1234"},
                    data.get(0)
            );
            Assertions.assertArrayEquals(
                    new String[] {"arane1223","Arane@1223"},
                    data.get(1)
            );
        }
    }
}

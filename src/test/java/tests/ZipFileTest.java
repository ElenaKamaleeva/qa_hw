package tests;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ZipFileTest {

    // ZIP лежит в src/test/resources/employees.zip
    private static final String ZIP_PATH = "/employees.zip";

    @Test
    void zipContainsAllExpectedFiles() throws Exception {
        boolean hasPdf  = false;
        boolean hasCsv  = false;
        boolean hasXlsx = false;

        try (ZipInputStream zis = new ZipInputStream(
                getClass().getResourceAsStream(ZIP_PATH))) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String name = entry.getName();
                if (name.endsWith(".pdf"))  hasPdf  = true;
                if (name.endsWith(".csv"))  hasCsv  = true;
                if (name.endsWith(".xlsx")) hasXlsx = true;
                zis.closeEntry();
            }
        }

        assertThat(hasPdf).as("ZIP should contain PDF").isTrue();
        assertThat(hasCsv).as("ZIP should contain CSV").isTrue();
        assertThat(hasXlsx).as("ZIP should contain XLSX").isTrue();
    }

    @Test
    void csvFileContainsExpectedContent() throws Exception {
        String csvContent = readFileFromZip("employees.csv");

        assertThat(csvContent)
                .contains("id,name,department,salary")
                .contains("John Smith")
                .contains("Engineering")
                .contains("95000");
    }

    @Test
    void pdfFileContainsExpectedContent() throws Exception {
        String pdfContent = readFileFromZip("employees.pdf");

        // Проверяем что это валидный PDF (начинается с сигнатуры)
        assertThat(pdfContent).startsWith("%PDF");
        // Проверяем наличие текста в сыром содержимом
        assertThat(pdfContent).contains("Employees Report 2024");
    }

    @Test
    void xlsxFileIsNotEmpty() throws Exception {
        // XLSX — бинарный формат, проверяем что файл не пустой
        // и содержит сигнатуру ZIP (XLSX внутри это ZIP)
        byte[] xlsxBytes = readBytesFromZip("employees.xlsx");

        assertThat(xlsxBytes).isNotEmpty();
        // Первые байты XLSX/ZIP: PK (0x50 0x4B)
        assertThat(xlsxBytes[0]).isEqualTo((byte) 0x50); // P
        assertThat(xlsxBytes[1]).isEqualTo((byte) 0x4B); // K
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private String readFileFromZip(String fileName) throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                getClass().getResourceAsStream(ZIP_PATH))) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals(fileName)) {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(zis));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    return sb.toString();
                }
                zis.closeEntry();
            }
        }
        throw new RuntimeException("File not found in ZIP: " + fileName);
    }

    private byte[] readBytesFromZip(String fileName) throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                getClass().getResourceAsStream(ZIP_PATH))) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals(fileName)) {
                    return zis.readAllBytes();
                }
                zis.closeEntry();
            }
        }
        throw new RuntimeException("File not found in ZIP: " + fileName);
    }
}
package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.model.Diploma;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DiplomaExportServiceTest {

  @Mock private DiplomaService diplomaService;

  @InjectMocks private DiplomaExportService diplomaExportService;

  @Test
  void shouldExportGraduatesToXlsx() throws IOException {
    Diploma graduate =
        Diploma.builder()
            .rank(1)
            .studentNumber("STD001")
            .firstName("Jean")
            .lastName("Rakoto")
            .average(12.5)
            .build();
    when(diplomaService.getGraduatesByPromotion("promotion-1")).thenReturn(List.of(graduate));

    byte[] result = diplomaExportService.exportGraduatesByPromotion("promotion-1");

    assertNotNull(result);
    assertArrayEquals(new byte[] {'P', 'K'}, new byte[] {result[0], result[1]});
    try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(result))) {
      var sheet = workbook.getSheet("Diplomes");
      assertNotNull(sheet);
      assertEquals("Rang", sheet.getRow(0).getCell(0).getStringCellValue());
      assertEquals("Matricule", sheet.getRow(0).getCell(1).getStringCellValue());
      assertEquals("Nom", sheet.getRow(0).getCell(2).getStringCellValue());
      assertEquals("Prenom", sheet.getRow(0).getCell(3).getStringCellValue());
      assertEquals("Moyenne generale", sheet.getRow(0).getCell(4).getStringCellValue());
      assertEquals(1, sheet.getRow(1).getCell(0).getNumericCellValue());
      assertEquals("STD001", sheet.getRow(1).getCell(1).getStringCellValue());
      assertEquals("Rakoto", sheet.getRow(1).getCell(2).getStringCellValue());
      assertEquals("Jean", sheet.getRow(1).getCell(3).getStringCellValue());
      assertEquals(12.5, sheet.getRow(1).getCell(4).getNumericCellValue());
    }
    verify(diplomaService).getGraduatesByPromotion("promotion-1");
  }

  @Test
  void shouldExportEmptyWorkbookWhenPromotionHasNoGraduates() throws IOException {
    when(diplomaService.getGraduatesByPromotion("promotion-1")).thenReturn(List.of());

    byte[] result = diplomaExportService.exportGraduatesByPromotion("promotion-1");

    try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(result))) {
      var sheet = workbook.getSheet("Diplomes");
      assertNotNull(sheet);
      assertNotNull(sheet.getRow(0));
      assertEquals(0, sheet.getLastRowNum());
    }
    verify(diplomaService).getGraduatesByPromotion("promotion-1");
  }
}

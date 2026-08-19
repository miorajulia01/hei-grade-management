package com.example.demo.service;

import com.example.demo.model.Diploma;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiplomaExportService {

  private final DiplomaService diplomaService;

  public byte[] exportGraduatesByPromotion(String promotionId) {
    List<Diploma> graduates = diplomaService.getGraduatesByPromotion(promotionId);

    try (Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream output = new ByteArrayOutputStream()) {
      Sheet sheet = workbook.createSheet("Diplomes");
      Row header = sheet.createRow(0);
      header.createCell(0).setCellValue("Rang");
      header.createCell(1).setCellValue("Matricule");
      header.createCell(2).setCellValue("Nom");
      header.createCell(3).setCellValue("Prenom");
      header.createCell(4).setCellValue("Moyenne generale");

      for (int index = 0; index < graduates.size(); index++) {
        Diploma graduate = graduates.get(index);
        Row row = sheet.createRow(index + 1);
        row.createCell(0).setCellValue(graduate.getRank());
        row.createCell(1).setCellValue(graduate.getStudentNumber());
        row.createCell(2).setCellValue(graduate.getLastName());
        row.createCell(3).setCellValue(graduate.getFirstName());
        row.createCell(4).setCellValue(graduate.getAverage());
      }

      for (int column = 0; column < 5; column++) {
        sheet.autoSizeColumn(column);
      }

      workbook.write(output);
      return output.toByteArray();
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to generate diploma export", exception);
    }
  }
}

package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Service
public class PdfService {

  public File generatePdfFromHtml(String html, String fileNamePrefix) throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    ITextRenderer renderer = new ITextRenderer();
    renderer.setDocumentFromString(html);
    renderer.layout();
    renderer.createPDF(outputStream);

    File tempFile = File.createTempFile(fileNamePrefix, ".pdf");
    try (FileOutputStream fos = new FileOutputStream(tempFile)) {
      fos.write(outputStream.toByteArray());
    }
    return tempFile;
  }
}

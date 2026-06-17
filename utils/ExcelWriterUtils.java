package utils;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriterUtils {

    public static void writeAllCartDetails(List<String[]> carts) {

        String folder = "D:/Eclipse/DataDownload";
        String filePath =
            folder + "/CartDetails_" + LocalDate.now() + ".xlsx";

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("CartDetails");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Cart Number");
        header.createCell(1).setCellValue("Machine Number");
        header.createCell(2).setCellValue("QR Code URL");

        int rowNum = 1;
        for (String[] cart : carts) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(cart[0]);
            row.createCell(1).setCellValue(cart[1]);
            row.createCell(2).setCellValue(cart[2]);
        }

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
            workbook.close();
            System.out.println("📄 Excel created: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

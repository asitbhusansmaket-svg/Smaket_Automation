package utils;

import java.io.FileInputStream;
import java.util.*;
import org.apache.poi.ss.usermodel.*;

public class ExcelUtils {

    // ===== CART DATA =====
    public static List<String[]> readCartData(String path, String sheetName) {

        List<String[]> data = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(path);
             Workbook wb = WorkbookFactory.create(fis)) {

            Sheet sheet = wb.getSheet(sheetName);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null) continue;

                String prefix  = getValue(row.getCell(0));
                String number  = getValue(row.getCell(1));
                String machine = getValue(row.getCell(2));

                if (prefix.isEmpty()) continue;

                data.add(new String[]{ prefix, number, machine });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    // ===== PRODUCT DATA =====
    public static List<Map<String, String>> readProductManagementData(
            String path, String sheetName) {

        List<Map<String, String>> dataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(path);
             Workbook wb = WorkbookFactory.create(fis)) {

            Sheet sheet = wb.getSheet(sheetName);
            Row headerRow = sheet.getRow(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, String> rowData = new HashMap<>();

                for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                    rowData.put(
                        getValue(headerRow.getCell(j)),
                        getValue(row.getCell(j))
                    );
                }
                dataList.add(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    private static String getValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:  return cell.getStringCellValue().trim();
            case NUMERIC: return String.valueOf((long) cell.getNumericCellValue());
            default: return "";
        }
    }
}

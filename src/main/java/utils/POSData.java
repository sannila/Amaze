package utils;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class POSData {

    public static FileInputStream file;
    public static XSSFWorkbook workbook;
    public static XSSFSheet sheet;

    public String getSheet(String sheetName, Integer row, Integer cell){
        try{
            file = new FileInputStream(new File("src/main/resources/AmazePOSData.xlsx").getAbsolutePath());
        } catch (FileNotFoundException e) {
            Log.warn("Exception while getting Amaze POS Data sheet path: " + e.getMessage());
        }

        try{
            workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            Log.warn("Exception while getting Amaze POS Data workbook: " + e.getMessage());
        }

        sheet = workbook.getSheet(sheetName);
        String data = sheet.getRow(row).getCell(cell).toString();
        return data;
    }
}

package com.devdehaine.report_system.models;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

public class ExcelMaker {


    public static void readFromExcelFile(String pathToFile){
        try {
            FileInputStream file = new FileInputStream(new File(pathToFile));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Map<Integer, List<String>> data = new HashMap<>();
            int  i = 0;
            for (Row row : sheet){
                data.put(i,new ArrayList<String>());
                for (Cell cell : row){
                    switch (cell.getCellTypeEnum()){
                        case STRING: data.get(new Integer(i)).add(cell.getRichStringCellValue().toString()); break;
                        case NUMERIC: if (DateUtil.isCellDateFormatted(cell)){
                            data.get(i).add(cell.getDateCellValue().toString()); break;
                        }else{
                            data.get(i).add(cell.getNumericCellValue() + ""); break;
                        }
                        case BOOLEAN: data.get(i).add(cell.getBooleanCellValue() + ""); break;
                        case FORMULA: data.get(i).add(cell.getCellFormula().toString()); break;
                        default:data.get(new Integer(i)).add(" ");
                    }
                }
                i++;
            }

            System.out.println("Data in excel : " + data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeToExcelFile(){
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Persons");
        sheet.setColumnWidth(0,6000);
        sheet.setColumnWidth(1,4000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);

        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Age");
        headerCell.setCellStyle(headerStyle);

        //Writing the content below with a different style

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);


        Row row = sheet.createRow(2);
        Cell cell = row.createCell(0);
        cell.setCellValue("Öluwatobi");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(40);
        cell.setCellStyle(style);

        // Writing the content to  a file in  current directory

        File currentDirectory = new File(".");
        String path = currentDirectory.getAbsolutePath();
        System.out.println("Path : " + path);
        String fileLocation = path.substring(0,path.length() -1) + "new.xlsx";
        System.out.println("File_Location : " + fileLocation);
        try {
            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readFromDBAndWriteToExcelFile(Map<String,Object> excelConfig,List<Map<String,String>> excelData,List<String> columnNames){
        Workbook workbook = new XSSFWorkbook();



        Sheet sheet = workbook.createSheet(excelConfig.get("sheetName").toString());
        int noOfColumn = Integer.parseInt(excelConfig.get("noOfColumn").toString());
        for (int i = 0 ; i < noOfColumn; i++){
            sheet.setColumnWidth(i,6000);
        }
        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);

        headerStyle.setFont(font);


        Cell headerCell;
        for (int i = 0 ; i < noOfColumn; i++){
            headerCell = header.createCell(i);
            headerCell.setCellValue(columnNames.get(i));
            headerCell.setCellStyle(headerStyle);
        }


        //Writing the content below with a different style

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);


        int count = 1;

        for (int i = 0; i < excelData.size(); i++){
            Row row = sheet.createRow(count);
            count++;
            for (int j = 0; j < columnNames.size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(excelData.get(i).get(columnNames.get(j)));
                cell.setCellStyle(style);
                //  System.out.println("data insert : " + excelData.get(i).get(columnNames.get(j)));
            }


        }
        // Writing the content to  a file in  current directory

        File currentDirectory = new File(".");
        String path = currentDirectory.getAbsolutePath();
        System.out.println("Path : " + path);
        String fileLocation = path.substring(0,path.length() -1) + "dataFromDb.xlsx";
        System.out.println("File_Location : " + fileLocation);
        try {
            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

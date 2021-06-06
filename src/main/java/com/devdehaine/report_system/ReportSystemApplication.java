package com.devdehaine.report_system;

import com.devdehaine.report_system.models.DBConfiguration;
import com.devdehaine.report_system.models.ExcelMaker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class ReportSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportSystemApplication.class, args);

        DBConfiguration dbConfiguration = new DBConfiguration("localhost","3306",
                "com.mysql.jdbc.Driver","root","dhaineroot@12","tobi",
                "select * from transactions");




        Map<String,Object> excelConfig = new HashMap<>();
        List<Map<String,String>> excelData = new ArrayList<>();

        List<String> columnNames = dbConfiguration.fetchColumnNames();

        excelData = dbConfiguration.fetchDataForExcel();
//        for (int i = 0; i < excelData.size(); i++){
//            System.out.println("Result : " + excelData.get(i));
//        }
//        System.out.println("the size of data : " + excelData.size() );
        excelConfig.put("sheetName","Record");
        excelConfig.put("noOfColumn",String.valueOf(columnNames.size()));
//        columnNames.add("First name");
//        columnNames.add("Second name");


        ExcelMaker.readFromDBAndWriteToExcelFile(excelConfig,excelData,columnNames);


    }

}

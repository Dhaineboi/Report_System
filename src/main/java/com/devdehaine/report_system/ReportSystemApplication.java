package com.devdehaine.report_system;

import com.devdehaine.report_system.models.DBConfiguration;
import com.devdehaine.report_system.services.ExcelMaker;
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

//        DBConfiguration dbConfiguration = new DBConfiguration("localhost","3306",
//                "com.mysql.jdbc.Driver","root","dhaineroot@12","tobi",
//                "select * from transactions");

        DBConfiguration dbConfiguration = new DBConfiguration("128.199.143.171","1433",
                "net.sourceforge.jtds.jdbc.Driver","sa","Long8r!dge","TIS",
                "select 'Forma' as entity_type, currency_code, count(1) as count,isnull(sum(amount),0)as totalamount,convert(date, date_created, 20) as datecreated\n" +
                        "from forma where currency_code = 'USD' and (status = 'S' or status='RS')\n" +
                        "             and date_Created between ( '2021-05-26' ) and ('2021-05-29' )\n" +
                        "             and cifid = '000061477'\n" +
                        "group by currency_code, convert(date, date_created, 20)\n" +
                        "union\n" +
                        "select 'ImportLc' as entity_type, lc_currency, count(1) as count,isnull(sum(lc_amount),0)as totalamount,convert(date, date_created, 20) as datecreated\n" +
                        "from lc where lc_currency = 'USD' and status = 'S'\n" +
                        "          and date_Created between ( '2021-05-26' ) and ('2021-05-29' )\n" +
                        "          and cifid = '000061477'\n" +
                        "group by lc_currency, convert(date, date_created, 20)\n" +
                        "union\n" +
                        "Select 'FormM' as entity_type,currency_code,count(1) as count,isnull(sum(form_amount),0)as totalamount,convert(date, date_created, 20) as datecreated\n" +
                        "from formm\n" +
                        "where cifid = '000061477'\n" +
                        "  and currency_code= 'USD' and status = 'RL'\n" +
                        "  and date_Created between ( '2021-05-26' ) and ('2021-05-29' )\n" +
                        "group by currency_code, convert(date, date_created, 20)");




        Map<String,Object> excelConfig = new HashMap<>();
        List<Map<String,String>> excelData = new ArrayList<>();
        List<String> columnNames = new ArrayList<>();
        List<List<Map<String,String>>> returnedData = dbConfiguration.fetchDataForExcel();




            excelData = returnedData.get(1);

            for (int i = 1; i <= returnedData.get(0).get(0).size(); i++){
                columnNames.add(returnedData.get(0).get(0).get("column_"+i));
            }

            System.out.println("the actual data size = " + excelData.size());
            System.out.println("the actual data = " + excelData);
            System.out.println("the number of columns size = " + columnNames.size());
            System.out.println("the names of columns  = " + columnNames);




       // excelData = dbConfiguration.fetchDataForExcel();
//        for (int i = 0; i < excelData.size(); i++){
//            System.out.println("Result : " + excelData.get(i));
//        }
//        System.out.println("the size of data : " + excelData.size() );
        excelConfig.put("sheetName","Record");
        excelConfig.put("noOfColumn",String.valueOf(columnNames.size()));
//        columnNames.add("First name");
//        columnNames.add("Second name");


      //  ExcelMaker.readFromDBAndWriteToExcelFile(excelConfig,excelData,columnNames);


    }

}

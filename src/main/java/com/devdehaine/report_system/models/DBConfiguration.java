package com.devdehaine.report_system.models;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBConfiguration {
    private Connection conn;
    private String url;
    private String ipAddress;
    private String port;
    private String driver;
    private String userName;
    private String password;
    private String schema;
    private PreparedStatement stmt;
    private String query;

    // = "jdbc:jtds:sqlserver://10.100.2.4:1433;databasename=Efass001";
    // "net.sourceforge.jtds.jdbc.Driver";

    public DBConfiguration() {
    }

    public DBConfiguration(String ipAddress, String port, String driver, String userName, String password, String schema, String query) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.driver = driver;
        this.userName = userName;
        this.password = password;
        this.schema = schema;
        this.query = query;
        //this.url = "jdbc:mysql://"+this.ipAddress+":"+this.port+"/"+this.schema;
        this.url = "jdbc:jtds:sqlserver://"+this.ipAddress+":"+this.port+";databasename="+this.schema;
        System.out.println("The url is : " + this.url);
    }

    //    public void runQuery() {
//        try {
//            Class.forName(driver);
//            conn = DriverManager.getConnection(url, userName, password);
//            String query = "select * from customer";
//            String query2 = "insert into customer values (?,?)";
//            stmt = conn.prepareStatement(query2);
//            stmt.setString(1, firstName);
//            stmt.setString(2, secondName);
//            int row = ((PreparedStatement) stmt).executeUpdate();
//            System.out.println("äffected rows number : " + row);
//            stmt = conn.prepareStatement(query);
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                System.out.println("Result : " + rs.getString("First name"));
//                System.out.println("Result2 : " + rs.getString("Second name"));
//            }
//            rs.close();
//            conn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }




    public  List<List<Map<String, String>>> fetchDataForExcel() {

        List<List<Map<String, String>>> data = new ArrayList<>();
        List<Map<String, String>> columnNames = new ArrayList<>();
        List<Map<String, String>> values = new ArrayList<>();
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);
           // String query = "select * from customer";
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();

            int columnCount = metaData.getColumnCount();
            Map<String, String> columnData = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                System.out.println("Name of column : " + columnName);
                columnData.put("column_"+i,columnName);
            }
            System.out.println("names here = " + columnData);
            columnNames.add(columnData);
            while(rs.next()) {
                Map<String, String> mapdata = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    mapdata.put(metaData.getColumnName(i), rs.getString(metaData.getColumnName(i)));
                }
                values.add(mapdata);
            }


            data.add(columnNames);
            data.add(values);
            System.out.println("The data size is : " + data.size());

//            while(rs.next()){
//                Map<String, String> mapdata = new HashMap<>();
//                mapdata.put("First name", rs.getString("First name"));
//                mapdata.put("Second name", rs.getString("Second name"));
//                data.add(mapdata);
//            }


            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public List<String> fetchColumnNames() {
        List<String> columnNames = new ArrayList<>();
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);
          //  String query = "select * from customer";
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();

            int columnCount = metaData.getColumnCount();
            for (int i = 1; i < columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                System.out.println("Name of column : " + columnName);
                columnNames.add(columnName);
            }


            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return columnNames;

    }


}

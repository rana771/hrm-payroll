package com.bracu.hrm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by ASUS on 07-Jan-18.
 */
public class SQLDataSource {
    private Connection con;
public Connection getSqlConnection(){
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String userName = "bu_it";
            String password = "";
            String url = "jdbc:sqlserver://";
            //String url = "jdbc:sqlserver://";
            con = DriverManager.getConnection(url, userName, password);
            //String result = new result[20];

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    return  con;
    }

}

package practica9;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    
    public boolean connect(String user, String password){
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            con = DriverManager.getConnection(
                    "jdbc:mysql://mozart.dis.ulpgc.es/DIU_BD?useSSL=true",
                    user,
                    password);
         
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            return false;
        }
        
        return true;
    }
    
    public List<String> getTables(){
        List<String> tables = new ArrayList();
        
        try {
            DatabaseMetaData md = con.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = md.getTables(null, null, "%", types);
            
            while(rs.next()){
                tables.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tables;
    }
    
    public List<String> getColumns(String table){
        List<String> columns = new ArrayList();
             
        try {
            DatabaseMetaData md = con.getMetaData();
            ResultSet rs = md.getColumns(null, null, table, null);
        
            while(rs.next()){
                columns.add(rs.getString("COLUMN_NAME"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return columns;
    }
    
    public void close(){
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Connection con;
}

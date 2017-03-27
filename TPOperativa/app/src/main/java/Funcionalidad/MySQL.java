package Funcionalidad;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




/**
 * Created by Maxi on 23/3/2017.
 */

public class MySQL{
    private String dateBase;
    private String user;
    private String pass;
    private Connection conn;



    public MySQL(String db, String user, String pass) {
        this.user = user;
        this.pass = pass;
        this.dateBase = db;
    }

    public String getDateBase() {
        return dateBase;
    }

    public void setDateBase(String dateBase) {
        this.dateBase = dateBase;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public ResultSet getResultset(String query){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/" +
                    this.dateBase, this.user, this.pass);
            Statement st = conn.createStatement();

            return st.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Connection getConnection(){
        return this.conn;
    }


}

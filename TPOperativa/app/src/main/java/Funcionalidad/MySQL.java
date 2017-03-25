package Funcionalidad;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * Created by Maxi on 23/3/2017.
 */

public class MySQL{

    private String dateBase;
    private String user;
    private String pass;



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


}

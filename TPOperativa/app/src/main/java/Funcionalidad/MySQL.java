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

public class MySQL extends Thread{

    private String dateBase;
    private String user;
    private String pass;
    private boolean used;
    private AbsQuery consulta;
    private String query;




    public MySQL(String db, String user, String pass) {
        this.user = user;
        this.pass = pass;
        this.dateBase = db;
        this.used = true;
        this.query="";
    }


    public void setConsulta(AbsQuery query) {
        this.consulta = query;
    }

    public AbsQuery getConsulta() {
        return consulta;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void run() {
            consulta.execute(dateBase,user,pass);
    }
}

package Funcionalidad;


import android.util.Log;

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
    private Statement st;



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

    public void CloseConecction(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Statement crearConn(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            // "jdbc:mysql://IP:PUERTO/DB", "USER", "PASSWORD");
            // Si estás utilizando el emulador de android y tenes el mysql en tu misma PC no utilizar 127.0.0.1 o localhost como IP, utilizar 10.0.2.2
            //TODO: Modificar la direccion de la base de datos
            //http://dbases.exa.unicen.edu.ar:8080/phpgadmin/
            //unc_248361
            //kobebryant
            conn = DriverManager.getConnection("jdbc:mysql://192.168.43.195:3306/" +
                    this.dateBase, this.user, this.pass);

            st = conn.createStatement();
            st.executeUpdate("USE operativa");

            Log.v("MySql","Conexion con BD establecida.");
        } catch (SQLException se) {
            Log.v("MySql","oops! No se puede conectar. Error: " + se.toString());
        } catch (ClassNotFoundException e) {
            Log.v("MySql","oops! No se encuentra la clase. Error: " + e.getMessage());
        }

        return st;
    }

    public ResultSet getResultset(String s){
        try {
            crearConn();
            return st.executeQuery(s);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.v("MySql","Error en executeQuery-MySql");
        }
        return null;
    }

    public int executeQuery(String s){
        try {
            crearConn();
            int rs = st.executeUpdate(s);
            Log.d("Retorno","Valor= "+rs);
            return rs;
        } catch (SQLException e) {
            Log.d("Retorno","Valor= NULO");
            e.printStackTrace();
            return -1;
        }
    }


}

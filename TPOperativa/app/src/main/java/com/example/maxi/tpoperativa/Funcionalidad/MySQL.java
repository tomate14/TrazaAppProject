package com.example.maxi.tpoperativa.Funcionalidad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Maxi on 17/3/2017.
 */

public class MySQL {

    private static Connection Conexion;

    public void MySQLConnection(String user, String pass, String db_name) throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Conexion = DriverManager.getConnection("jdbc:mysql:http://127.0.0.1:8081/phpmyadmin/" + db_name, user, pass);
            //Conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db_name, user, pass);
            //JOptionPane.showMessageDialog(null, "Se ha iniciado la conexión con el servidor de forma exitosa");
        } catch (ClassNotFoundException ex) {
           // Logger.getLogger(MysqlXid.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
       //     Logger.getLogger(MysqlXid.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void closeConnection() {
        try {
            Conexion.close();
           // JOptionPane.showMessageDialog(null, "Se ha finalizado la conexión con el servidor");
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void createDB(String name) {
        try {
            String Query = "CREATE DATABASE " + name;
            Statement st = (Statement) Conexion.createStatement();
            st.executeUpdate(Query);
            closeConnection();
            try {
                MySQLConnection("root", "", name);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
         //   JOptionPane.showMessageDialog(null, "Se ha creado la base de datos " + name + " de forma exitosa");
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createTableUsuarios(String name) {
        try {
            String Query = "CREATE TABLE " + name + ""
                    + "(ID INT PRIMARY KEY, User VARCHAR(50), Pass VARCHAR(50), Nombre VARCHAR(50), Apellido VARCHAR(50),"
                    + " Telefono VARCHAR(25), Domicilio VARCHAR(50), Ciudad VARCHAR(50), RazonSocial VARCHAR(50), Admin VARCHAR(50))";

            Statement st = (Statement) Conexion.createStatement();
            st.executeUpdate(Query);
        //    JOptionPane.showMessageDialog(null, "Se ha creado la tabla " + name + " de forma exitosa");
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createTableRecurso(String name) {
        try {
            String Query = "CREATE TABLE " + name + ""
                    + "(ID INT PRIMARY KEY, Nombre VARCHAR(50), Descripcion VARCHAR(50), Estado VARCHAR(25),"
                    + " Cantidad INT, Ubicacion VARCHAR(500), Ciudad VARCHAR(500), Persona VARCHAR(50), Consumible VARCHAR(25))";

            Statement st = (Statement) Conexion.createStatement();
            st.executeUpdate(Query);
         //   JOptionPane.showMessageDialog(null, "Se ha creado la tabla " + name + " de forma exitosa");
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertUsuario(int ID, String user, String pass, String name, String lastname, String telefono, String domicilio, String ciudad, String razonSocial, String admin) {
        try {
            String Query = "INSERT INTO usuarios VALUES("
                    + "\"" + ID + "\", "
                    + "\"" + user + "\", "
                    + "\"" + pass + "\", "
                    + "\"" + name + "\", "
                    + "\"" + lastname + "\", "
                    + "\"" + telefono + "\", "
                    + "\"" + domicilio + "\", "
                    + "\"" + ciudad + "\", "
                    + "\"" + razonSocial + "\", "
                    + "\"" + admin + "\")";
            Statement st = (Statement) Conexion.createStatement();
            st.executeUpdate(Query);
          //  JOptionPane.showMessageDialog(null, "Datos almacenados de forma exitosa");
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Error en el almacenamiento de datos");
        }
    }

    public void insertRecurso(int ID, String name, String descripcion, String estado, int cantidad, String ubicacion, String ciudad, String persona, String consumible) {
        try {
            String query = "INSERT INTO recursos VALUES("
                    + "\"" + ID + "\", "
                    + "\"" + name + "\", "
                    + "\"" + descripcion + "\", "
                    + "\"" + estado + "\", "
                    + "\"" + cantidad + "\", "
                    + "\"" + ubicacion + "\", "
                    + "\"" + ciudad + "\", "
                    + "\"" + persona + "\", "
                    + "\"" + consumible + "\")";

            Statement st = (Statement) Conexion.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("error al escribir la BD");
        }
    }

    public void modificarRecurso(int ID, int personaN, String domicilio, String ciudadN){
        try {
            String query = "SELECT ubicacion, ciudad, persona FROM recursos WHERE ID= " + ID ;
            Statement st = (Statement) Conexion.createStatement();
            java.sql.ResultSet resultSet;
            resultSet = st.executeQuery(query);

            String ubicacion = null;
            String ciudad = null;
            String persona = null;
            while (resultSet.next()) {
                ubicacion = resultSet.getString("ubicacion");
                ciudad = resultSet.getString("ciudad");
                persona = resultSet.getString("persona");
            }

            ubicacion = ubicacion +"," + domicilio;
            ciudad = ciudad +"," + ciudadN;
            persona= persona +"," + personaN;

            String query2 = "UPDATE recursos SET "
                    + "persona= '" + persona + "',"
                    + "ubicacion= '"+ ubicacion + "',"
                    + "ciudad= '" + ciudad + "'"
                    + "WHERE id = " + ID ;

            st.executeUpdate(query2);
         //   JOptionPane.showMessageDialog(null, "Recurso modificado de forma exitosa");
        } catch (SQLException ex) {
         //   JOptionPane.showMessageDialog(null, "Error al modificar el recurso");
        }
    }


    public Vector<Persona> getUsuarios() {
        Vector<Persona> personas = new Vector<Persona>();
        try {
            String query = "SELECT * FROM usuarios " ;
            Statement st = (Statement) Conexion.createStatement();
            java.sql.ResultSet resultSet;
            resultSet = st.executeQuery(query);

            while (resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString("ID"));
                String user = resultSet.getString("user");
                String pass = resultSet.getString("pass");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String telefono = resultSet.getString("telefono");
                String domicilio = resultSet.getString("domicilio");
                String ciudad = resultSet.getString("ciudad");
                String razon_social = resultSet.getString("razonSocial");
                boolean admin = Boolean.parseBoolean(resultSet.getString("admin"));

                Persona p = new Persona(id, user, pass, nombre, apellido, telefono, domicilio, ciudad, razon_social, admin);

                personas.add(p);
            }

        } catch (SQLException ex) {
         //   JOptionPane.showMessageDialog(null, "Error en la adquisición de usuarios");
        }

        return personas;
    }

    public int getID(String nombre, String descripcion, String estado, int cantidad, String domicilio, String ciudad, int persona, String consumible){
        int id=-1;
        try {
            String query = "SELECT ID FROM recursos WHERE "
                    + "nombre= '" + nombre + "',"
                    + "descripcion= '" + descripcion + "',"
                    + "estado= '" + estado + "',"
                    + "cantidad= '" + cantidad + "',"
                    + "ubicacion= '" + domicilio + "',"
                    + "ciudad= '" + ciudad + "',"
                    + "persona= '" + persona + "',"
                    + "consumible= '" + consumible + "';";
            Statement st = (Statement) Conexion.createStatement();
            java.sql.ResultSet resultSet;
            resultSet = st.executeQuery(query);

            while (resultSet.next()) {
                id = Integer.parseInt(resultSet.getString("ID"));
            }

        } catch (SQLException ex) {
          //  JOptionPane.showMessageDialog(null, "Error en la adquisición de recursos");
        }

        return id;
    }
    public Vector<Recurso> getRecursos() {
        Vector<Recurso> recursos = new Vector<Recurso>();
        try {
            String query = "SELECT * FROM recursos " ;
            Statement st = (Statement) Conexion.createStatement();
            java.sql.ResultSet resultSet;
            resultSet = st.executeQuery(query);

            while (resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString("ID"));
                String nombre = resultSet.getString("nombre");
                String descripcion = resultSet.getString("descripcion");
                String estado = resultSet.getString("estado");
                int cantidad = Integer.parseInt(resultSet.getString("cantidad"));
                String ubicacion = resultSet.getString("ubicacion");
                String ciudad = resultSet.getString("ciudad");
                String persona = resultSet.getString("persona");
                boolean consumible = Boolean.parseBoolean(resultSet.getString("consumible"));

                Recurso r = new Recurso(id, nombre, descripcion, estado, cantidad, ubicacion, ciudad, persona, consumible);

                recursos.add(r);
            }

        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Error en la adquisición de recursos");
        }

        return recursos;
    }

//	 public void showUsuarios() {
//	        try {
//	            String query = "SELECT * FROM usuarios " ;
//	            Statement st = (Statement) Conexion.createStatement();
//	            java.sql.ResultSet resultSet;
//	            resultSet = st.executeQuery(query);
//
//	            System.out.println("Usuarios:");
//
//	            while (resultSet.next()) {
//	                System.out.println("ID: " + resultSet.getString("ID") + " "
//	                		+ "User: " + resultSet.getString("user") + " "
//	                		+ "Pass: " + resultSet.getString("pass") + " "
//	                        + "Nombre: " + resultSet.getString("Nombre") + " " + resultSet.getString("Apellido") + " "
//	                        + "Telefono: " + resultSet.getString("telefono") + " "
//	                        + "Domicilio: " + resultSet.getString("domicilio") + " "
//	                        + "Ciudad: " + resultSet.getString("ciudad") + " "
//	                        + "Razon Social: " + resultSet.getString("razonSocial") + " "
//	                        + "Admin: " + resultSet.getString("admin"));
//	            }
//
//	            System.out.println();
//
//	        } catch (SQLException ex) {
//	            JOptionPane.showMessageDialog(null, "Error en la adquisición de usuarios");
//	        }
//	    }
//
//	 public void showRecursos() {
//	        try {
//	            String query = "SELECT * FROM recursos " ;
//	            Statement st = (Statement) Conexion.createStatement();
//	            java.sql.ResultSet resultSet;
//	            resultSet = st.executeQuery(query);
//
//	            System.out.println("Recursos:");
//
//	            while (resultSet.next()) {
//	                System.out.println("ID: " + resultSet.getString("ID") + " "
//	                		+ "Nombre: " + resultSet.getString("nombre") + " "
//	                		+ "Descripcion: " + resultSet.getString("descripcion") + " "
//	                        + "Estado: " + resultSet.getString("estado") + " "
//	                        + "Cantidad: " + resultSet.getString("cantidad") + " "
//	                        + "Ubicacion: " + resultSet.getString("ubicacion") + " "
//	                        + "Ciudad: " + resultSet.getString("ciudad") + " "
//	                        + "Persona: " + resultSet.getString("persona"));
//	            }
//
//	            System.out.println();
//
//	        } catch (SQLException ex) {
//	            JOptionPane.showMessageDialog(null, "Error en la adquisición de recursos");
//	        }
//	    }

    public void deleteRecord(String table_name, String ID) {
        try {
            String query = "DELETE FROM " + table_name + " WHERE ID = \"" + ID + "\"";
            Statement st = (Statement) Conexion.createStatement();
            st.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            //JOptionPane.showMessageDialog(null, "Error borrando el registro especificado");
        }
    }


    public int getLastResourcesID() {
        Vector<Recurso> recursos = this.getRecursos();
        int valor = recursos.size();
        return valor + 1;
    }


    public int getLastUserID() {
        Vector<Persona> personas = this.getUsuarios();
        int valor = personas.size();
        return valor + 1;
    }

}
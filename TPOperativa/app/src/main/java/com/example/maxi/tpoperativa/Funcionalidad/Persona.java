package com.example.maxi.tpoperativa.Funcionalidad;
import java.io.Serializable;

/**
 * Created by Maxi on 17/3/2017.
 */

public class Persona implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int id; //identificador unico

    private String pass;
    private String user;

    private String nombre;
    private String apellido;
    private String telefono;
    private String domicilio;
    private String ciudad;
    private String razon_social;
    private boolean admin;

    private String foto; //puede ir o no

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }


    public String getUser() {
        return user;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Persona(int id, String u, String pass, String nombre, String apellido, String telefono, String domicilio, String ciudad,
                   String razon_social, boolean admin) {
        super();
        this.id = id;
        this.pass = pass;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.domicilio = domicilio;
        this.razon_social = razon_social;
        this.ciudad=ciudad;
        this.user=u;
        this.admin=admin;
    }



}
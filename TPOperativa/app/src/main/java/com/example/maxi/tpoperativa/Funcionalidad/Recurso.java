package com.example.maxi.tpoperativa.Funcionalidad;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by Maxi on 17/3/2017.
 */

public class Recurso implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String nombre;
    private String descripcion;
    private String estado;
    private int cantidad;

    private Vector<String> v= new Vector<String>();	//Serian las ids de las personas, las cuales fueron propietarios

    private Vector<String> ubicacion=new Vector<String>(); //direccion + nro (todo String)
    private Vector<String> ciudad=new Vector<String>();	//ciudad (String)

    private boolean consumible;

    public Recurso(int id, String nombre, String descripcion, String estado, int cantidad, String ubicacion, String ciudad, String pers, boolean consumible) {
        super();
        this.id=id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.cantidad = cantidad;


        String[] ubicaciones = ubicacion.split(",");
        for (String ubi : ubicaciones) {
            this.ubicacion.addElement(ubi);
        }

        String[] ciudades = ciudad.split(",");
        for (String city : ciudades) {
            this.ciudad.addElement(city);
        }

        String[] personas = pers.split(",");
        for (String person : personas) {
            this.v.addElement(person);
        }

        this.consumible = consumible;
    }

    public Vector<String> getCiudad() {
        return ciudad;
    }

    public void setCiudad(Vector<String> ciudad) {
        this.ciudad = ciudad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Vector<String> getV() {
        return v;
    }

    public void setV(Vector<String> v) {
        this.v = v;
    }

    public Vector<String> getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Vector<String> ubicacion) {
        this.ubicacion = ubicacion;
    }

    public boolean isConsumible() {
        return consumible;
    }

    public void setConsumible(boolean consumible) {
        this.consumible = consumible;
    }

    public void asignarRecurso(Persona p){
        v.add(String.valueOf(p.getId()));
        this.ubicacion.addElement(p.getDomicilio());
        this.ciudad.addElement(p.getCiudad());
    }

    public boolean tienePermisoConsulta(Persona p){ //la idea es q solo pueda consultar un persona que tuvo ese recurso alguna vez
        String id=String.valueOf(p.getId());
        if (p.isAdmin())  return true;
        for (String prop : v) {
            if ( id.equals(prop)){
                return true;
            }
        }
        return false;
    }
}
package Funcionalidad;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Persona implements Serializable {
	/**
	 * 
	 */


	private int id; //identificador unico
	
	private String pass;
	private String user;
	
	private String nombre;
	private String telefono;
	private String domicilio;
	private int ciudad;
	private boolean admin;
    private String email;


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


	public String getUser() {
		return user;
	}

	public int getCiudad() {
		return ciudad;
	}

	public void setCiudad(int ciudad) {
		this.ciudad = ciudad;
	}

	public void setUser(String user) {
		this.user = user;
	}
	

	public boolean isAdmin() {
		return admin;
	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Persona(int id, String u, String pass, String nombre, String telefono, String domicilio, int ciudad, boolean admin,String email) {
		this.id = id;
		this.pass = pass;
		this.nombre = nombre;
		this.telefono = telefono;
		this.domicilio = domicilio;
		this.ciudad=ciudad;
		this.user=u;
		this.admin=admin;
        this.email = email;
	}



}

package com.example.maxi.tpoperativa;

/**
 * Created by Maxi on 17/3/2017.
 */
import android.util.Log;

import com.example.maxi.tpoperativa.Funcionalidad.MySQL;
import com.example.maxi.tpoperativa.Funcionalidad.Persona;
import com.example.maxi.tpoperativa.Funcionalidad.Recurso;

import java.util.Vector;

public class Main {


    public void getCosasBase() {

        /////////Conexion base de datos/////////
        MySQL db = new MySQL();
        try {
            db.MySQLConnection("", "", "operativa");
            Log.d("PROBANDO","ENTRO A CREAR LA CONEXION");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.d("PROBANDO","EXCEPCION DE LA BASE");
            e.printStackTrace();
        }

        Vector<Persona> personas = db.getUsuarios();
        Vector<Recurso> recursos = db.getRecursos();
        for (Persona P : personas) {
            Log.d(P.getUser(), "Persona: ");
        }
//		 ESTO VA SOLO LA PRIMERA VEZ..
//		db.createDB("DBPrueba");
//		db.createTableUsuarios("usuarios");
//		db.createTableRecurso("recursos");
//		// ESTO VA SOLO LA PRIMERA VEZ..
//
//		ACA AGREGAMOS A LOS ADMINISTRADORES..
//		db.insertUsuario(1, "maty", "123", "Matias", "Tangorra", "2494280888", "Yrigoyen 1068", "Tandil", "ONG", "true");

//		db.showUsuarios();
//		db.showRecursos();

//		personas.removeAllElements();
//		recursos.removeAllElements();


        //  new Loggin(personas,recursos);


    }
}
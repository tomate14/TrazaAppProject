package com.example.maxi.tpoperativa;

import android.os.AsyncTask;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import Funcionalidad.MySQL;

public class RegisterActivity extends AppCompatActivity {
    private TextView Nombre;
    private TextView Email;
    private TextView Usuario;
    private TextView Direccion;
    private TextView Telefono;
    private TextView Password;
    private ListView Ciudades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Crear Cuenta");
        setContentView(R.layout.activity_register);

        this.Nombre = (TextView) findViewById(R.id.txt_nombre);
        this.Nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(String.valueOf(Nombre.getText()).equals("Nombre y Apellido"))
                    Nombre.setText("");
            }
        });
        this.Email = (TextView) findViewById(R.id.txt_email);
        this.Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(String.valueOf(Email.getText()).equals("Email"))
                    Email.setText("");
            }
        });
        this.Usuario = (TextView) findViewById(R.id.txt_username);
        this.Usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(String.valueOf(Usuario.getText()).equals("Nombre de usuario"))
                    Usuario.setText("");
            }
        });
        this.Direccion = (TextView) findViewById(R.id.txt_direccion);
        this.Direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(String.valueOf(Direccion.getText()).equals("Direccion"))
                    Direccion.setText("");
            }
        });
        this.Telefono = (TextView) findViewById(R.id.txt_telefono);
        this.Telefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(String.valueOf(Telefono.getText()).equals("Telefono"))
                    Telefono.setText("");
            }
        });

       /* this.Ciudades = (ListView) findViewById(R.id.listCities);
        this.Ciudades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CIUDAD:","HAGO CLICK");
                /*GetCitiesTask citiesTask = new GetCitiesTask();
                citiesTask.execute();
                ResultSet cities = citiesTask.getCities();
                LinkedList ciudadesAux = new LinkedList();
                try {
                    //CARGO AL ARRAY LA LISTA DE LA BASE DE DATOS DE LAS CIUDADES
                    while (cities.next()){
                       ciudadesAux.add(cities.getInt("id"),cities.getString("name"));
                        Log.d("CIUDAD:",ciudadesAux.getLast().toString());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });*/
        if(verificaDatos()){
            //Llamar a insert a la base calculando la posicion donde esta el tipo
        }

    }


    private boolean verificaDatos() {
        if(String.valueOf(this.Nombre.getText()).length() != 0 || String.valueOf(this.Email.getText()).length() != 0
                ||String.valueOf(this.Usuario.getText()).length() != 0 || String.valueOf(this.Direccion.getText()).length() != 0
                ||String.valueOf(this.Telefono.getText()).length() != 0 || String.valueOf(this.Password.getText()).length() != 0  )
            return true;
        return false;
    }

    //TAREA ASINCRONA PARA PODER LEVANTAR TODAS LAS CIUDADES
    //DE LA BASE EN EL RESULTSET
    public class GetCitiesTask extends AsyncTask<Void, Void, Boolean>{
        private ResultSet ciudades;


        @Override
        protected Boolean doInBackground(Void... params) {
            String query = "SELECT NAME,ID" +
                           "FROM LOCATIONS";
            this.ciudades = LoginActivity.Sql.getResultset(query);

            return true;
        }

        public ResultSet getCities(){
            return this.ciudades;
        }
    }
}

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Funcionalidad.MySQL;

public class RegisterActivity extends AppCompatActivity {
    private TextView Nombre;
    private TextView Email;
    private TextView Usuario;
    private TextView Direccion;
    private TextView Telefono;
    private TextView Password;
    private Spinner spinnerCities;
    private Button btnFinalizar;
    private int itemSpinner;

    private List listCities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Crear Cuenta");
        setContentView(R.layout.activity_register);
        this.itemSpinner = 0;

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

        this.Password = (TextView) findViewById(R.id.txt_password);
        this.Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(String.valueOf(Password.getText()).equals("Email"))
                    Password.setText("");
            }
        });

        this.spinnerCities = (Spinner) findViewById(R.id.spinner);
        updateSpinner();

        this.spinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView,View selectedItemView, int position, long id) {
                itemSpinner = spinnerCities.getSelectedItemPosition() + 1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                itemSpinner = 0;
            };
        });

        this.btnFinalizar = (Button) findViewById(R.id.btnFinalizar);
        this.btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verifyDate()){
                    AddUserTask add = new AddUserTask(Usuario.getText(),Password.getText().toString(),
                    Nombre.getText(),
                    Email.getText(),
                    Direccion.getText(),
                    Telefono.getText());
                    add.execute();
                    //Llamar a insert a la base calculando la posicion donde esta el tipo
                }
            }
        });



    }

    private void updateSpinner(){
        Log.d("CIUDAD:","HAGO CLICK");
        GetCitiesTask citiesTask = new GetCitiesTask();
        citiesTask.execute();

    }
    private boolean verifyDate() {
        if(String.valueOf(this.Nombre.getText()).length() != 0 && String.valueOf(this.Email.getText()).length() != 0
                && String.valueOf(this.Usuario.getText()).length() != 0 && String.valueOf(this.Direccion.getText()).length() != 0
                &&String.valueOf(this.Telefono.getText()).length() != 0 && String.valueOf(this.Password.getText()).length() != 0
                && (itemSpinner != 0) )
            return true;
        return false;
    }

    //TAREA ASINCRONA PARA PODER LEVANTAR TODAS LAS CIUDADES
    //DE LA BASE EN EL RESULTSET
    //Tercer parametro lo que retorna doInBackgroud
    public class GetCitiesTask extends AsyncTask<Void, Void, ResultSet>{
        //private ResultSet ciudades;


        @Override
        //protected Boolean doInBackground(Void... params) {
        protected ResultSet doInBackground(Void... params) {

            ResultSet ciudades;
            String query = "SELECT *" +
                           "FROM locations";
            ciudades = LoginActivity.Sql.getResultset(query);

            return ciudades;
        }

        protected void onPostExecute(final ResultSet cities) {
            try {
                int index = 0;
                while (cities.next()){
                    listCities.add(cities.getString("name"));
                    Log.d("Prueba",listCities.get(index).toString());
                    index+=1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ArrayAdapter spinner_adapter;
            spinner_adapter = new ArrayAdapter(RegisterActivity.this, android.R.layout.simple_spinner_item, listCities);
            spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCities.setAdapter(spinner_adapter);
        }

    }

    public class AddUserTask extends AsyncTask<Void, Void, Boolean>{
        private String Usuario;
        private String Password;
        private String Nombre;
        private String Email;
        private String Direccion;
        private String Telefono;


        public AddUserTask(CharSequence Usuario, String Password, CharSequence Nombre, CharSequence Email,
                           CharSequence Direccion, CharSequence Telefono){

            this.Usuario = String.valueOf(Usuario);
            this.Password = Password;
            this.Nombre = String.valueOf(Nombre);
            this.Email = String.valueOf(Email);
            this.Direccion =String.valueOf(Direccion);
            this.Telefono = String.valueOf(Telefono);
        };
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                String query = "SELECT * FROM USERS " +
                               "WHERE email='"+Email+"'";
                ResultSet resultSet = LoginActivity.Sql.getResultset(query);

                if(resultSet.next()) {
                    return false;
                }
                query = "INSERT INTO USERS(id,role_id,username,password,name,email,address,phone,location_id) " +
                        "VALUES(`id`,1," +
                        this.Usuario+","+
                        this.Password+"," +
                        this.Nombre+"," +
                        this.Email+"," +
                        this.Direccion+"," +
                        this.Telefono+"," +
                        String.valueOf(itemSpinner)+")";
                LoginActivity.Sql.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return true;
        }
        protected void onPostExecute(final Boolean sucess) {
          if (sucess) {
              //GENERAR LAS COORDENADAS

              finish();
            } else {

            }

        }
    }
}

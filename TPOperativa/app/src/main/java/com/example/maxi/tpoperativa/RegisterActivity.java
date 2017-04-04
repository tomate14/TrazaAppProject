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
import android.widget.Toast;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Funcionalidad.MySQL;
import TareasAsincronas.AddUserTask;
import TareasAsincronas.GetCitiesTask;

public class RegisterActivity extends AppCompatActivity {
    private TextView Nombre;
    private TextView Email;
    private TextView Usuario;
    private TextView Direccion;
    private TextView Telefono;
    private TextView Password;
    private TextView Website;
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
                if(String.valueOf(Password.getText()).equals("Password"))
                    Password.setText("");
            }
        });

        this.Website = (TextView) findViewById(R.id.txt_Website);
        this.Website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(String.valueOf(Website.getText()).equals("Website"))
                    Website.setText("");
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
                                                      Nombre.getText(),Email.getText(),Direccion.getText(),Telefono.getText(),
                                                      Website.getText(),itemSpinner);
                                                      add.execute();
                    //Llamar a insert a la base calculando la posicion donde esta el tipo
                }
                finish();
            }
        });



    }

    private void updateSpinner(){
        Log.d("CIUDAD:","HAGO CLICK");
        GetCitiesTask citiesTask = new GetCitiesTask(this.spinnerCities,RegisterActivity.this);
        citiesTask.execute();
        spinnerCities = citiesTask.getSpinnerCities();

    }
    private boolean verifyDate() {
        if(String.valueOf(this.Nombre.getText()).length() != 0 && String.valueOf(this.Email.getText()).length() != 0
                && String.valueOf(this.Usuario.getText()).length() != 0 && String.valueOf(this.Direccion.getText()).length() != 0
                &&String.valueOf(this.Telefono.getText()).length() != 0 && String.valueOf(this.Password.getText()).length() != 0
                && (itemSpinner != 0) )
            return true;
        return false;
    }


}

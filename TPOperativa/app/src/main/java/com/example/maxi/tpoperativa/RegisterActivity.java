package com.example.maxi.tpoperativa;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.support.v4.app.ActivityCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
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

import java.io.IOException;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import Funcionalidad.MySQL;
import Funcionalidad.Persona;
import Localizacion.Localizacion;
import TareasAsincronas.AddUserTask;
import TareasAsincronas.SpinnerTask;

public class RegisterActivity extends AppCompatActivity{
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
    private Localizacion Local;
    private RegisterActivity actividad;

    private List listCities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Crear Cuenta");
        setContentView(R.layout.activity_register);
        this.actividad = this;

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
                    createUser();
            }
        });



    }
    private void createUser(){
        if(verifyDate()){
            Persona newUser = new Persona();
            locationStart(newUser);
            AddUserTask add = new AddUserTask(Usuario.getText(),Password.getText().toString(),
                    Nombre.getText(),Email.getText(),Direccion.getText(),Telefono.getText(),
                    Website.getText(),itemSpinner,newUser.getLatitude(),newUser.getLongitude());
            add.execute();
            try {
                if(add.get()){
                    Log.d("CORRECTO","AGREGADO");
                    finish();
                }
                else{
                    Toast text = Toast.makeText(actividad,"Hay datos registrados en la base",Toast.LENGTH_SHORT);
                    text.setGravity(Gravity.CENTER, 0, 0);
                    text.show();
                    Log.d("FALSO","NO AGREGADO");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }else {
            Toast text = Toast.makeText(actividad,"Faltan datos",Toast.LENGTH_SHORT);
            text.setGravity(Gravity.CENTER, 0, 0);
            text.show();
        }
    }
    private void updateSpinner(){
        Log.d("CIUDAD:","HAGO CLICK");
        SpinnerTask citiesTask = new SpinnerTask(this.spinnerCities,RegisterActivity.this);

        String query = "SELECT *" +
                "FROM locations";

        citiesTask.execute(query);
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

    private void locationStart(Persona user){

        Log.d("LOCALIZATIONSTART","EMPEZO");
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        this.Local = new Localizacion(location);
        this.Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            Log.d("SALGO","TERMINO");

            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
        seeLocation(this.Local.getLocation(),user);
        Log.d("","Localizacion agregada");

    }



    public void seeLocation(Location loc,Persona user) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> list = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            if (!list.isEmpty()) {
                Address DirCalle = list.get(0);
                user.setDireccion(DirCalle.getAddressLine(0));
                Log.d("","Mi direccion es: \n"
                        + DirCalle.getAddressLine(0));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

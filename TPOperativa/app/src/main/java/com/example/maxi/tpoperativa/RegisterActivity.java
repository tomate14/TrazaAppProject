package com.example.maxi.tpoperativa;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Funcionalidad.Persona;
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

        this.spinnerCities = (Spinner) findViewById(R.id.spinner_resource);
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
            AddUserTask add = new AddUserTask(Usuario.getText(),Password.getText().toString(),
                    Nombre.getText(),Email.getText(),Direccion.getText(),Telefono.getText(),
                    Website.getText(),itemSpinner);
            add.execute();
            finish();

        }else {
            Toast text = Toast.makeText(actividad,"Faltan datos",Toast.LENGTH_SHORT);
            text.setGravity(Gravity.CENTER, 0, 0);
            text.show();
        }
    }
    private void updateSpinner(){
        Log.d("CIUDAD:","HAGO CLICK");
        SpinnerTask citiesTask = new SpinnerTask(this.spinnerCities,RegisterActivity.this);

        String query = "SELECT * " +
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

    public class AddUserTask extends AsyncTask<Void, Void, Boolean> implements LocationListener{
        private Boolean result;
        //nuevo
        private Persona user;
        private LocationManager lm;

        public Boolean getResult() {
            return result;
        }

        public void setResult(Boolean result) {
            this.result = result;
        }


        public AddUserTask(CharSequence Usuario, String Password, CharSequence Nombre, CharSequence Email,
                           CharSequence Direccion, CharSequence Telefono, CharSequence Website,
                           int item){
            this.user = new Persona();
            this.user.setUser(String.valueOf(Usuario));
            this.user.setPass(Password);
            this.user.setNombre(String.valueOf(Nombre));
            this.user.setEmail(String.valueOf(Email));
            this.user.setDireccion(String.valueOf(Direccion));
            this.user.setTelefono(String.valueOf(Telefono));
            this.user.setCiudad(item);



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
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return true;
        }
        protected void onPostExecute(final Boolean sucess) {
            super.onPostExecute(sucess);
            Log.d("Resultado dentro",String.valueOf(result));
            if (sucess) {
                try {
                    if (ContextCompat.checkSelfPermission(RegisterActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(RegisterActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        lm = (LocationManager) getSystemService(RegisterActivity.LOCATION_SERVICE);

                        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                    }
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                //GENERAR LAS COORDENADAS
            } else {
                    //Toast del email ya existe

            }


        }

        @Override
        public void onLocationChanged(Location location) {
            lm.removeUpdates(this);

            double longitud = 0;
            double latitud = 0;

            if (location != null) {
                longitud = location.getLongitude();
                latitud = location.getLatitude();
                this.user.setLatitude(latitud);
                this.user.setLongitude(longitud);
            }
            Log.d("Persona",this.user.toString());
            TaskInsert task = new TaskInsert(this.user);
            task.execute();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
    public class TaskInsert extends AsyncTask<Void, Void, Integer> {

        private Persona user;

        public TaskInsert(Persona user){
            this.user = user;
        }

        protected Integer doInBackground(Void... params) {
            Log.d("Persona antes del insert",this.user.toString());
            String query = "INSERT INTO USERS(id,role_id,username,password,name,email,"+
                    "address,phone,location_id,website,latitude, longitude)" +
                    "          VALUES(`id`,2,'"+this.user.getUser()+"','"+this.user.getPass()+"'," +
                    "'"+this.user.getNombre()+"','"+this.user.getEmail()+"'," +
                    "'"+this.user.getDireccion()+"','"+this.user.getTelefono()+"'," +
                    "'"+this.user.getCiudad()+"','"+Website+"',"+
                    " "+this.user.getLatitude()+","+" "+this.user.getLongitude()+")";

            Log.d("Consulta",query);
            LoginActivity.Sql.executeQuery(query);
            return LoginActivity.Sql.executeQuery(query);
        }
        protected void onPostExecute(Integer insert) {
            super.onPostExecute(insert);
            if (insert != -1) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Registrado Correctamente.", Toast.LENGTH_SHORT);
                toast.show();
            }
            RegisterActivity.this.finish();
        }
    }


}

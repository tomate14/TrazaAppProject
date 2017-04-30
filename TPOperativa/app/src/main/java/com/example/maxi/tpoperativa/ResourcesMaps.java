package com.example.maxi.tpoperativa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import Funcionalidad.Persona;
import TareasAsincronas.SpinnerTask;

public class ResourcesMaps extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Persona user;
    private Spinner spinnerCities;
    private Marker tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        this.user = (Persona)getIntent().getExtras().getSerializable("Persona");

        this.spinnerCities = (Spinner) findViewById(R.id.spinner);
        updateSpinner();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    private void updateSpinner(){
        Log.d("CIUDAD:","HAGO CLICK");
        SpinnerTask citiesTask = new SpinnerTask(this.spinnerCities,ResourcesMaps.this);
        String query = "SELECT * " +
                       "FROM resources " +
                       "ORDER BY name";
        citiesTask.execute(query);
        spinnerCities = citiesTask.getSpinnerCities();

    }
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Esperar que termine el Toast para mostrar el mapa
        Toast text = Toast.makeText(this,"Cargando mapa",Toast.LENGTH_SHORT);
        text.setGravity(Gravity.CENTER, 0, 0);
        text.show();


        addPointIntoMap(this.user.getLatitude(), this.user.getLongitude(), this.user.getDireccion(),5);

    }

    private void addPointIntoMap(double latitude, double longitude,String text, int number){
        LatLng point = new LatLng(latitude, longitude);

        MarkerOptions tag = new MarkerOptions().position(point).title(text).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMap.addMarker(tag);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
    }
}

package com.example.maxi.tpoperativa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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

        //Tomar el id de ese movimiento y pasarlo como ultimo parametro
        addPointIntoMap(this.user.getLatitude(), this.user.getLongitude(), this.user.getDireccion(),5);

    }

    private void addPointIntoMap(double latitude, double longitude,String text, int number){
        LatLng point = new LatLng(latitude, longitude);
        String textNumber = String.valueOf(number);
        int numberIcon = this.getLayoutNumber();

        //recorro la lista que retorne el resultset del recurso y miro, si ese id es admin


        MarkerOptions tag = new MarkerOptions().position(point).title(text) .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(textNumber,numberIcon)));
        mMap.addMarker(tag);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
    }

    private int getLayoutNumber(){
        if(!this.user.isAdmin()){
            return R.layout.icon_maps;
        }else{
            return R.layout.icon_maps_admin;
        }
    }
    private Bitmap getMarkerBitmapFromView(String text, int numberIcon) {

        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(numberIcon, null);
        TextView markerImageView = (TextView) customMarkerView.findViewById(R.id.profile_icon);
        markerImageView.setText(text);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }
}


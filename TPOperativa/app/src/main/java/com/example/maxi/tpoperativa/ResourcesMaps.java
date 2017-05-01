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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

import Funcionalidad.Persona;
import Funcionalidad.PointInfo;
import TareasAsincronas.ResultSetTask;
import TareasAsincronas.SpinnerTask;

public class ResourcesMaps extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Persona user;
    private Spinner spinnerCities;
    private String textSpinner;

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
        this.textSpinner = "";
        this.spinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView,View selectedItemView, int position, long id) {
                String text = (String) spinnerCities.getSelectedItem();
                Log.d("SPINNER","SELECCIONO ITEM ="+textSpinner+", TEXT="+text);
                if(textSpinner != text){
                    textSpinner = text;
                    mMap.clear();
                    onMapReady(mMap);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textSpinner = "";
            };
        });
    }

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
        Log.d("MAPA:","ENTRO AL MAPA");
        mMap = googleMap;

        Toast text = Toast.makeText(this,"Cargando mapa",Toast.LENGTH_SHORT);
        text.setGravity(Gravity.CENTER, 0, 0);
        text.show();
        Log.d("STRINGS:","CARGO MENSAJE Y QUERY");
        String mensaje = "No hay datos para mostrar";

        String query = "SELECT RR.ID, RR.ID_RESOURCE, RR.ID_ORIGEN, O.NAME as ORI_NAME, RR.ID_DESTINO, D.NAME AS DES_NAME, D.ROLE_ID AS DES_ROLE,\n" +
                "       D.ADDRESS AS DES_ADDRESS, RR.LATITUDE, RR.LONGITUDE, DATE_FORMAT(RR.DATE,'%d/%m/%Y') AS date\n" +
                " FROM   RESOURCE_ROUTE AS RR, USERS AS O, USERS AS D, RESOURCES AS R\n" +
                " WHERE R.ID = RR.ID_RESOURCE " +
                "  AND  R.NAME ='"+textSpinner+"'\n" +
                "  AND  RR.ID_DESTINO = D.ID\n" +
                "  AND  RR.ID_ORIGEN  = O.ID ";

        if(textSpinner != ""){
            Log.d("CARGAR MOVIMIENTOS RECURSO",query);

            ResultSetTask task = new ResultSetTask(this,mensaje);
            task.execute(query);
            int count = 1;
            try {
                ResultSet result = task.get();

                while(result.next()){
                    PointInfo point = generarPoint(result);
                    addPointIntoMap(point,count);
                    count++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    private PointInfo generarPoint(ResultSet result) throws SQLException {
        int id              = result.getInt("id");
        String id_resource  = result.getString("id_resource");
        String id_origen    = result.getString("id_origen");
        String origen_name  = result.getString("ORI_NAME");
        String id_destino   = result.getString("id_destino");
        String destino_name = result.getString("DES_NAME");
        int destino_role    = result.getInt("DES_ROLE");
        double latitude     = result.getDouble("latitude");
        double longitude    = result.getDouble("longitude");
        String date         = result.getString("date");
        String dest_address      = result.getString("DES_ADDRESS");
        return new PointInfo(id,id_resource,id_origen,origen_name,id_destino,destino_name,destino_role,dest_address,latitude,longitude,date);

    }

    //Crea y Agrega punto al mapa
    private void addPointIntoMap(PointInfo pointer, int count){

        LatLng point = new LatLng(pointer.getLatitude(), pointer.getLongitude());

        String textPrincipal  = "Movimiento Nro: "+pointer.getId();
        String textSnipper = "Direccion Destino: "+pointer.getDestino_address()+"\n"+
                             "Origen:         "+pointer.getOrigen_name()+"\n"+
                             "Destino:        "+pointer.getDestino_name()+"\n"+
                             "Fecha:          "+pointer.getDate();

        String textNumber = String.valueOf(count);

        int numberIcon = this.getLayoutNumber(pointer.getDestino_role());

        //recorro la lista que retorne el resultset del recurso y miro, si ese id es admin

        MarkerOptions tag = new MarkerOptions().position(point).title(textPrincipal)
                .snippet(textSnipper).icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(textNumber,numberIcon)));
        mMap.addMarker(tag);
        addWindowsInfo();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
    }

    private void addWindowsInfo(){

    }
    //Selecciono el color del marcador a utilizar
    private int getLayoutNumber(int destino_role){
        if(destino_role != 1){
            return R.layout.icon_maps;
        }else{
            return R.layout.icon_maps_admin;
        }
    }

    //Genero el mapa de bits para dibujar el icono
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


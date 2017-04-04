package TareasAsincronas;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.maxi.tpoperativa.LoginActivity;
import com.example.maxi.tpoperativa.RegisterActivity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxi on 3/4/2017.
 */

public class GetCitiesTask extends AsyncTask<Void, Void, ResultSet> {
    private ResultSet ciudades;
    private List listCities = new ArrayList<>();
    private Spinner spinnerCities;

    public GetCitiesTask(Spinner spinner){
        this.spinnerCities = spinner;
    }

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
       /* ArrayAdapter spinner_adapter;
        spinner_adapter = new ArrayAdapter(RegisterActivity, android.R.layout.simple_spinner_item, listCities);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCities.setAdapter(spinner_adapter);*/
    }



}
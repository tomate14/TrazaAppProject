package TareasAsincronas;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
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

public class SpinnerTask extends AsyncTask<String, Void, ResultSet> {
    private ResultSet ciudades;
    private List listCities = new ArrayList<>();
    private Spinner spinnerCities;
    private AppCompatActivity register;

    public SpinnerTask(Spinner spinner, AppCompatActivity activity){
        this.register = activity;
        this.spinnerCities = spinner;
    }

    @Override
    protected ResultSet doInBackground(String... params) {
        Log.d("PARAMETRO",params[0]);
        this.ciudades = LoginActivity.Sql.getResultset(params[0]);
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
        spinner_adapter = new ArrayAdapter(this.register, android.R.layout.simple_spinner_item, listCities);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCities.setAdapter(spinner_adapter);
    }

    public Spinner getSpinnerCities() {
        return spinnerCities;
    }

}
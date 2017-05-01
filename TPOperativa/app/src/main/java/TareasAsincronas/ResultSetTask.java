package TareasAsincronas;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.maxi.tpoperativa.LoginActivity;
import com.example.maxi.tpoperativa.ResourcesMaps;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Maxi on 30/4/2017.
 */

public class ResultSetTask  extends AsyncTask<String, Void, ResultSet> {

    private ResourcesMaps activity;
    private String mensaje;

    public ResultSetTask(ResourcesMaps activity, String mensaje){
        this.activity = activity;
        this.mensaje = mensaje;
    }

    @Override
    protected ResultSet doInBackground(String... params) {
        Log.d("Query",params[0]);
        return LoginActivity.Sql.getResultset(params[0]);
    }

    @Override
    protected void onPostExecute(ResultSet resultSet) {
        super.onPostExecute(resultSet);
        try {
            if(!resultSet.next()){
                Toast toast = Toast.makeText(this.activity,
                        this.mensaje, Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

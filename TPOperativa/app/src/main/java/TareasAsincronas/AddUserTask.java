package TareasAsincronas;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.maxi.tpoperativa.LoginActivity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Maxi on 4/4/2017.
 */

public class AddUserTask extends AsyncTask<Void, Void, Boolean> {
    private String Usuario;
    private String Password;
    private String Nombre;
    private String Email;
    private String Direccion;
    private String Telefono;
    private String Website;
    private int itemSpinner;


    public AddUserTask(CharSequence Usuario, String Password, CharSequence Nombre, CharSequence Email,
                       CharSequence Direccion, CharSequence Telefono, CharSequence Website,
                       int item){

        this.Usuario = String.valueOf(Usuario);
        this.Password = Password;
        this.Nombre = String.valueOf(Nombre);
        this.Email = String.valueOf(Email);
        this.Direccion =String.valueOf(Direccion);
        this.Telefono = String.valueOf(Telefono);
        this.Website = String.valueOf(Website);
        this.itemSpinner = item;
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
            query = "INSERT INTO USERS(id,role_id,username,password,name,email,"+
                    "address,phone,location_id,website)" +
                    "          VALUES(`id`,2,'"+this.Usuario+"','"+this.Password+"'," +
                    "'"+this.Nombre+"','"+this.Email+"'," +
                    "'"+this.Direccion+"','"+this.Telefono+"'," +
                    "'"+String.valueOf(itemSpinner)+"','"+this.Website+"')";
            Log.d("Consulta",query);
            LoginActivity.Sql.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
    protected void onPostExecute(final Boolean sucess) {
        if (sucess) {
            try {
                AddUserTask.this.finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            //GENERAR LAS COORDENADAS
        } else {

        }

    }
}
package com.wgetdc.crudtarde;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wgetdc.crudtarde.Modelo.BDSQLiteOpenHelper;

public class MainActivity extends AppCompatActivity {

    EditText edtCodigo, edtNombre, edtDisco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtCodigo = (EditText) findViewById(R.id.edtCodigo);
        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtDisco = (EditText) findViewById(R.id.edtDisco);
    }

    public void pantallaBuscar(View view){
        Intent buscar = new Intent(this, BuscarActivity.class);
        startActivity(buscar);
    }

    public void ingresar(View view){
        BDSQLiteOpenHelper gestion =
                new BDSQLiteOpenHelper(this,"discografia",null,1);
        SQLiteDatabase bd = gestion.getWritableDatabase();

        String codigo = edtCodigo.getText().toString();
        String nombre = edtNombre.getText().toString();
        String disco = edtDisco.getText().toString();

        if (!codigo.isEmpty() && !nombre.isEmpty() && !disco.isEmpty()){
            ContentValues row = new ContentValues();
            row.put("codigo",codigo);
            row.put("nombre",nombre);
            row.put("disco",disco);

            bd.insert("bandas",null,row);
            bd.close();

            edtCodigo.setText("");
            edtNombre.setText("");
            edtDisco.setText("");

            Toast.makeText(this, "El disco ha sido ingresado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Por favor complete los campos", Toast.LENGTH_SHORT).show();
        }
    }
}

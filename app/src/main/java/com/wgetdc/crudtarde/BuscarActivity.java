package com.wgetdc.crudtarde;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wgetdc.crudtarde.Modelo.BDSQLiteOpenHelper;

public class BuscarActivity extends AppCompatActivity {

    EditText edtCodigo, edtNombre, edtDisco;
    TextView txtCodigo, txtNombre, txtDisco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        edtCodigo = (EditText) findViewById(R.id.edtCodigo);
        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtDisco = (EditText) findViewById(R.id.edtDisco);
        txtCodigo = (TextView) findViewById(R.id.txtCodigo);
        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtDisco = (TextView) findViewById(R.id.txtDisco);
    }

    public void buscar(View view){
        BDSQLiteOpenHelper gestion =
                new BDSQLiteOpenHelper(this,"discografia", null, 1);
        SQLiteDatabase bd = gestion.getWritableDatabase();

        String codigo = edtCodigo.getText().toString();
        if(!codigo.isEmpty()){
            Cursor row = bd.rawQuery("select codigo, nombre, disco " +
                    "from bandas where codigo="+ codigo,null);
            if (row.moveToFirst()){
                txtCodigo.setText(row.getString(0));
                txtNombre.setText(row.getString(1));
                txtDisco.setText(row.getString(2));
                bd.close();
            } else{
                Toast.makeText(this, "La banda no existe", Toast.LENGTH_SHORT).show();
                bd.close();
            }
        } else{
            Toast.makeText(this, "Por favor ingrese codigo", Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminar(View view){
        BDSQLiteOpenHelper gestion = new BDSQLiteOpenHelper(this,"discografia",null,1);
        SQLiteDatabase bd = gestion.getWritableDatabase();

        String codigo = edtCodigo.getText().toString();

        if (!codigo.isEmpty()){
           int afectadas = bd.delete("bandas", "codigo=" + codigo, null);
            bd.close();
            edtCodigo.setText("");
            txtCodigo.setText("Codigo:");
            txtNombre.setText("Nombre:");
            txtDisco.setText("Disco:");

            if (afectadas==1){
                Toast.makeText(this, "La banda se elimino", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "la banda no existe", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Para eliminar debe ingresar un codigo", Toast.LENGTH_SHORT).show();
        }

    }

    public void modificar(View view){
        BDSQLiteOpenHelper gestion = new BDSQLiteOpenHelper(this,"discografia",null,1);
        SQLiteDatabase bd = gestion.getWritableDatabase();

        String codigo = edtCodigo.getText().toString();
        String nombre = edtNombre.getText().toString();
        String disco = edtDisco.getText().toString();

        if (!codigo.isEmpty() && !nombre.isEmpty() && !disco.isEmpty()){
            ContentValues actualizar = new ContentValues();
            actualizar.put("codigo", codigo);
            actualizar.put("nombre",nombre);
            actualizar.put("disco",disco);

            int afecto = bd.update
                    ("bandas", actualizar, "codigo=" + codigo, null);
            bd.close();

            if (afecto == 1){
                Toast.makeText(this, "La banda se actuaalizo", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "La banda no existe", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Por favor complete los campos", Toast.LENGTH_SHORT).show();
        }
    }
}

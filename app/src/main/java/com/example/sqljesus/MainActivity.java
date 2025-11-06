package com.example.sqljesus;
import android.database.Cursor;
import android.os.Bundle;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    EditText txtRFC, txtNombre, txtCorreo, txtTelefono, txtDireccion, txtCiudad;
    TextView tvRFC, tvNombre, tvCorreo, tvTelefono, tvDireccion, tvCiudad;
    public static final String N_BASE = "BDClientes";
    public static final String N_TABLA = "cliente";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtRFC = findViewById(R.id.txtRFC);
        txtNombre = findViewById(R.id.txtNombre);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtCiudad = findViewById(R.id.txtCiudad);

        tvRFC = findViewById(R.id.tvRFC);
        tvNombre = findViewById(R.id.tvNombre);
        tvCorreo = findViewById(R.id.tvCorreo);
        tvTelefono = findViewById(R.id.tvTelefono);
        tvDireccion = findViewById(R.id.tvDireccion);
        tvCiudad = findViewById(R.id.tvCiudad);

        inicializarBD();
    }

    private void inicializarBD() {
        UtilidadesSQL sql = new UtilidadesSQL(this, N_BASE, null, 1);
        SQLiteDatabase db = sql.getWritableDatabase();


        // 🔹 Agregar registro inicial solo si la tabla está vacía
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM " + N_TABLA, null);
        cur.moveToFirst();
        int total = cur.getInt(0);
        cur.close();

        if (total == 0) {
            ContentValues nvoRegistro = new ContentValues();
            nvoRegistro.put("rfc", "JUAP890101MX0");
            nvoRegistro.put("nombre", "Juan Pérez");
            nvoRegistro.put("correo", "juanperez@example.com");
            nvoRegistro.put("telefono", "5544332211");
            nvoRegistro.put("direccion", "Av. Reforma 123");
            nvoRegistro.put("ciudad", "CDMX");
            db.insert(N_TABLA, null, nvoRegistro);
            Toast.makeText(this, "Registro inicial agregado", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public void agregar(View v) {
        UtilidadesSQL sql = new UtilidadesSQL(this, N_BASE, null, 1);
        SQLiteDatabase db = sql.getWritableDatabase();

        ContentValues nuevo = new ContentValues();
        nuevo.put("rfc", txtRFC.getText().toString());
        nuevo.put("nombre", txtNombre.getText().toString());
        nuevo.put("correo", txtCorreo.getText().toString());
        nuevo.put("telefono", txtTelefono.getText().toString());
        nuevo.put("direccion", txtDireccion.getText().toString());
        nuevo.put("ciudad", txtCiudad.getText().toString());

        db.insert(N_TABLA, null, nuevo);
        db.close();
        Toast.makeText(this, "Insertado correctamente", Toast.LENGTH_SHORT).show();
    }

    public void buscar(View v) {
        UtilidadesSQL sql = new UtilidadesSQL(this, N_BASE, null, 1);
        SQLiteDatabase db = sql.getReadableDatabase();

        String[] args = {txtRFC.getText().toString()};
        Cursor c = db.query(N_TABLA,
                new String[]{"rfc", "nombre", "correo", "telefono", "direccion", "ciudad"},
                "rfc=?", args, null, null, null);

        if (c.moveToFirst()) {
            tvRFC.setText(c.getString(0));
            tvNombre.setText(c.getString(1));
            tvCorreo.setText(c.getString(2));
            tvTelefono.setText(c.getString(3));
            tvDireccion.setText(c.getString(4));
            tvCiudad.setText(c.getString(5));
            Toast.makeText(this, "Registro encontrado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No encontrado", Toast.LENGTH_SHORT).show();
        }

        c.close();
        db.close();
    }

    public void actualizar(View v) {
        UtilidadesSQL sql = new UtilidadesSQL(this, N_BASE, null, 1);
        SQLiteDatabase db = sql.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nombre", txtNombre.getText().toString());
        valores.put("correo", txtCorreo.getText().toString());
        valores.put("telefono", txtTelefono.getText().toString());
        valores.put("direccion", txtDireccion.getText().toString());
        valores.put("ciudad", txtCiudad.getText().toString());

        String[] args = {txtRFC.getText().toString()};
        db.update(N_TABLA, valores, "rfc=?", args);
        db.close();

        Toast.makeText(this, "Actualizado correctamente", Toast.LENGTH_SHORT).show();
    }
    public void eliminar(View v) {
        UtilidadesSQL sql = new UtilidadesSQL(this, N_BASE, null, 1);
        SQLiteDatabase db = sql.getWritableDatabase();
        String[] args = {txtRFC.getText().toString()};
        db.delete(N_TABLA, "rfc=?", args);
        db.close();
        Toast.makeText(this, "Eliminado correctamente", Toast.LENGTH_SHORT).show();
    }
}

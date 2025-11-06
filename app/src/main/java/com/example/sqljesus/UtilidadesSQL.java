package com.example.sqljesus;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
public class UtilidadesSQL extends SQLiteOpenHelper {

    public UtilidadesSQL(@Nullable Context context, @Nullable String name,
                         @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS cliente(" +
                "rfc TEXT PRIMARY KEY, " +
                "nombre TEXT, " +
                "correo TEXT, " +
                "telefono TEXT, " +
                "direccion TEXT, " +
                "ciudad TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS cliente");
        onCreate(db);
    }
}

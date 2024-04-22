package com.ruiz.medialertugb_cru;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Usuarios.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "Usuarios";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRES = "nombres";
    public static final String COLUMN_APELLIDOS = "apellidos";
    public static final String COLUMN_EDAD = "edad";
    public static final String COLUMN_GENERO = "genero";
    public static final String COLUMN_CARRERA = "carrera";
    public static final String COLUMN_CORREO = "correo";
    public static final String COLUMN_CONTRASENA = "contrasena";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla de usuarios
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRES + " TEXT, " +
                COLUMN_APELLIDOS + " TEXT, " +
                COLUMN_EDAD + " INTEGER, " +
                COLUMN_GENERO + " TEXT, " +
                COLUMN_CARRERA + " TEXT, " +
                COLUMN_CORREO + " TEXT UNIQUE, " +
                COLUMN_CONTRASENA + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Manejar la actualización de la base de datos si es necesario
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insertar un nuevo usuario en la base de datos
    public long insertarUsuario(String nombres, String apellidos, int edad, String genero, String carrera, String correo, String contrasena) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRES, nombres);
        values.put(COLUMN_APELLIDOS, apellidos);
        values.put(COLUMN_EDAD, edad);
        values.put(COLUMN_GENERO, genero);
        values.put(COLUMN_CARRERA, carrera);
        values.put(COLUMN_CORREO, correo);
        values.put(COLUMN_CONTRASENA, contrasena);

        try {
            long resultado = db.insertOrThrow(TABLE_NAME, null, values);
            db.close();
            return resultado;
        } catch (SQLiteConstraintException e) {
            db.close();
            return -1;
        }
    }

    // Método para autenticar al usuario
    public boolean autenticarUsuario(String correo, String contrasena) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_CORREO + " = ? AND " + COLUMN_CONTRASENA + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{correo, contrasena});
        boolean autenticado = cursor.moveToFirst();
        cursor.close();
        return autenticado;
    }
}

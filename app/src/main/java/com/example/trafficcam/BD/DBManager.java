package com.example.trafficcam.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.trafficcam.Objects.Incidencia;
import com.example.trafficcam.Objects.Camara;


import java.util.ArrayList;
import java.util.List;

public class DBManager  {

    private DBHelper dbHelper;
    private SQLiteDatabase database;




    public DBManager(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }



        dbHelper = new DBHelper(context);
    }



    public void open() {
        try {
            if (database == null || !database.isOpen()) {
                database = dbHelper.getWritableDatabase();
            }
        } catch (SQLException e) {
            // Maneja la excepción, tal vez mostrando un mensaje al usuario
            e.printStackTrace();

        }
    }

    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }














    // Insertar una cámara en la tabla de favoritos
    public void anadirCamaraFavoritos(Camara camara) {
        open();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_CAMARA_ID, camara.getId());  // ID único de la API
        values.put(DBHelper.COLUMN_CAMARA_NAME, camara.getCameraName());
        values.put(DBHelper.COLUMN_CAMARA_ADDRESS, camara.getAddress());
        values.put(DBHelper.COLUMN_CAMARA_KILOMETRO, camara.getKilometer());
        values.put(DBHelper.COLUMN_CAMARA_ROAD, camara.getRoad());

 database.insertWithOnConflict(DBHelper.TABLE_CAMARAS, null, values, SQLiteDatabase.CONFLICT_IGNORE);


    }

    public void anadirIncidenciasFavoritos(Incidencia incidencia) {
        open();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_INCIDENT_ID, incidencia.getId());  // ID único de la API
        values.put(DBHelper.COLUMN_INCIDENT_Type, incidencia.getIncidenceType());
        values.put(DBHelper.COLUMN_INCIDENT_CAUSE, incidencia.getCause());
        values.put(DBHelper.COLUMN_INCIDENT_ADDRESS, incidencia.getDirection());
        values.put(DBHelper.COLUMN_INCIDENT_CITY, incidencia.getCityTown());

        database.insertWithOnConflict(DBHelper.TABLE_INCIDENCIAS, null, values, SQLiteDatabase.CONFLICT_IGNORE);


    }

    // Eliminar una cámara de favoritos
    public void eliminarCamaraFavoritos(Camara camara) {
        open();
        database.delete(DBHelper.TABLE_CAMARAS, DBHelper.COLUMN_CAMARA_ID + " = ?",
                new String[]{String.valueOf(camara.getId())});
    }

    public void eliminarIncidenciaFavoritos(Incidencia incidencia) {
        open();
        database.delete(DBHelper.TABLE_INCIDENCIAS, DBHelper.COLUMN_INCIDENT_ID + " = ?",
                new String[]{String.valueOf(incidencia.getId())});
    }










    // Obtener todas las cámaras favoritas
    public List<Camara> getCamarasFavoritas() {
        open();
        List<Camara> lista = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.TABLE_CAMARAS, null, null, null, null, null, null);

        Log.d("DBManager", "Número de cámaras encontradas: " + cursor.getCount());  // Verifica cuántas cámaras se encuentran

        if (cursor.moveToFirst()) {
            do {
                Camara camara = new Camara(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CAMARA_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CAMARA_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CAMARA_ADDRESS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CAMARA_KILOMETRO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CAMARA_ROAD))


                );
                lista.add(camara);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return lista;
    }

    //Obtener todas las Incidencias

    // Obtener todas las incidencias
    public List<Incidencia> getIncidencasFavoritos() {
        open();
        List<Incidencia> lista = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.TABLE_INCIDENCIAS, null, null, null, null, null, null);

        Log.d("DBManager", "Número de cámaras encontradas: " + cursor.getCount());  // Verifica cuántas cámaras se encuentran

        if (cursor.moveToFirst()) {
            do {
                Incidencia incidencia = new Incidencia(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_INCIDENT_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_INCIDENT_Type)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_INCIDENT_CAUSE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_INCIDENT_ADDRESS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_INCIDENT_CITY))


                );
                lista.add(incidencia);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return lista;
    }

    public boolean existeCamara(int  id) {
        open();
        String query = "SELECT COUNT(*) FROM " + DBHelper.TABLE_CAMARAS + " WHERE " + DBHelper.COLUMN_CAMARA_ID + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(id)});

        boolean existe = false;
        if (cursor.moveToFirst()) {
            existe = cursor.getInt(0) > 0;
            existe = true;
        }
        cursor.close();
        return existe;
    }

    public boolean existeIncidence(int  id) {
        open();
        String query = "SELECT COUNT(*) FROM " + DBHelper.TABLE_INCIDENCIAS + " WHERE " + DBHelper.COLUMN_INCIDENT_ID + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(id)});

        boolean existe = false;
        if (cursor.moveToFirst()) {
            existe = cursor.getInt(0) > 0;
            existe = true;
        }
        cursor.close();
        return existe;
    }
}

package com.example.trafficcam.BD;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "TraffiCCam";




    // Tabla Cámaras
    public static final String TABLE_CAMARAS = "Camaras";
    public static final String COLUMN_CAMARA_ID = "id";
    public static final String COLUMN_CAMARA_NAME = "camaraName";
    public static final String COLUMN_CAMARA_ADDRESS = "address";
    public static final String COLUMN_CAMARA_ROAD = "road";
    public static final String COLUMN_CAMARA_KILOMETRO = "kilometro";

    public static  final String COLUMN_CAMARA_BOOLEAN = "Favorito";


    // Tabla Incidencias
    public static final String TABLE_INCIDENCIAS = "Incidencias";
    public static final String COLUMN_INCIDENT_ID = "id";
    public static final String COLUMN_INCIDENT_Type = "Type";
    public static final String COLUMN_INCIDENT_ADDRESS = "address";
    public static final String COLUMN_INCIDENT_CAUSE = "cause";
    public static final String COLUMN_INCIDENT_CITY = "CityTown";





    // Sentencias SQL para crear las tablas
    private static final String CREATE_TABLE_CAMARAS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CAMARAS + " (" +
                    COLUMN_CAMARA_ID + " INTEGER PRIMARY KEY , " +
                    COLUMN_CAMARA_NAME + " TEXT NOT NULL, " +
                    COLUMN_CAMARA_ADDRESS + " TEXT , " +
                    COLUMN_CAMARA_KILOMETRO + " TEXT , " +
                    COLUMN_CAMARA_ROAD + " TEXT );";


    private static final String CREATE_TABLE_INCIDENCIAS =
            "CREATE TABLE " + TABLE_INCIDENCIAS + " (" +
                    COLUMN_INCIDENT_ID + " INTEGER PRIMARY KEY , " +
                    COLUMN_INCIDENT_Type + " TEXT , " +
                    COLUMN_INCIDENT_ADDRESS + " TEXT , " +
                    COLUMN_INCIDENT_CAUSE + " TEXT , " +
                    COLUMN_INCIDENT_CITY + " TEXT );";



    private DBManager db;
    private  SQLiteDatabase database;









    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_CAMARAS);
        db.execSQL(CREATE_TABLE_INCIDENCIAS);
            //Co

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAMARAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCIDENCIAS);
        onCreate(db);


    }


}

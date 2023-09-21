package com.example.enigma_assiette;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "restaurants.db";
    private static final int DATABASE_VERSION = 1;

    // Définition de la table
    static final String TABLE_NAME = "restaurants";
    private static final String COLUMN_ID = "_id";
    static final String COLUMN_NOM = "nom";
    static final String COLUMN_DATE_HEURE_REPAS = "date_heure_repas";
    static final String COLUMN_NOTE_DECORATION = "note_decoration";
    static final String COLUMN_NOTE_NOURRITURE = "note_nourriture";
    static final String COLUMN_NOTE_SERVICE = "note_service";
    static final String COLUMN_CRITIQUE = "critique";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOM + " TEXT, " +
                    COLUMN_DATE_HEURE_REPAS + " TEXT, " +
                    COLUMN_NOTE_DECORATION + " REAL, " +
                    COLUMN_NOTE_NOURRITURE + " REAL, " +
                    COLUMN_NOTE_SERVICE + " REAL, " +
                    COLUMN_CRITIQUE + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
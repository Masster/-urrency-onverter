package com.andrey.testsber.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andrey Antonenko on 17.11.2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    // База данных для кеширования
    public static final String DATABASE_NAME = "testsber.db";
    public static final int DATABASE_VERSION = 1;

    // Таблица для данных по курсам валют
    public static final String TABLE_NAME = "CURRENCIES_CONVERTER";

    // Поля таблицы TABLE_NAME
    public static final String CURRENCY_ID = "currencyId";
    public static final String NUMCODE = "numCode";
    public static final String CHARCODE = "charCode";
    public static final String NOMINAL = "nominal";
    public static final String NAME = "name";
    public static final String VALUE = "value";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +
                TABLE_NAME +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CURRENCY_ID + " TEXT, " +
                NUMCODE + " INTEGER, " +
                CHARCODE + " TEXT, " +
                NOMINAL + " INTEGER, " +
                NAME + " TEXT, " +
                VALUE + " REAL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

package com.andrey.testsber.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.andrey.testsber.MainApp;

import java.util.Collections;
import java.util.List;

/**
 * Created by Andrey Antonenko on 18.11.2016.
 */

public class DBManager {

    private static DBHelper helper = new DBHelper(MainApp.getInstance());

    private static DBHelper getHelper() {
        if (helper == null) {
            helper = new DBHelper(MainApp.getInstance());
        }
        return helper;
    }

    private static ContentValues currencyToCV(Currency currency) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.CURRENCY_ID, currency.getCurrencyId());
        cv.put(DBHelper.NUMCODE, currency.getNumCode());
        cv.put(DBHelper.CHARCODE, currency.getCharCode());
        cv.put(DBHelper.NOMINAL, currency.getNominal());
        cv.put(DBHelper.NAME, currency.getCurrencyName());
        cv.put(DBHelper.VALUE, currency.getValue());
        return cv;
    }

    public static void insert(Currency currency) {
        SQLiteDatabase db = getHelper().getWritableDatabase();
        db.beginTransaction();
        try {
            db.insert(DBHelper.TABLE_NAME, null, currencyToCV(currency));
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void delete(Currency currency) {
        SQLiteDatabase db = getHelper().getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(DBHelper.TABLE_NAME, DBHelper.CURRENCY_ID + " = " + currency.getCurrencyId(), null);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void bulkInsert(List<Currency> currencies) {
        SQLiteDatabase db = getHelper().getWritableDatabase();
        db.beginTransaction();
        try {
            for (Currency c : currencies) {
                db.insert(DBHelper.TABLE_NAME, null, currencyToCV(c));
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void deleteAll() {
        SQLiteDatabase db = getHelper().getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(DBHelper.TABLE_NAME, null, null);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static Currency getCurrencyById(String id) {
        return null;
    }

    public static List<Currency> getCurrencies() {

        return Collections.emptyList();
    }
}

package com.andrey.testsber.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andrey.testsber.MainApp;

import java.util.ArrayList;
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

    public static void bulkInsert(List<Currency> currencies) {
        DBManager.deleteAll();
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
        SQLiteDatabase db = getHelper().getReadableDatabase();
        final Cursor cursor = db.query(DBHelper.TABLE_NAME, null, DBHelper.CURRENCY_ID + " = ?", new String[]{id}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return new Currency(cursor);
        } else {
            return null;
        }
    }

    public static List<Currency> getCurrencies() {
        SQLiteDatabase db = getHelper().getReadableDatabase();
        final Cursor cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null) {
            List<Currency> result = new ArrayList<>();
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                result.add(new Currency(cursor));
            }
            return result;
        } else {
            return Collections.emptyList();
        }
    }
}

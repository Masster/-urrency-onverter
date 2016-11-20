package com.andrey.testsber.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.andrey.testsber.MainApp;
import com.andrey.testsber.model.Currency;
import com.andrey.testsber.model.DBManager;

/**
 * Created by Andrey Antonenko on 20.11.2016.
 */

public class CommonUtils {
    private static final String SHARED_PREFS = "Shared prefs";
    private static final String VALUE = "Value";

    public enum CurrencyType {
        FROM_CURRENCY, TO_CURRENCY
    }

    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) MainApp.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void saveLastCurrency(Currency currency, CurrencyType type) {
        SharedPreferences sharedPreferences = MainApp.getInstance().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(type.toString(), currency.getCurrencyId()).apply();
    }

    public static Currency getLastCurrency(CurrencyType type) {
        SharedPreferences sharedPreferences = MainApp.getInstance().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String itemId = sharedPreferences.getString(type.toString(), "");
        if (TextUtils.isEmpty(itemId)) {
            return null;
        } else {
            return DBManager.getCurrencyById(itemId);
        }
    }

    public static double getValue() {
        SharedPreferences sharedPreferences = MainApp.getInstance().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(VALUE, 0);
    }

    public static void saveValue(float value) {
        SharedPreferences sharedPreferences = MainApp.getInstance().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        sharedPreferences.edit().putFloat(VALUE, value).apply();
    }
}

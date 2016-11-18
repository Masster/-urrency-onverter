package com.andrey.testsber.model;

import android.util.Log;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Andrey Antonenko on 17.11.2016.
 */

@Root(name = "Valute")
public class Currency {
    @Attribute(name = "ID", required = false)
    private String currencyId;
    @Element(name = "NumCode", required = false)
    private int numCode;
    @Element(name = "CharCode", required = false)
    private String charCode;
    @Element(name = "Nominal", required = false)
    private int nominal;
    @Element(name = "Name", required = false)
    private String currencyName;
    @Element(name = "Value", required = false)
    private String value;

    public Currency(){

    }

    public Currency(String currencyId,
                    int numCode,
                    String charCode,
                    int nominal,
                    String currencyName,
                    double value) {
        this.currencyId = currencyId;
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.currencyName = currencyName;
        this.value = String.valueOf(value);
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public int getNumCode() {
        return numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public int getNominal() {
        return nominal;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public double getValue() {
        try {
            return Double.valueOf(value.replace(",", "."));
        } catch (Exception e) {
            Log.e(Currency.class.getCanonicalName(), e.getMessage());
            return 0;
        }
    }
}

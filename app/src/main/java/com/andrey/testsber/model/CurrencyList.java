package com.andrey.testsber.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.Collections;
import java.util.List;

/**
 * Created by Andrey Antonenko on 18.11.2016.
 */

@Root(name = "ValCurs")
public class CurrencyList {
    @Attribute(name = "Date")
    private String lastUpdate;
    @Attribute
    private String name;
    @ElementList(name = "Valute", inline = true)
    private List<Currency> currencies;

    public CurrencyList() {
    }

    public List<Currency> getCurrencies() {
        if (currencies == null) {
            return Collections.emptyList();
        } else {
            return currencies;
        }
    }
}

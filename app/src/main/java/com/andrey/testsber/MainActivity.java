package com.andrey.testsber;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.andrey.testsber.model.CurrencyList;
import com.andrey.testsber.model.DBManager;
import com.andrey.testsber.net.API;
import com.andrey.testsber.net.OnRequestSentListener;
import com.andrey.testsber.net.Response;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        API.sendRequestAsync(API.BASE_URL, new OnRequestSentListener() {
            @Override
            public void requestSent(Response response) {
                if (response.isSuccess()) {
                    Log.d(MainActivity.class.getCanonicalName(), response.getSuccessString());
                    Serializer serializer = new Persister();
                    CurrencyList currencyList = null;
                    try {
                        currencyList = serializer.read(CurrencyList.class, response.getSuccessString());
                        DBManager.bulkInsert(currencyList.getCurrencies());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    int i = 0;
                    i = i + 1;
                } else {
                    Log.d(MainActivity.class.getCanonicalName(), response.getErrorString());
                }
            }
        });
    }
}

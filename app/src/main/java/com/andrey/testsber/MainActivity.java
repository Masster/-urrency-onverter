package com.andrey.testsber;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.andrey.testsber.model.CurrencyList;
import com.andrey.testsber.model.DBManager;
import com.andrey.testsber.net.API;
import com.andrey.testsber.net.OnRequestSentListener;
import com.andrey.testsber.net.Response;
import com.andrey.testsber.ui.ConverterScreen;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,new ConverterScreen());
        fragmentTransaction.commit();
    }
}

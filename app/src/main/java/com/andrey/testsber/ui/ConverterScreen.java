package com.andrey.testsber.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.andrey.testsber.R;

/**
 * Created by Andrey Antonenko on 17.11.2016.
 */

public class ConverterScreen extends Fragment {

    private EditText fromCurrencyValue;
    private EditText toCurrencyValue;
    private Spinner fromCurrency;
    private Spinner toCurrency;
    private Button convert;
    private TextView lastUpdateDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.converter_screen, container, false);

        fromCurrencyValue = (EditText) rootView.findViewById(R.id.from_currency_value);
        toCurrencyValue = (EditText) rootView.findViewById(R.id.to_currency_value);
        fromCurrency = (Spinner) rootView.findViewById(R.id.from_currency);
        toCurrency = (Spinner) rootView.findViewById(R.id.to_currency);
        convert = (Button) rootView.findViewById(R.id.convert);
        lastUpdateDate = (TextView) rootView.findViewById(R.id.last_update);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

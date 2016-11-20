package com.andrey.testsber.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.andrey.testsber.R;
import com.andrey.testsber.model.Currency;

import java.util.List;

/**
 * Created by Andrey Antonenko on 17.11.2016.
 */

public class CurrencyAdapter extends ArrayAdapter<Currency> {

    private List<Currency> items;
    private Context context;

    public CurrencyAdapter(Context context, int textViewResourceId, List<Currency> objects) {
        super(context, textViewResourceId, objects);
        this.items = objects;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public Currency getItem(int position) {
        return items.get(position);
    }

    public int getPosition(Currency currency) {
        int position = 0;
        for (Currency curr : items) {
            if (curr.getCurrencyId().equalsIgnoreCase(currency.getCurrencyId()))
                return position;
            position++;
        }
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.spinner_selected_view, parent, false);
        TextView textView = (TextView) rootView.findViewById(android.R.id.text1);
        textView.setText(getItem(position).getCharCode());
        return rootView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.spinner_list_item_view, parent, false);
        TextView textView = (TextView) rootView.findViewById(android.R.id.text1);
        textView.setText(getItem(position).getCurrencyName());
        return rootView;
    }
}

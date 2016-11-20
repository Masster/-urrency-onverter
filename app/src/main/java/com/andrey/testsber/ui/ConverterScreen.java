package com.andrey.testsber.ui;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.andrey.testsber.R;
import com.andrey.testsber.model.Currency;
import com.andrey.testsber.model.CurrencyList;
import com.andrey.testsber.model.DBManager;
import com.andrey.testsber.net.API;
import com.andrey.testsber.net.OnRequestSentListener;
import com.andrey.testsber.net.Response;
import com.andrey.testsber.utils.CommonUtils;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * Created by Andrey Antonenko on 17.11.2016.
 */

public class ConverterScreen extends Fragment {

    private EditText fromCurrencyValue;
    private TextView toCurrencyValue;
    private Spinner fromCurrency;
    private Spinner toCurrency;
    private Button convert;
    private TextView russianRub;

    private LinearLayout content;
    private RelativeLayout emptyView;
    private Button refreshButton;

    private CurrencyAdapter adapter;
    private ProgressDialog dialog;

    private AdapterView.OnItemSelectedListener fromSelectedListener;
    private AdapterView.OnItemSelectedListener toSelectedListener;
    private View.OnClickListener clickListener;
    private IntentFilter connectivityChangeFilter;

    private Currency from;
    private Currency to;
    private double fromValue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectivityChangeFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.converter_screen, container, false);

        fromCurrencyValue = (EditText) rootView.findViewById(R.id.from_currency_value);
        toCurrencyValue = (TextView) rootView.findViewById(R.id.to_currency_value);
        fromCurrency = (Spinner) rootView.findViewById(R.id.from_currency);
        toCurrency = (Spinner) rootView.findViewById(R.id.to_currency);
        convert = (Button) rootView.findViewById(R.id.convert);
        russianRub = (TextView) rootView.findViewById(R.id.russian_rub);
        content = (LinearLayout) rootView.findViewById(R.id.content);
        emptyView = (RelativeLayout) rootView.findViewById(R.id.emptyView);
        refreshButton = (Button) rootView.findViewById(R.id.refreshButton);

        dialog = new ProgressDialog(getContext());
        dialog.setTitle(R.string.dialog_title);
        dialog.setMessage(getContext().getString(R.string.dialog_message));
        dialog.setCancelable(false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
        initViews();
        tryingToUpdate();
    }

    private void initViews() {
        adapter = new CurrencyAdapter(getContext(), R.layout.spinner_selected_view, DBManager.getCurrencies());
        adapter.setDropDownViewResource(R.layout.spinner_list_item_view);
        fromCurrency.setAdapter(adapter);
        toCurrency.setAdapter(adapter);
        from = CommonUtils.getLastCurrency(CommonUtils.CurrencyType.FROM_CURRENCY);
        if (from != null) {
            fromCurrency.setSelection(adapter.getPosition(from));
        }
        to = CommonUtils.getLastCurrency(CommonUtils.CurrencyType.TO_CURRENCY);
        if (to != null) {
            toCurrency.setSelection(adapter.getPosition(to));
        }
        fromCurrencyValue.setText(String.valueOf(CommonUtils.getValue()));
    }

    private void setListeners() {
        fromSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateValues();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
        toSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateValues();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateValues();
                CommonUtils.hideKeyboard(view);
            }
        };
        fromCurrency.setOnItemSelectedListener(fromSelectedListener);
        toCurrency.setOnItemSelectedListener(toSelectedListener);
        convert.setOnClickListener(clickListener);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryingToUpdate();
            }
        });
    }

    private void updateValues() {
        from = (Currency) fromCurrency.getSelectedItem();
        to = (Currency) toCurrency.getSelectedItem();
        try {
            fromValue = Double.valueOf(fromCurrencyValue.getText().toString());
        } catch (Exception e) {
            fromValue = 0;
        }

        final double result = fromValue * (from.getValue() / from.getNominal()) / (to.getValue() / to.getNominal());
        double rub = fromValue * from.getValue() / from.getNominal();
        toCurrencyValue.setText(getContext().getString(R.string.other_currency, to.getCharCode(),result));
        russianRub.setText(getContext().getString(R.string.russian_rub, rub));

        CommonUtils.saveLastCurrency(from, CommonUtils.CurrencyType.FROM_CURRENCY);
        CommonUtils.saveLastCurrency(to, CommonUtils.CurrencyType.TO_CURRENCY);
        CommonUtils.saveValue((float) fromValue);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.convert_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh) {
            tryingToUpdate();
        }
        return super.onOptionsItemSelected(item);
    }

    private void tryingToUpdate() {
        final boolean agressive = DBManager.getCurrencies().isEmpty();
        if (agressive) {
            showHide(false, false);
            dialog.show();
        } else {
            showHide(true, false);
        }
        API.sendRequestAsync(API.BASE_URL, new OnRequestSentListener() {
            @Override
            public void requestSent(Response response) {
                dialog.dismiss();
                if (response.isSuccess()) {
                    Serializer serializer = new Persister();
                    try {
                        final CurrencyList currencyList = serializer.read(CurrencyList.class, response.getSuccessString());
                        DBManager.bulkInsert(currencyList.getCurrencies());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (agressive) {
                        initViews();
                    }
                    showHide(true, false);
                } else if (agressive) {
                    showHide(false, true);
                }
            }
        });
    }

    private void showHide(boolean showCotent, boolean showEmpty) {
        if (showCotent) {
            content.setVisibility(View.VISIBLE);
        } else {
            content.setVisibility(View.GONE);
        }
        if (showEmpty) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }

    }

    private BroadcastReceiver connectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            tryingToUpdate();
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        getActivity().registerReceiver(connectionReceiver, connectivityChangeFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(connectionReceiver);
    }
}

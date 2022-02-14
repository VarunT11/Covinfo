package com.geekhaven.covinfo.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.ViewModelProvider;

import com.example.covinfo.R;
import com.geekhaven.covinfo.enums.RegionType;
import com.geekhaven.covinfo.viewmodels.MainViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String SHARED_PREF_KEY = "PROPS_KEY";
    private static final String REGION_TYPE_KEY = "REGION_TYPE_KEY";

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.setCurrentRegionType(getCurrentRegionType());

        getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            if(event == Lifecycle.Event.ON_START)
                setMenu();
        });
    }

    private void setMenu() {
        TextInputLayout inputRegionMenu = findViewById(R.id.inputHomeRegionMenu);
        AutoCompleteTextView menuRegion = (AutoCompleteTextView) inputRegionMenu.getEditText();

        if (menuRegion != null) {
            ArrayList<String> regionType = new ArrayList<>(Arrays.asList("India", "Global"));
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.region_type_menu_item, regionType);

            menuRegion.setAdapter(arrayAdapter);
            if (getCurrentRegionType() == RegionType.INDIA)
                menuRegion.setText(regionType.get(0), false);
            else
                menuRegion.setText(regionType.get(1), false);

            menuRegion.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String newRegion = s.toString();
                    if (regionType.get(0).equals(newRegion)) {
                        saveCurrentRegionType(RegionType.INDIA);
                        mainViewModel.setCurrentRegionType(RegionType.INDIA);
                    } else {
                        saveCurrentRegionType(RegionType.GLOBAL);
                        mainViewModel.setCurrentRegionType(RegionType.GLOBAL);
                    }
                }
            });
        }
    }

    private void saveCurrentRegionType(RegionType regionType) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(REGION_TYPE_KEY, regionType.name());
        editor.apply();
    }

    private RegionType getCurrentRegionType(){
        SharedPreferences preferences = getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
        return RegionType.valueOf(preferences.getString(REGION_TYPE_KEY, RegionType.INDIA.name()));
    }
}
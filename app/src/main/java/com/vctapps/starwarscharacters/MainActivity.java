package com.vctapps.starwarscharacters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.vctapps.starwarscharacters.model.Register;
import com.vctapps.starwarscharacters.persistence.dao.CharacterDAO;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "mainDebug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

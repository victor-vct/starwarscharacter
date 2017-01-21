package com.vctapps.starwarscharacters.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.vctapps.starwarscharacters.R;
import com.vctapps.starwarscharacters.model.Character;
import com.vctapps.starwarscharacters.model.Register;
import com.vctapps.starwarscharacters.service.ManagerRegister;
import com.vctapps.starwarscharacters.service.OnFinish;

public class DetailCharacterActivity extends AppCompatActivity {

    private static final String TAG = "detailCharacterDebug";
    public static final String CHARACTER_FOR_DETAIL = "com.vctapps.characterfordetails";
    private Register register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_character);

        if(getIntent().getExtras().containsKey(CHARACTER_FOR_DETAIL)){
            register = (Register) getIntent().getExtras().getSerializable(CHARACTER_FOR_DETAIL);

            Log.d(TAG, "Registro recebido: " + register.getLink());

            ManagerRegister manager = new ManagerRegister(this);

            manager.getCharacter(register, new OnFinish<Character>() {
                @Override
                public void onSuccess(Character character) {

                }

                @Override
                public void onError() {

                }
            });
        }
    }
}

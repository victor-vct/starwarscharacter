package com.vctapps.starwarscharacters.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vctapps.starwarscharacters.R;
import com.vctapps.starwarscharacters.model.Character;
import com.vctapps.starwarscharacters.model.CharacterObservable;
import com.vctapps.starwarscharacters.model.Register;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment implements Observer{

    private static final String TAG = "abouFragtDebug";
    private Character character;
    private Register register;

    //Views
    private TextView lastRefreshDatas;
    private TextView lng;
    private TextView lat;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        lastRefreshDatas = (TextView) view.findViewById(R.id.text_refresh_in);
        lng = (TextView) view.findViewById(R.id.text_lng);
        lat = (TextView) view.findViewById(R.id.text_lat);

        setInfosOnViews();

        return view;
    }

    private void setInfosOnViews(){
        if(register != null){
            lng.setText(register.getLng() + "");
            lat.setText(register.getLat() + "");
        }
        if(character != null){
            Date date = new Date(character.getLastRefresh());
            lastRefreshDatas.setText(date.toString());
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if(observable instanceof CharacterObservable){
            CharacterObservable obs = (CharacterObservable) observable;

            Character ch = obs.getCharacter();

            Log.d(TAG, "Personagem recebido na CharacterAbout: " + ch.getName());
        }else{
            Log.d(TAG, "Não foi possível receber um novo personagem");
        }
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public void setRegister(Register register) {
        this.register = register;
    }
}

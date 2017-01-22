package com.vctapps.starwarscharacters.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vctapps.starwarscharacters.R;
import com.vctapps.starwarscharacters.model.Character;
import com.vctapps.starwarscharacters.model.CharacterObservable;

import java.util.Observable;
import java.util.Observer;

public class MoviesFragment extends Fragment implements Observer {

    private static final String TAG = "moviesFragDebug";
    private Character character;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false);
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
}

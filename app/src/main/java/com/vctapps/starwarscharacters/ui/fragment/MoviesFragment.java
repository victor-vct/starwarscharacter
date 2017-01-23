package com.vctapps.starwarscharacters.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vctapps.starwarscharacters.R;
import com.vctapps.starwarscharacters.model.Character;
import com.vctapps.starwarscharacters.model.CharacterObservable;
import com.vctapps.starwarscharacters.ui.adapter.FilmAdapter;

import java.util.Observable;
import java.util.Observer;

public class MoviesFragment extends Fragment implements Observer {

    private static final String TAG = "moviesFragDebug";
    private Character character;

    //List
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager managerLayout;
    private FilmAdapter adapter;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_film_list);
        managerLayout = new LinearLayoutManager(getContext());
        adapter = new FilmAdapter(character.getFilms(), getContext());

        recyclerView.setLayoutManager(managerLayout);
        recyclerView.setAdapter(adapter);

        return view;
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

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

import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class CharacterAboutFragment extends Fragment implements Observer{

    private static final String TAG = "CharacterAboutDebug";
    private Character character;

    //View que recebem informações dos personagens
    private TextView name;
    private TextView height;
    private TextView mass;
    private TextView hairColor;
    private TextView skinColor;
    private TextView eyeColor;
    private TextView birthdayYear;
    private TextView gender;

    public CharacterAboutFragment() {
        // Required empty public constructor
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character_about, container, false);

        name = (TextView) view.findViewById(R.id.text_character_name);
        height = (TextView) view.findViewById(R.id.text_character_height);
        mass = (TextView) view.findViewById(R.id.text_character_mass);
        hairColor = (TextView) view.findViewById(R.id.text_character_hair_color);
        skinColor = (TextView) view.findViewById(R.id.text_character_skin_color);
        eyeColor = (TextView) view.findViewById(R.id.text_character_eye_color);
        birthdayYear = (TextView) view.findViewById(R.id.text_character_birthday_year);
        gender = (TextView) view.findViewById(R.id.text_character_gender);

        setInfoOnViews();

        return view;
    }

    private void setInfoOnViews(){
        if(character != null){
            name.setText(character.getName());
            height.setText(character.getHeight());
            mass.setText(character.getMass());
            hairColor.setText(character.getHairColor());
            skinColor.setText(character.getSkinColor());
            eyeColor.setText(character.getEyeColor());
            birthdayYear.setText(character.getBirthYear());
            gender.setText(character.getGender());
        }
    }



    /**
     * Método chamado sempre que existe novas informações de um character
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        if(observable instanceof CharacterObservable){
            CharacterObservable obs = (CharacterObservable) observable;

            character = obs.getCharacter();

            setInfoOnViews();

            Log.d(TAG, "Personagem recebido na CharacterAbout: " + character.getName());
        }else{
            Log.d(TAG, "Não foi possível receber um novo personagem");
        }
    }
}

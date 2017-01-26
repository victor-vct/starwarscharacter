package com.vctapps.starwarscharacters.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;

import com.vctapps.starwarscharacters.R;
import com.vctapps.starwarscharacters.model.Character;
import com.vctapps.starwarscharacters.model.CharacterObservable;
import com.vctapps.starwarscharacters.model.Register;
import com.vctapps.starwarscharacters.ui.fragment.AboutFragment;
import com.vctapps.starwarscharacters.ui.fragment.CharacterAboutFragment;
import com.vctapps.starwarscharacters.ui.fragment.MoviesFragment;

/**
 * Classe que gerencia os fragments de um viewpager sobre um personagem
 */

public class AboutCharacterAdapter extends FragmentPagerAdapter {

    private CharacterObservable observable; //Observable para cadastrar os Observers
    private Character character; //Todas as view utilizam para mostrar informações
    private Register register; //Apenas a tela about precisa para mostrar informações de geolocalização

    //Fragments
    private FragmentManager fm;
    private Context context;
    private CharacterAboutFragment characterAbout;
    private MoviesFragment movies;
    private AboutFragment about;

    public AboutCharacterAdapter(FragmentManager fm, Context context, Register register,
                                 Character character, CharacterObservable observable) {
        super(fm);
        this.fm = fm;
        this.context = context;
        this.observable = observable;
        this.character = character;
        this.register = register;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                if(fm.getFragments() != null) {
                    for (Fragment frag : fm.getFragments()) {
                        if (frag instanceof CharacterAboutFragment)
                            characterAbout = (CharacterAboutFragment) frag;
                    }
                }
                if(characterAbout == null) characterAbout = new CharacterAboutFragment();
                characterAbout.setCharacter(character);
                observable.addObserver(characterAbout);
                return characterAbout;
            case 1:
                if(fm.getFragments() != null) {
                    for (Fragment frag : fm.getFragments()) {
                        if (frag instanceof MoviesFragment)
                            movies = (MoviesFragment) frag;
                    }
                }
                if(movies == null) movies = new MoviesFragment();
                movies.setCharacter(character);
                observable.addObserver(movies);
                return movies;
            case 2:
                if(fm.getFragments() != null) {
                    for (Fragment frag : fm.getFragments()) {
                        if (frag instanceof AboutFragment)
                            about = (AboutFragment) frag;
                    }
                }
                if(about == null) about = new AboutFragment();
                about.setCharacter(character);
                about.setRegister(register);
                observable.addObserver(about);
                return about;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getResources().getString(R.string.character);
            case 1:
                return context.getResources().getString(R.string.movies);
            case 2:
                return context.getResources().getString(R.string.about);
        }
        return super.getPageTitle(position);
    }
}

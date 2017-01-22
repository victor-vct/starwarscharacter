package com.vctapps.starwarscharacters.model;

import java.util.Observable;

/**
 * Classe com padrão Observable para avisar seus observers sobre um novo Personagem ou atualização
 * do mesmo.
 */

public class CharacterObservable extends Observable{

    private Character mCharacter;

    public void setCharacter(Character character){
        mCharacter = character;

        setChanged();
        notifyObservers();
    }

    public Character getCharacter(){
        return mCharacter;
    }
}

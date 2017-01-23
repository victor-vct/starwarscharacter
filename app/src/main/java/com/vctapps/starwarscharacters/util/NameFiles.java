package com.vctapps.starwarscharacters.util;

import com.vctapps.starwarscharacters.model.Character;
import com.vctapps.starwarscharacters.model.Film;
import com.vctapps.starwarscharacters.model.Register;

/**
 * Classe util para construir nomes a arquivos
 */

public class NameFiles {

    public static String MakeCharacterJsonName(Register register){
        return "Character@" + register.getCharacterName();
    }

    public static String MakeFilmJsonName(String url){
        return "Film@" + url.substring(url.length() - 2, url.length() - 1);
    }
}

package com.vctapps.starwarscharacters.util;

import com.vctapps.starwarscharacters.model.Character;
import com.vctapps.starwarscharacters.model.Register;

/**
 * Classe util para construir nomes a arquivos
 */

public class NameFiles {

    public static String MakeCharacterJsonName(Register register){
        //return register.getUserName() + "@" + register.getCharacterName();
        return register.getCharacterName();
    }
}

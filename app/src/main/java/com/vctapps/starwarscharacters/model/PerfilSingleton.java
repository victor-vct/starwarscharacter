package com.vctapps.starwarscharacters.model;

/**
 * Classe criada para recuperar informações do perfil que está sendo usadoa pós login
 */

public class PerfilSingleton {

    private static Register mRegister;

    private PerfilSingleton(){};

    public static Register getInstance(){
        if(mRegister == null){
            mRegister = new Register();
        }

        return mRegister;
    }
}

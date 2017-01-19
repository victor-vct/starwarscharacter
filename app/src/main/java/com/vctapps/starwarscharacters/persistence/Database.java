package com.vctapps.starwarscharacters.persistence;

import android.content.Context;

/**
 * Classe Singleton para acessar a base de dados.
 */

public class Database {

    private static BDCore mSingleton;

    private Database(){}

    public static BDCore getInstance(Context context){
        if(mSingleton == null) mSingleton = new BDCore(context);

        return mSingleton;
    }
}

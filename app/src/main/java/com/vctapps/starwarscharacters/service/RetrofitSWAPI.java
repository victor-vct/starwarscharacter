package com.vctapps.starwarscharacters.service;

import com.vctapps.starwarscharacters.util.Const;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Victor on 22/01/2017.
 */

public class RetrofitSWAPI {

    private static Retrofit mSingleton;

    private RetrofitSWAPI(){}

    public static Retrofit getInstance(){
        if(mSingleton == null){
            mSingleton = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Const.BASE_URL)
                    .build();
        }

        return mSingleton;
    }
}

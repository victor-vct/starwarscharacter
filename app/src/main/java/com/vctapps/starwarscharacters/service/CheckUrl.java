package com.vctapps.starwarscharacters.service;

import android.util.Log;

import com.vctapps.starwarscharacters.util.Const;

/**
 * Created by Victor on 20/01/2017.
 */

public class CheckUrl {

    public static boolean check(String url){
        Log.d("Check", url);
        return url.contains(Const.BASE_URL);
    }
}

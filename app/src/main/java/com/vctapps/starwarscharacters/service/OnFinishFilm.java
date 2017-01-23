package com.vctapps.starwarscharacters.service;

import com.vctapps.starwarscharacters.model.Film;

/**
 * Created by Victor on 22/01/2017.
 */

public interface OnFinishFilm {

    void onGetFilm(Film film);
    void onGetUrlPoster(String url);
    void onErro();
}

package com.vctapps.starwarscharacters.service;

import com.vctapps.starwarscharacters.model.Film;
import com.vctapps.starwarscharacters.model.Character;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Victor on 21/01/2017.
 */

public interface StarWarsAPI {

    @GET
    Call<Character> getCharacter(@Url String url);

    @GET
    Call<Film> getFilm(@Url String url);
}

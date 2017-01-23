package com.vctapps.starwarscharacters.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Victor on 22/01/2017.
 */

public interface TheMovieDbAPI {

    String BASE_URL = "https://api.themoviedb.org/3/";
    String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w150";

    @GET("search/movie")
    Call<ResponseBody> getMovie(@Query("api_key") String apiKey,
                                @Query("lang") String lang,
                                @Query("query") String query);
}

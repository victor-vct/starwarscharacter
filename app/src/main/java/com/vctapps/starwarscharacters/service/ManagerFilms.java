package com.vctapps.starwarscharacters.service;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.vctapps.starwarscharacters.model.Film;
import com.vctapps.starwarscharacters.persistence.files.ManagerJsonFiles;
import com.vctapps.starwarscharacters.util.NameFiles;
import com.vctapps.starwarscharacters.util.StatusConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Classe que gerencia o os filmes
 */

public class ManagerFilms {

    private static final String TAG = "ManagerFilmsDebug";
    private Context context;

    public ManagerFilms(Context context){
        this.context = context;
    }

    public void getFilm(final String url, final OnFinishFilm callback){
        if(!StatusConnection.isConnected(context)){
            if(ManagerJsonFiles.hasJsonStorage(context, NameFiles.MakeFilmJsonName(url))) {
                Log.d(TAG, "Filme em cache encontrado");
                String stringFilm = ManagerJsonFiles.get(context, NameFiles.MakeFilmJsonName(url));

                Film film = new Gson().fromJson(stringFilm, Film.class);

                callback.onGetFilm(film);

                callback.onGetUrlPoster(film.getUrlPoster());
            }else{
                callback.onErro();
            }
        }else {
            Retrofit retrofit = RetrofitSWAPI.getInstance();

            StarWarsAPI serviceApi = retrofit.create(StarWarsAPI.class);

            Call<Film> getFilm = serviceApi.getFilm(url);

            getFilm.enqueue(new Callback<Film>() {
                @Override
                public void onResponse(Call<Film> call, Response<Film> response) {
                    final Film film = response.body();
                    callback.onGetFilm(film);

                    final ManagerJsonFiles jsonFiles = new ManagerJsonFiles();
                    jsonFiles.save(context, new Gson().toJson(film), NameFiles.MakeFilmJsonName(url));

                    Retrofit theMovieRetro = RetrofitTMDBAPI.getInstance();

                    TheMovieDbAPI theMovieDbAPI = theMovieRetro.create(TheMovieDbAPI.class);

                    Call<ResponseBody> responseBodyCall = theMovieDbAPI
                            .getMovie("b1c8688d49be65c5c58ff7d1517dd182", "pt-BR", film.getTitle());

                    responseBodyCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                JSONObject root = new JSONObject(response.body().string());
                                JSONArray results = root.getJSONArray("results");
                                String poster = results.getJSONObject(0).getString("poster_path");

                                if (poster != null) {
                                    Log.d(TAG, "Link do poster: " + poster);
                                    String link = TheMovieDbAPI.BASE_URL_IMAGE + poster;
                                    film.setUrlPoster(link);
                                    jsonFiles.save(context, new Gson().toJson(film), NameFiles.MakeFilmJsonName(url));
                                    callback.onGetUrlPoster(link);
                                } else {
                                    Log.d(TAG, "Poster null");
                                }
                            } catch (IOException e) {
                                Log.d(TAG, "Não foi possível achar o filme na API");
                                callback.onErro();
                            } catch (JSONException e) {
                                Log.d(TAG, "Erro ao fazer parse do JSON");
                                callback.onErro();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            callback.onErro();
                        }
                    });
                }

                @Override
                public void onFailure(Call<Film> call, Throwable t) {
                    callback.onErro();
                }
            });
        }
    }
}

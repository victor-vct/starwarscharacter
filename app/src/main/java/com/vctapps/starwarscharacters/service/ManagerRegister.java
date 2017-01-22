package com.vctapps.starwarscharacters.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.vctapps.starwarscharacters.model.Character;
import com.vctapps.starwarscharacters.model.Register;
import com.vctapps.starwarscharacters.persistence.dao.RegisterDAO;
import com.vctapps.starwarscharacters.persistence.files.ManagerJsonFiles;
import com.vctapps.starwarscharacters.util.Const;
import com.vctapps.starwarscharacters.util.NameFiles;
import com.vctapps.starwarscharacters.util.StatusConnection;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Classe de serviço que salva e recupera dados
 */

public class ManagerRegister {

    private static final String TAG = "managerRegisterDebug";
    private RegisterDAO dao;
    private Context mContext;

    public ManagerRegister(Context context){
        dao = new RegisterDAO(context);
        mContext = context;
    }

    public void getRegisters(final OnFinish<List<Register>> callback){
        new AsyncTask<Void, Void, List<Register>>(){
            @Override
            protected List<Register> doInBackground(Void... voids) {
                //TODO criar logica para ver
                return dao.readAll();
            }

            @Override
            protected void onPostExecute(List<Register> registers) {
                //Retorna sucesso apenas se houver algum registro
                if(callback != null && registers != null && registers.size() > 0){
                    callback.onSuccess(registers);
                }else{
                    callback.onError();
                }
            }
        }.execute();
    }

    public void saveRegister(Register register, final OnFinish<Register> callback){
        new AsyncTask<Register, Void, Register>(){
            @Override
            protected Register doInBackground(Register... regs) {
                Log.d(TAG, "Iniciando processo para salvar registro");
                Long id = dao.save(regs[0]);

                Log.d(TAG, "Processo finalizado");
                if(id <= 0){
                    Log.d(TAG, "Não foi possível salvar o registro");
                    return null;
                }

                Log.d(TAG, "Registro salvo com sucesso");
                regs[0].setCod(id.intValue());

                return regs[0];
            }

            @Override
            protected void onPostExecute(Register register) {
                if(register == null) {
                    callback.onError();
                    return;
                }

                callback.onSuccess(register);
            }
        }.execute(register);
    }

    public void getCharacter(final Register register, final OnFinish<Character> callback){
        // Primeiro verifica se existe conexão
        // Se existe baixa e salva o arquivo
        // Se não existe verifica se existe arquivo salvo e recupera ele

        if(!StatusConnection.isConnected(mContext)){
            //Pega arquivo em cache, caso exista
            Character character = getCacheFile(register);
            Log.d(TAG, "Personagem recuperado do cache: " + character.getName());
            callback.onSuccess(character);
        }else{
            //Existe conexão, tenta realizar o download
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Const.BASE_URL)
                    .build();

            StarWarsAPI serviceApi = retrofit.create(StarWarsAPI.class);

            Call<Character> getCharacter = serviceApi.getCharacter(register.getLink());

            getCharacter.enqueue(new Callback<Character>() {
                @Override
                public void onResponse(Call<Character> call, Response<Character> response) {
                    if(!response.isSuccessful() || response.body() == null){
                        Log.d(TAG, "Erro ao fazer download do personagem. onSuccess: " + response.message());
                        callback.onError();
                        return;
                    }else{
                        Character person = response.body();
                        Log.d(TAG, "Personagem baixado: " + person.getName());
                        //Atualiza no banco para armazenar o nome do personagem
                        RegisterDAO dao = new RegisterDAO(mContext);
                        register.setCharacterName(person.getName());
                        dao.update(register);

                        //Transforma o json em String para armazenar em cache
                        person.setLastRefresh(Calendar.getInstance().getTimeInMillis());
                        String json = new Gson().toJson(person);

                        Log.d(TAG, json);

                        ManagerJsonFiles.save(mContext, json, NameFiles.MakeCharacterJsonName(register));

                        callback.onSuccess(person);
                    }
                }

                @Override
                public void onFailure(Call<Character> call, Throwable t) {
                    Log.d(TAG, "Erro ao fazer download do personagem. onFailure: " + t.getMessage());
                    callback.onError();
                }
            });
        }
    }

    /**
     * Verifica se existe arquivo salvo e recupera.
     * @param register instancia com dados base para recupearar o arquivo salvo
     * @return Character caso sucesso, null caso contrário;
     */
    private Character getCacheFile(Register register){
        if(register.getCharacterName() != null && !register.getCharacterName().equals("") &&
                ManagerJsonFiles.hasJsonStorage(mContext, NameFiles.MakeCharacterJsonName(register))){
            Character character = new Gson().fromJson(ManagerJsonFiles.get(
                    mContext,
                    NameFiles.MakeCharacterJsonName(register)),
                    Character.class);

            Log.d(TAG, "Personagem recuperado de um arquivo JSON");

            return character;
        }
        return null;
    }
}

package com.vctapps.starwarscharacters.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.vctapps.starwarscharacters.model.Character;
import com.vctapps.starwarscharacters.model.Register;
import com.vctapps.starwarscharacters.persistence.dao.RegisterDAO;
import com.vctapps.starwarscharacters.util.Const;
import com.vctapps.starwarscharacters.util.StatusConnection;

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

    public void getCharacter(Register register, OnFinish<Character> callback){
        if(!StatusConnection.isConnected(mContext)){
            //TODO fazer aviso que não há conexão
        }else{
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Const.BASE_URL)
                    .build();

            StarWarsAPI serviceApi = retrofit.create(StarWarsAPI.class);

            Call<Character> getCharacter = serviceApi.getCharacter(register.getLink());

            getCharacter.enqueue(new Callback<Character>() {
                @Override
                public void onResponse(Call<Character> call, Response<Character> response) {
                    if(!response.isSuccessful()){
                        Log.d(TAG, "Não foi possível baixar o personagem." + response.message() + "ErrorBody" + response.errorBody());
                        //TODO tratar erro
                    }else{
                        Character person = response.body();
                        if(person != null){
                            Log.d(TAG, "Personagem baixado: " + person.getName());
                        }else{
                            Log.d(TAG, "Erro ao baixar personagem");
                        }
                    }
                }

                @Override
                public void onFailure(Call<Character> call, Throwable t) {

                }
            });
        }
    }
}

package com.vctapps.starwarscharacters.service;

import android.content.Context;
import android.os.AsyncTask;

import com.vctapps.starwarscharacters.model.Register;
import com.vctapps.starwarscharacters.persistence.dao.RegisterDAO;

import java.util.List;

/**
 * Created by Victor on 19/01/2017.
 */

public class ManagerRegister {

    private RegisterDAO dao;

    public ManagerRegister(Context context){
        dao = new RegisterDAO(context);
    }

    public void getRegistes(final OnFinish<List<Register>> callback){
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
}

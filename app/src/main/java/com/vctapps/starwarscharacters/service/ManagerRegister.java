package com.vctapps.starwarscharacters.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.vctapps.starwarscharacters.model.Register;
import com.vctapps.starwarscharacters.persistence.dao.RegisterDAO;

import java.util.List;

/**
 * Classe de serviço que salva e recupera dados
 */

public class ManagerRegister {

    private static final String TAG = "managerRegisterDebug";
    private RegisterDAO dao;

    public ManagerRegister(Context context){
        dao = new RegisterDAO(context);
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
}

package com.vctapps.starwarscharacters.presenter;

import android.content.Context;

import com.vctapps.starwarscharacters.model.Register;
import com.vctapps.starwarscharacters.service.ManagerRegister;
import com.vctapps.starwarscharacters.service.OnFinish;

import java.util.List;

/**
 * Presenter que redireciona para os serviços corretos
 */

public class RegistersPresenter {

    private ManagerRegister mManager;

    public RegistersPresenter(Context context){
        mManager = new ManagerRegister(context);
    }

    public void getAllRegisters(OnFinish<List<Register>> callback){
        mManager.getRegisters(callback);
    }

    public void saveRegister(Register register, OnFinish<Register> callback){
        mManager.saveRegister(register, callback);
    }
}

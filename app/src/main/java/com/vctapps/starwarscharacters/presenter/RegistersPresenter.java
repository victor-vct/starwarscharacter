package com.vctapps.starwarscharacters.presenter;

import android.content.Context;

import com.vctapps.starwarscharacters.model.Register;
import com.vctapps.starwarscharacters.service.ManagerRegister;
import com.vctapps.starwarscharacters.service.OnFinish;

import java.util.List;

/**
 * Created by Victor on 19/01/2017.
 */

public class RegistersPresenter {

    private ManagerRegister mManager;

    public RegistersPresenter(Context context){
        mManager = new ManagerRegister(context);
    }

    public void getAllRegisters(OnFinish<List<Register>> callback){
        mManager.getRegistes(callback);
    }
}

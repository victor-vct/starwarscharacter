package com.vctapps.starwarscharacters.presenter;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.vctapps.starwarscharacters.model.PerfilSingleton;
import com.vctapps.starwarscharacters.model.Register;
import com.vctapps.starwarscharacters.service.LastLocationService;
import com.vctapps.starwarscharacters.service.ManagerRegister;
import com.vctapps.starwarscharacters.service.OnFinish;

import java.util.List;

/**
 * Presenter que redireciona para os serviços
 */

public class RegistersPresenter {

    private static final String TAG = "registerPresenter";
    private ManagerRegister mManager;
    private Context mContext;

    public RegistersPresenter(Context context){
        mManager = new ManagerRegister(context);
        mContext = context;
    }

    /**
     * Método que resgata todos os registros
     * @param callback
     */
    public void getAllRegisters(OnFinish<List<Register>> callback){
        mManager.getRegisters(callback);
    }

    /**
     * Método que salva o registro do usuário atual.
     * Pega a instancia do usuário logado, salva a url do personagem na intancia.
     * Tenta pegar a geolocalização, caso sucesso salva no banco, caso erro salva sem dados de
     * geolocalização
     * @param url
     * @param callback
     */
    public void saveRegisterWithUrl(String url, final OnFinish<Register> callback){
        final Register register = PerfilSingleton.getInstance();
        register.setLink(url);
        //inicializa o serviço de localização
        LastLocationService lastLocationService = new LastLocationService(mContext);
        lastLocationService.getLocation(new OnFinish<Location>() {
            @Override
            public void onSuccess(Location location) {
                register.setLat(location.getLatitude());
                register.setLng(location.getLongitude());
                Log.d(TAG, "Geolocation: " + location.getLatitude() + " | " + location.getLongitude());
                mManager.saveRegister(register, callback);
            }

            @Override
            public void onError() {
                //TODO implementar um aviso que não foi possível pegar geolocalização
                Log.d(TAG, "Erro ao pegar geolocalização");
                mManager.saveRegister(register, callback);
            }
        });
    }
}

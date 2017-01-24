package com.vctapps.starwarscharacters.service;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Class LastLocationService created on 23/01/2017.
 */

public class LastLocationService implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, OnFinish<Location> {

    private static final String TAG = "geofenceDebug";
    private OnFinish<Location> onFinish;
    private GoogleApiClient mApiClient;
    private Context context;

    public LastLocationService(Context context) {
        this.context = context;
    }

    /**
     * Método que inicia o processo de solicitação de geolocalização
     * @param onFinish callback chamado ao ocorrer sucesso ou erro
     */
    public void getLocation(OnFinish<Location> onFinish){
        this.onFinish = onFinish;

        mApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mApiClient.connect();
        Log.d(TAG, "Iniciando serviço de geolocalização");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed");
        onError();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "Serviço de geolização conectado com sucesso");
        //Verifica se existe permissão
        int check = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);

        if(check == PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "Permissão para geolocalização: Ok");
            Location location = LocationServices.FusedLocationApi.getLastLocation(mApiClient);
            onSuccess(location);
        }else{
            Log.d(TAG, "Não existe permissão para acesso a geolozalição");
            onError();
        }
        mApiClient.disconnect();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended");
        onError();
    }

    /**
     * Método chamado internamento ao finalizar processo
     * @param location Instancia com informações de localização
     */
    @Override
    public void onSuccess(Location location) {
        if(onFinish != null) onFinish.onSuccess(location);
    }

    /**
     * Método chamado internamento ao ocorrer erro durante o processo
     */
    @Override
    public void onError() {
        if(onFinish != null) onFinish.onError();
    }
}

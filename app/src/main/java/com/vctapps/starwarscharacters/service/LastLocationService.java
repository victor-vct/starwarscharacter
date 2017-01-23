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

    private OnFinish<Location> onFinish;
    private GoogleApiClient mApiClient;
    private Context context;

    public LastLocationService(Context context) {
        this.context = context;
    }

    public void getLocation(OnFinish<Location> onFinish){
        this.onFinish = onFinish;

        mApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        onError();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Verifica se existe permissão
        int check = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);

        if(check == PackageManager.PERMISSION_GRANTED){
            Location location = LocationServices.FusedLocationApi.getLastLocation(mApiClient);
            onSuccess(location);
        }else{
            //TODO solicitar permissão de localização
            //requestPermissionCamera();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        onError();
    }

    @Override
    public void onSuccess(Location location) {
        if(onFinish != null) onFinish.onSuccess(location);
    }

    @Override
    public void onError() {
        if(onFinish != null) onFinish.onError();
    }
}

package com.vctapps.starwarscharacters.service;

/**
 * Interface callback para retornos de consultas
 */

public interface OnFinish<T> {

    void onSuccess(T t);
    void onError();
}

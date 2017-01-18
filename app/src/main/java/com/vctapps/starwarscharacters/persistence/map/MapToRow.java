package com.vctapps.starwarscharacters.persistence.map;

import android.content.ContentValues;

import java.util.List;

/**
 * Interface com contrato de todos metodos de mapeamento de Objeto para ContentValues
 */

public interface MapToRow<T> {

    /**
     * Método que faz mapeamento de um objeto
     * @param t Instancia de um objeto
     * @return ContentValues com informações do objeto passado como parâmetro
     */
    ContentValues mapToRow(T t);
}

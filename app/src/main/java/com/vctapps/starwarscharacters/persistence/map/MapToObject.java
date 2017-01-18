package com.vctapps.starwarscharacters.persistence.map;

import android.database.Cursor;

import java.util.List;

/**
 * Interface com contrato de todos metodos de mapeamento de Cursor para objeto
 */

public interface MapToObject<T> {

    /**
     * Mapeamento de um cursor para um objeto
     * @param cursor
     * @return Instancia de um objeto com base nos dados do cursor
     */
    T mapToObject(Cursor cursor);

    /**
     * Mapeamento de um cursor para uma lista de objetos
     * @param cursor
     * @return Instancia de um List com objetos criados com base nos dados do cursor
     */
    List<T> mapToObjects(Cursor cursor);
}

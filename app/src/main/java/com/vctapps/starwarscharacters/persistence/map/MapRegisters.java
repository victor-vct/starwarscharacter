package com.vctapps.starwarscharacters.persistence.map;

import android.content.ContentValues;
import android.database.Cursor;

import com.vctapps.starwarscharacters.model.Register;
import com.vctapps.starwarscharacters.persistence.ConstDb.Registers;

import java.util.ArrayList;
import java.util.List;

/**
 * Class criada para fazer o mapeamento de objetos do tipo Register que serão salvos/recuperados
 * do Banco de dados
 */

public class MapRegisters implements Map<Register> {

    /**
     * Faz o mapeamento de um Cursor para uma instancia Register
     * @param cursor Cursor com dados vindo do BD
     * @return Register caso sucesso, null caso algum erro com o Cursor
     */
    @Override
    public Register mapToObject(Cursor cursor) {
        if(cursor != null && cursor.moveToFirst()){

            return mapToRegister(cursor);

        }
        return null;
    }

    /**
     * Faz o mapeamento de um Cursor para uma lista de Register
     * @param cursor Cursor com dados vindo do BD
     * @return List de Register caso sucesso, null caso algum erro com o Cursor
     */
    @Override
    public List<Register> mapToObjects(Cursor cursor) {
        if(cursor != null && cursor.moveToFirst()){
            List<Register> regs = new ArrayList<>();

            do{
                Register reg = mapToRegister(cursor);

                regs.add(reg);
            }while (cursor.moveToNext());

            return regs;
        }
        return null;
    }

    /**
     * Faz o mapeamento de um Register para um ContentValues
     * @param reg instancia de um Register
     * @return ContentValues
     */
    @Override
    public ContentValues mapToRow(Register reg) {
        if(reg != null){
            ContentValues values = new ContentValues();

            values.put(Registers.COLUMN_USER_NAME, reg.getUserName());
            values.put(Registers.COLUMN_LINK, reg.getLink());
            values.put(Registers.COLUMN_LAT, reg.getLat());
            values.put(Registers.COLUMN_LNG, reg.getLng());
            if(reg.getCharacterName() != null)
                values.put(Registers.COLUMN_CHARACTER_NAME, reg.getCharacterName());

            return values;
        }
        return null;
    }

    /**
     * Método que faz mapeamento de um Cursor para Register. Método que chamar já deve ter apontado
     * para o registro correto com moveToFirst() ou moveToNext().
     * @param cursor Cursor
     * @return Instancia de um Register com informações vindas do cursor
     */
    private Register mapToRegister(Cursor cursor){
        if(cursor != null) {
            Register reg = new Register();

            reg.setCod(cursor.getInt(
                    cursor.getColumnIndex(Registers.COLUMN_COD)
            ));
            reg.setUserName(cursor.getString(
                    cursor.getColumnIndex(Registers.COLUMN_USER_NAME)
            ));
            reg.setLink(cursor.getString(
                    cursor.getColumnIndex(Registers.COLUMN_LINK)
            ));
            reg.setCharacterName(cursor.getString(
                    cursor.getColumnIndex(Registers.COLUMN_CHARACTER_NAME)
            ));
            reg.setLat(cursor.getDouble(
                    cursor.getColumnIndex(Registers.COLUMN_LAT)
            ));
            reg.setLng(cursor.getDouble(
                    cursor.getColumnIndex(Registers.COLUMN_LNG)
            ));

            return reg;
        }
        return null;
    }
}

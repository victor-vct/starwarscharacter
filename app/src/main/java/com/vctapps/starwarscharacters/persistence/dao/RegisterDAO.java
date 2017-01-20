package com.vctapps.starwarscharacters.persistence.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.vctapps.starwarscharacters.model.Register;
import com.vctapps.starwarscharacters.persistence.ConstDb.Registers;
import com.vctapps.starwarscharacters.persistence.map.MapRegisters;

import java.util.List;

/**
 * Classe responsável por fazer acesso ao DB
 */

public class RegisterDAO extends BaseDAO<Register> {

    private MapRegisters map;

    public RegisterDAO(Context context) {
        super(context);
        map = new MapRegisters();
    }

    /**
     * Recupera um registro no banco
     * @param id número do id do registro
     * @return instancia de um Register com dados recuperados
     */
    @Override
    public Register readById(int id) {
        openDb();
        try {
            Cursor query = mDb.query(Registers.TABLE_NAME, Registers.COLUMNS,
                    Registers.COLUMN_COD + " = " + id,
                    null, null, null, null);

            return map.mapToObject(query);
        }catch (SQLiteException e){
            Log.e(TAG, "Erro ao buscar todos registros. " + e.getMessage());
        }finally {
            closeDb();
        }
        return null;
    }

    /**
     * Recupera todos os registros do banco
     * @return Array com todos registros
     */
    @Override
    public List<Register> readAll() {
        openDb();
        try {
            mCursor = mDb.query(Registers.TABLE_NAME, Registers.COLUMNS, null, null, null, null, null);

            return map.mapToObjects(mCursor);
        }catch (SQLiteException e){
            Log.e(TAG, "Erro ao buscar todos registros. " + e.getMessage());
        }finally {
            closeDb();
        }
        return null;
    }

    /**
     * Salva os dados de um registro no banco de dados
     * @param register instancia com dados a serem salvos
     * @return long com número do registro
     */
    @Override
    public long save(Register register) {
        openDb();
        try {
            long resp = mDb.insert(Registers.TABLE_NAME, null, map.mapToRow(register));

            return resp;
        }catch (SQLiteException e){
            Log.e(TAG, "Erro ao salvar o registro. " + e.getMessage());
            return -1;
        }finally {
            closeDb();
        }
    }

    /**
     * Atualiza valores no registro baseado na instancia de um Register
     * @param register instancia com dados do Register
     * @return int com número de dados alterados, -1 caso erro
     */
    @Override
    public int update(Register register) {
        openDb();
        try {
            int resp = mDb.update(Registers.TABLE_NAME, map.mapToRow(register),
                    Registers.COLUMN_COD + " = " + register.getCod(), null);

            return resp;
        }catch (SQLiteException e){
            Log.e(TAG, "Erro ao atualizar o registro. " + e.getMessage());
            return -1;
        }finally {
            closeDb();
        }
    }

    /**
     * Deleta um registro baseado na instancia de um Register
     * @param register instancia com dados do Register
     * @return int com número de registros alterados, -1 caso erro
     */
    @Override
    public int delete(Register register) {
        openDb();
        try {
            int resp = mDb.delete(Registers.TABLE_NAME,
                    Registers.COLUMN_COD + " = " + register.getCod(), null);

            return resp;
        }catch (SQLiteException e){
            Log.e(TAG, "Erro ao deletar o registro. " + e.getMessage());
            return -1;
        }finally {
            closeDb();
        }
    }

    /**
     * Deleta um registro no banco a partir de um ID
     * @param id int com o id do registro
     * @return número de registros alterados, -1 caso erro
     */
    @Override
    public int delete(int id) {
        openDb();
        try {
            int resp = mDb.delete(Registers.TABLE_NAME,
                    Registers.COLUMN_COD + " = " + id, null);

            return resp;
        }catch (SQLiteException e){
            Log.e(TAG, "Erro ao deletar o registro. " + e.getMessage());
            return -1;
        }finally {
            closeDb();
        }
    }
}

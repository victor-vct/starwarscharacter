package com.vctapps.starwarscharacters.persistence.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vctapps.starwarscharacters.persistence.Database;

/**
 * Created by Victor on 19/01/2017.
 */

public abstract class BaseDAO<T> implements DAO<T>{

    protected Context mContext;
    protected SQLiteDatabase mDb;
    protected Cursor mCursor;
    protected static final String TAG = "daoDebug";

    protected BaseDAO(Context context){
        mContext = context;
    }

    /**
     * Método responsável por abrir conexão com banco
     */
    protected void openDb(){
        mDb = Database.getInstance(mContext).getWritableDatabase();
    }

    /**
     * Método responsável por fechar a conexão com o banco
     */
    protected void closeDb(){
        if(mDb != null) mDb.close();
        if(mCursor != null) mCursor.close();
    }
}

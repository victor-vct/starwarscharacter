package com.vctapps.starwarscharacters.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class BDCore created on 18/01/2017.
 */

public class BDCore extends SQLiteOpenHelper {

    private static final String DATABASE = "starwars";
    private static final int VERSION_DB = 0;

    public BDCore(Context context) {
        super(context, DATABASE, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ConstDb.Registers.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //Do something if changes in database
    }
}

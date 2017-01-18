package com.vctapps.starwarscharacters.persistence;

/**
 * Class ConstDb created on 18/01/2017.
 */

public class ConstDb {

    private static final String PK = " id INTEGER PRIMARY KEY AUTOINCREMENT ";
    private static final String FK = " FOREIGN KEY ";
    private static final String REF = " REFERENCES ";
    private static final String NN = " NOT NULL ";
    private static final String INT = " INTEGER ";
    private static final String INT_NN = " INTEGER " + NN;
    private static final String DOUBLE = " REAL ";
    private static final String DOUBLE_NN = " REAL " + NN;
    private static final String DATE = " TEXT ";
    private static final String DATE_NN = " TEXT " + NN;
    private static final String TEXT = " TEXT ";
    private static final String TEXT_NN = " TEXT " + NN;
    private static final String CM = " , ";

    public static class Registers{
        public static final String TABLE_NAME = "register";
        public static final String COLUMN_COD = "cod_reg";
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_LINK = "link";
        public static final String COLUMN_CHARACTER_NAME = "character";
        public static final String COLUMN_LAT = "lat";
        public static final String COLUMN_LNG = "lng";

        public static final String[] COLUMNS = new String[]{
                COLUMN_COD,
                COLUMN_USER_NAME,
                COLUMN_LINK,
                COLUMN_CHARACTER_NAME,
                COLUMN_LAT,
                COLUMN_LNG
        };

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
                COLUMN_COD + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + TEXT_NN +
                COLUMN_LINK + TEXT_NN +
                COLUMN_CHARACTER_NAME + TEXT +
                COLUMN_LAT + DOUBLE +
                COLUMN_LNG + DOUBLE + " )";

        public static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME;

    }
}

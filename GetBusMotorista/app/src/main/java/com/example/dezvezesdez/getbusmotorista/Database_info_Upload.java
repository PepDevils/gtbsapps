package com.example.dezvezesdez.getbusmotorista;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dezvezesdez on 23/05/16.
 */
public class Database_info_Upload extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "upload_info";

    protected static final String PACKAGE_NAME = "com.example.dezvezesdez.getbusmotorista";
    protected static final String DATABASE_PATH = PACKAGE_NAME + "." + DATABASE_NAME;


    protected static final String TABELA_UPLOADED_INFO = "tickets_uploaded_info";
    protected static final String UPLOADED_ID = "upload_id";
    protected static final String UPLOADED_STRING = "upload_STRING";


    //construtor
    public Database_info_Upload(Activity context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        String sql_create_table = "CREATE TABLE " + TABELA_UPLOADED_INFO + "("
                + UPLOADED_ID + " integer primary key,"
                + UPLOADED_STRING + " text "
                + ");";

        db.execSQL(sql_create_table);


    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {

        String sql_drop_if_exists_table = "DROP TABLE IF EXISTS" + TABELA_UPLOADED_INFO;
        db.execSQL(sql_drop_if_exists_table);

        onCreate(db);

    }

    public void InserirDado(Activity c, String id, String uploaded) {

        id = DataBase_Controller.VerificacaoNull(id);
        uploaded = DataBase_Controller.VerificacaoNull(uploaded);

        Database_info_Upload banco = new Database_info_Upload(c);
        SQLiteDatabase db = banco.getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put(Database_info_Upload.UPLOADED_ID, id);
        valores.put(Database_info_Upload.UPLOADED_STRING, uploaded);

        db.insertWithOnConflict(Database_info_Upload.TABELA_UPLOADED_INFO, null, valores, SQLiteDatabase.CONFLICT_REPLACE);

        db.close();

    }

    public Cursor BuscarDado(Activity activity) {

        Cursor cursor;


        String where = Database_info_Upload.UPLOADED_ID + "=" + "1";

        String[] campos = {Database_info_Upload.UPLOADED_ID, Database_info_Upload.UPLOADED_STRING};

        Database_info_Upload banco = new Database_info_Upload(activity);
        SQLiteDatabase db = banco.getReadableDatabase();
        cursor = db.query(Database_info_Upload.TABELA_UPLOADED_INFO, campos, where, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }



        return cursor;

    }

    // return true if it exists and can be read, false if it doesn't
    public static boolean checkDataBase(Context c) {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(String.valueOf(c.getDatabasePath(Database_info_Upload.DATABASE_NAME)), null, SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }


}

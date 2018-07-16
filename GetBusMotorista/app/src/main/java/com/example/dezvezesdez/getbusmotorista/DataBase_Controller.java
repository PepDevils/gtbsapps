package com.example.dezvezesdez.getbusmotorista;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

/**
 * Created by dezvezesdez on 25/05/16.
 */
public class DataBase_Controller {

    //base de dados
    private static Database banco;
    private static SQLiteDatabase db;

    //Construtor
    public DataBase_Controller(Context context) {
        banco = new Database(context);
    }


    //inserir dados na base de dados
    public boolean InsertDataBase(BILHETE bib) {

        //retirar valores da classe bilhete
        String id = bib.getID();
        String ip_client = bib.getIP_CLIENT();
        String num_id_client = bib.getNUM_ID_CLIENT();
        String name_client = bib.getNAME_CLIENT();
        String phone_client = bib.getPHONE_CLIENT();
        String email_cliente = bib.getEMAIL_CLIENT();
        String type_trip = bib.getTYPE_TRIP();
        String destination_trip = bib.getDESTINATION_TRIP();
        String source_trip = bib.getSOURCE_TRIP();
        String num_child = bib.getNUM_CHILD();
        String num_adult = bib.getNUM_ADULT();
        String date_go = bib.getDATE_GO();
        String date_come = bib.getDATE_COME();
        String type_pay = bib.getTYPE_PAY();
        String date_pay = bib.getDATE_PAY();
        String total_pay = bib.getTOTAL_PAY();

        //validar esses valores
        ip_client = VerificacaoNull(ip_client);
        num_id_client = VerificacaoNull(num_id_client);
        name_client = VerificacaoNull(name_client);
        phone_client = VerificacaoNull(phone_client);
        email_cliente = VerificacaoNull(email_cliente);
        type_trip = VerificacaoNull(type_trip);
        destination_trip = VerificacaoNull(destination_trip);
        source_trip = VerificacaoNull(source_trip);
        num_child = VerificacaoNull(num_child);
        num_adult = VerificacaoNull(num_adult);
        date_go = VerificacaoNull(date_go);
        date_come = VerificacaoNull(date_come);
        type_pay = VerificacaoNull(type_pay);
        date_pay = VerificacaoNull(date_pay);
        total_pay = VerificacaoNull(total_pay);

        //ir buscar a base de dados, com o insert da tabaela ja feito "onCreat"
        db = banco.getWritableDatabase();

        //valor do conteudo para preencher a tabela da base de dados
        ContentValues valores = new ContentValues();

        valores.put(Database.ID_, id);
        valores.put(Database.IP_CLIENT, ip_client);
        valores.put(Database.NUM_ID_CLIENT, num_id_client);
        valores.put(Database.NAME_CLIENT, name_client);
        valores.put(Database.PHONE_CLIENT, phone_client);
        valores.put(Database.EMAIL_CLIENT, email_cliente);
        valores.put(Database.TYPE_TRIP, type_trip);
        valores.put(Database.DESTINATION_TRIP, destination_trip);
        valores.put(Database.SOURCE_TRIP, source_trip);
        valores.put(Database.NUM_CHILD, num_child);
        valores.put(Database.NUM_ADULT, num_adult);
        valores.put(Database.DATE_GO, date_go);
        valores.put(Database.DATE_COME, date_come);
        valores.put(Database.TYPE_PAY, type_pay);
        valores.put(Database.DATE_PAY, date_pay);
        valores.put(Database.TOTAL_PAY, total_pay);

        //fazer o insert dos valores ou se já existem(conflito) então actualiza-os, dos valores criados acima
        //long resultado = db.insertOrThrow(Database.TABELA, null, valores);
        long resultado = db.insertWithOnConflict(Database.TABELA, null, valores, SQLiteDatabase.CONFLICT_REPLACE);


        //fechar a base de dados para evitar erros e melhorar a preformance
        db.close();

        //retornar um boolean, poderá ser necessario????
        if (resultado == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean InsertDataBaseValidator(BILHETE bib){

        String validation_id = bib.getVALIDATOR_ID();
        String post_id = bib.getID();
        String validation_ida = bib.getVALIDATOR_IDA();
        String validation_volta = bib.getVALIDATOR_VOLTA();

        validation_id = VerificacaoNull(validation_id);
        validation_ida = VerificacaoNull(validation_ida);
        validation_volta = VerificacaoNull(validation_volta);
        post_id = VerificacaoNull(post_id);

        db = banco.getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put(Database.VALIDATOR_ID, validation_id);
        valores.put(Database.POST_ID, post_id);
        valores.put(Database.VALIDATOR_IDA, validation_ida);
        valores.put(Database.VALIDATOR_VOLTA, validation_volta);

        long resultado = 0;
        try {
            resultado = db.insertWithOnConflict(Database.TABELA_VALIDATOR, null, valores, SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();


        if (resultado == -1) {
            return false;
        } else {
            return true;
        }


    }




    public Cursor carregaDadoById(int id) {
        Cursor cursor;

        String where = Database.ID_ + "=" + id;

        String[] campos = {Database.ID_, Database.IP_CLIENT, Database.NUM_ID_CLIENT, Database.NAME_CLIENT, Database.PHONE_CLIENT,
                Database.EMAIL_CLIENT, Database.TYPE_TRIP, Database.DESTINATION_TRIP, Database.SOURCE_TRIP, Database.NUM_CHILD,
                Database.NUM_ADULT, Database.DATE_GO, Database.DATE_COME, Database.TYPE_PAY, Database.DATE_PAY, Database.TOTAL_PAY};


        db = banco.getReadableDatabase();
        cursor = db.query(Database.TABELA, campos, where, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public static Cursor CarregaDadosdeValidaçãoporId(int id){
        Cursor cursor;

        String where = Database.POST_ID + "=" + id;

        String[] campos = {Database.POST_ID, Database.VALIDATOR_ID, Database.VALIDATOR_IDA, Database.VALIDATOR_VOLTA};

        db = banco.getReadableDatabase();
        cursor = db.query(Database.TABELA_VALIDATOR, campos, where, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }


        return cursor;
    }


    public static void alteraRegistoValidator(String post_id, String validator_ida, String validator_volta){
        ContentValues valores;
        String where;

        db = banco.getWritableDatabase();

        where = Database.POST_ID + "=" + post_id;

        valores = new ContentValues();
        valores.put(Database.VALIDATOR_IDA, validator_ida);
        valores.put(Database.VALIDATOR_VOLTA, validator_volta);

        db.update(Database.TABELA_VALIDATOR, valores, where, null);
        //db.updateWithOnConflict(Database.TABELA_VALIDATOR, valores, where, null ,SQLiteDatabase.CONFLICT_ROLLBACK);
        //db.updateWithOnConflict(Database.TABELA_VALIDATOR, valores, where, null ,SQLiteDatabase.CONFLICT_IGNORE);
    }

    // return true if it exists and can be read, false if it doesn't
    public static boolean checkDataBase(Context c) {
        SQLiteDatabase checkDB = null;
        try {
            //checkDB = SQLiteDatabase.openDatabase(Database.DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
            checkDB = SQLiteDatabase.openDatabase(String.valueOf(c.getDatabasePath(Database.DATABASE_NAME)), null, SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }


    public static String VerificacaoNull(String a) {
        String null_string = "NULL";


        try {

            if (a.equalsIgnoreCase("")) {
                a = null_string;

            }
        } catch (Exception e) {
            e.printStackTrace();
            a = null_string;
        }


        return a;

    }


}

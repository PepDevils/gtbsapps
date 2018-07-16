package com.example.dezvezesdez.getbusmotorista;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dezvezesdez on 23/05/16.
 */
public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "tickets_last_days";

    protected static final String PACKAGE_NAME = "com.example.dezvezesdez.getbusmotorista";
    protected static final String DATABASE_PATH = PACKAGE_NAME + "." + DATABASE_NAME;

    protected static final String TABELA = "tickets_last_days";
    protected static final String ID_ = "id";
    protected static final String IP_CLIENT = "ip_client";
    protected static final String NUM_ID_CLIENT = "num_id_client";
    protected static final String NAME_CLIENT = "name_client";
    protected static final String PHONE_CLIENT = "phone_client";
    protected static final String EMAIL_CLIENT = "email_cliente";
    protected static final String TYPE_TRIP = "type_trip";
    protected static final String DESTINATION_TRIP = "destination_trip";
    protected static final String SOURCE_TRIP = "source_trip";
    protected static final String NUM_CHILD = "num_child";
    protected static final String NUM_ADULT = "num_adult";
    protected static final String DATE_GO = "date_go";
    protected static final String DATE_COME = "date_come";
    protected static final String TYPE_PAY = "type_pay";
    protected static final String DATE_PAY = "date_pay";
    protected static final String TOTAL_PAY = "total_pay";

    protected static final String TABELA_VALIDATOR = "tickets_validator_last_days";
    protected static final String VALIDATOR_ID = "validator_id";
    protected static final String POST_ID = "post_id";
    protected static final String VALIDATOR_IDA = "validator_ida";
    protected static final String VALIDATOR_VOLTA = "validator_volta";


/*    protected static final String TABELA_UPLOADED_INFO = "tickets_uploaded_info";
    protected static final String UPLOADED_ID = "upload_id";
    protected static final String UPLOADED_STRING = "upload_STRING";*/


    //construtor
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        //quando a base de dados é criada, cria tb logo a tabela
        String sql_create_table = "CREATE TABLE " + TABELA + "("
                + ID_ + " integer primary key,"
                //+ ID_ + "text,"
                + IP_CLIENT + " text,"
                + NUM_ID_CLIENT + " text,"
                + NAME_CLIENT + " text,"
                + PHONE_CLIENT + " text,"
                + EMAIL_CLIENT + " text,"
                + TYPE_TRIP + " text,"
                + DESTINATION_TRIP + " text,"
                + SOURCE_TRIP + " text,"
                + NUM_CHILD + " text,"
                + NUM_ADULT + " text,"
                + DATE_GO + " text,"
                + DATE_COME + " text,"
                + TYPE_PAY + " text,"
                + DATE_PAY + " text,"
                + TOTAL_PAY + " text"
                + ");";

        String sql_create_table_validator = "CREATE TABLE " + TABELA_VALIDATOR + "("
                + VALIDATOR_ID + " integer primary key,"
                + POST_ID + " text,"
                + VALIDATOR_IDA + " text,"
                + VALIDATOR_VOLTA + " text"
                + ");";



        db.execSQL(sql_create_table);
        db.execSQL(sql_create_table_validator);

    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {

        //mudar o valor da versão como??????

        String sql_drop_if_exists_table = "DROP TABLE IF EXISTS" + TABELA;
        String sql_drop_if_exists_table_validator = "DROP TABLE IF EXISTS" + TABELA_VALIDATOR;

        db.execSQL(sql_drop_if_exists_table);
        db.execSQL(sql_drop_if_exists_table_validator);

        onCreate(db);

    }



}

package edu.fameg.helloworld.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Allan on 11/03/2017.
 */
public class Banco extends SQLiteOpenHelper {
    public Banco(Context context, String name, int version) {
        super(context, name, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqld){

        sqld.execSQL("CREATE TABLE viagens("
                + "id_viagem INTEGER PRIMARY KEY autoincrement, "
                + "destino varchar(45) NOT NULL ,"
                + "tipo_viagem varchar(45) NOT NULL, "
                + "data_chegada varchar (8) NOT NULL, "
                + "data_partida varchar (8) NOT NULL, "
                + "orcamento double NOT NULL, "
                + "qtd_pessoas INTEGER NOT NULL, "
                + "imagem varchar(60)"
                + "); ");

        sqld.execSQL("CREATE TABLE gastos(" +
                "id_gastos INTEGER PRIMARY KEY autoincrement," +
                "tipo_gasto varchar(45) NOT NULL, " +
                "valor double NOT NULL, " +
                "data varchar (8) NOT NULL, " +
                "descricao varchar(70), " +
                "local varchar(45) NOT NULL, " +
                "id_viagem INTEGER NOT NULL, " +
                "FOREIGN KEY(id_viagem) REFERENCES viagem(id_viagem)" +
                ");" );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO
    }



}


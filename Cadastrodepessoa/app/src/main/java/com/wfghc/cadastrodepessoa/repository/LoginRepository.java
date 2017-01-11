package com.wfghc.cadastrodepessoa.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wfghc.cadastrodepessoa.util.Constants;
import com.wfghc.cadastrodepessoa.util.WrapperLog;

/**
 * Created by wesleygoes on 04/06/16.
 */
public class LoginRepository extends SQLiteOpenHelper {



    public LoginRepository(Context context) {
        super(context, Constants.BD_NOME, null,Constants.BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE TB_LOGIN(");
        query.append("ID_LOGIN INTEGER PRIMARY KEY AUTOINCREMENT,");
        query.append("USUARIO TEXT(15) NOT NULL,");
        query.append("SENHA TEXT(15) NOT NULL)");

        db.execSQL(query.toString());


        query = new StringBuilder();
        query.append("CREATE TABLE TB_PESSOA( ");
        query.append(" ID_PESSOA INTEGER PRIMARY KEY AUTOINCREMENT,");
        query.append(" NOME TEXT(30) NOT NULL,");
        query.append(" ENDERECO TEXT(50),");
        query.append(" CPF TEXT(14),");
        query.append(" CNPJ TEXT(14),");
        query.append(" SEXO INTEGER(1) NOT NULL,");
        query.append(" PROFISSAO INTEGER(3) NOT NULL,");
        query.append(" DT_NASC INTEGER NOT NULL)");

        db.execSQL(query.toString());
        popularDB(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void popularDB(SQLiteDatabase db) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO TB_LOGIN(USUARIO, SENHA) VALUES (?,?)");

//        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query.toString(), new String[] {"admin", "admin"});
    }

    public void listaLogin(){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("TB_LOGIN", null, "id_login = ? and usuario = ?", new String[] {"1", "admin"}, null, null, "USUARIO");
        while (cursor.moveToNext()){
            Log.d("NOME DE USUÁRIO", cursor.getString(1));
            WrapperLog.info("ID DE USUÁRIO "+ String.valueOf(cursor.getInt(cursor.getColumnIndex("ID_LOGIN"))
                           +" NOME DE USUÁRIO "+ cursor.getString(cursor.getColumnIndex("USUARIO"))
                           + " SENHA DE USUÁRIO "+ cursor.getString(cursor.getColumnIndex("SENHA"))));
        }
    }

    public void adicionarLogin(String login, String senha){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USUARIO", login);
        contentValues.put("SENHA", senha);

        db.insert("TB_LOGIN",null, contentValues);
    }

    public void updateLogin(String login, String senha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USUARIO", login);
        contentValues.put("SENHA", senha);

        db.update("TB_LOGIN", contentValues, "id_login > 1", null);
    }

    public void deleteLogin(String login, String senha) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("TB_LOGIN", "usuario = ? or senha = ?", new String[] {login, senha});
    }

}
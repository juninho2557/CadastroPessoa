package com.wfghc.cadastrodepessoa.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.wfghc.cadastrodepessoa.entidade.enums.ProfissaoEnum;
import com.wfghc.cadastrodepessoa.entidade.enums.SexoEnum;
import com.wfghc.cadastrodepessoa.entidade.enums.TipoPessoaEnum;
import com.wfghc.cadastrodepessoa.entidade.model.Pessoa;
import com.wfghc.cadastrodepessoa.util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wesleygoes on 05/01/17.
 */

public class PessoaRepository extends SQLiteOpenHelper {

    public PessoaRepository(Context context) {
        super(context, Constants.BD_NOME, null, Constants.BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXIST TB_PESSOA( ");
        query.append(" ID_PESSOA INTEGER PRIMARY KEY AUTOINCREMENT,");
        query.append(" NOME TEXT(30) NOT NULL,");
        query.append(" ENDERECO TEXT(50),");
        query.append(" CPF TEXT(14),");
        query.append(" CNPJ TEXT(14),");
        query.append(" SEXO INTEGER(1) NOT NULL,");
        query.append(" PROFISSAO INTEGER(3) NOT NULL,");
        query.append(" DT_NASC INTEGER NOT NULL)");

        db.execSQL(query.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void salvarPessoa(Pessoa pessoa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = getContentValuesPessoa(pessoa);
        db.insert("TB_PESSOA", null, contentValues);
    }

    public void editarPessoa(Pessoa pessoa){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = getContentValuesPessoa(pessoa);
        db.update("TB_PESSOA",contentValues,"ID_PESSOA = ?",new String[]{String.valueOf(pessoa.getIdPessoa())});
    }

    @NonNull
    private ContentValues getContentValuesPessoa(Pessoa pessoa) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("NOME", pessoa.getNome());
        contentValues.put("ENDERECO", pessoa.getEndereco());
        switch (pessoa.getTipoPessoa()) {
            case FISICA:
                contentValues.put("CPF", pessoa.getCpfCnepj());
                break;
            case JURIDICA:
                contentValues.put("CNPJ", pessoa.getCpfCnepj());
                break;
        }
        contentValues.put("SEXO", pessoa.getSexo().ordinal());
        contentValues.put("PROFISSAO", pessoa.getProfissao().ordinal());
        contentValues.put("DT_NASC", pessoa.getDtNascimento().getTime());
        return contentValues;
    }


    /*
        Listar Pessoas
     */
    public List<Pessoa> listaPessoa(){
        List<Pessoa> lista = new ArrayList<Pessoa>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("TB_PESSOA",null,null,null,null,null,"NOME");
        while (cursor.moveToNext()){
            Pessoa pessoa = new Pessoa();
            setPessoaFromCursor(cursor, pessoa);
            lista.add(pessoa);
        }
        return lista;
    }


    public Pessoa consultarPessoaPorId(int idPessoa){
        Pessoa pessoa = new Pessoa();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("TB_PESSOA", null, "ID_PESSOA = ?", new String[]{String.valueOf(idPessoa)}, null, null, "NOME");

        if (cursor.moveToNext()){
            setPessoaFromCursor(cursor, pessoa);
        }
        return pessoa;
    }


    private void setPessoaFromCursor(Cursor cursor, Pessoa pessoa) {
        pessoa.setIdPessoa(cursor.getInt(cursor.getColumnIndex("ID_PESSOA")));
        pessoa.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
        pessoa.setEndereco(cursor.getString(cursor.getColumnIndex("ENDERECO")));
        String cpf = cursor.getString(cursor.getColumnIndex("CPF"));
        String cnpj = cursor.getString(cursor.getColumnIndex("CNPJ"));
        if(cpf != null && "".equals(cpf)){
            pessoa.setTipoPessoa(TipoPessoaEnum.FISICA);
            pessoa.setCpfCnepj(cpf);
        }else{
            pessoa.setTipoPessoa(TipoPessoaEnum.JURIDICA);
            pessoa.setCpfCnepj(cnpj);
        }
        int sexo =cursor.getInt(cursor.getColumnIndex("SEXO"));
        pessoa.setSexo(SexoEnum.getSexo(sexo));

        int profissao =cursor.getInt(cursor.getColumnIndex("PROFISSAO"));
        pessoa.setProfissao(ProfissaoEnum.getProfissao(profissao));

        long time =cursor.getLong(cursor.getColumnIndex("DT_NASC"));
        Date dtNasc = new Date();
        dtNasc.setTime(time);
        pessoa.setDtNascimento(dtNasc);

    }

    public void removerPessoaPorId(int idPessoa) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("TB_PESSOA","ID_PESSOA = ?", new String[]{String.valueOf(idPessoa)});
    }
}

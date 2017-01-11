package com.wfghc.cadastrodepessoa.entidade.model;


import com.wfghc.cadastrodepessoa.entidade.enums.ProfissaoEnum;
import com.wfghc.cadastrodepessoa.entidade.enums.SexoEnum;
import com.wfghc.cadastrodepessoa.entidade.enums.TipoPessoaEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wesleygoes on 06/01/17.
 */

public class Pessoa implements Serializable {
    private int idPessoa;
    private String nome;
    private String endereco;
    private String cpfCnepj;
    private TipoPessoaEnum tipoPessoa;
    private SexoEnum sexo;
    private ProfissaoEnum profissao;
    private Date dtNascimento;

    public String getCpfCnepj() {
        return cpfCnepj;
    }

    public void setCpfCnepj(String cpfCnepj) {
        this.cpfCnepj = cpfCnepj;
    }

    public Date getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ProfissaoEnum getProfissao() {
        return profissao;
    }

    public void setProfissao(ProfissaoEnum profissao) {
        this.profissao = profissao;
    }

    public SexoEnum getSexo() {
        return sexo;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }

    public TipoPessoaEnum getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoaEnum tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    @Override
    public String toString() {
        return "\nPessoa{" +
                "\ncpfCnepj='" + cpfCnepj + '\'' +
                ", \nnome='" + nome + '\'' +
                ", \nendereco='" + endereco + '\'' +
                ", \ntipoPessoa=" + tipoPessoa +
                ", \nsexo=" + sexo +
                ", \nprofissao=" + profissao +
                ", \ndtNascimento=" + dtNascimento +
                '}';
    }
}

package com.wfghc.cadastrodepessoa.entidade.enums;

/**
 * Created by wesleygoes on 06/01/17.
 */

public enum TipoPessoaEnum {

    FISICA("Física"),
    JURIDICA("Jurídica");

    private TipoPessoaEnum(String pessoa) {
        this.pessoa = pessoa;
    }

    private String pessoa;

    public String getPessoa() {
        return pessoa;
    }
}

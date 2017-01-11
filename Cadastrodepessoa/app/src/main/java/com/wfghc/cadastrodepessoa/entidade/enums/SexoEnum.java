package com.wfghc.cadastrodepessoa.entidade.enums;

/**
 * Created by wesleygoes on 06/01/17.
 */

public enum SexoEnum {
    MASCULINO  ("Masculino"),
    FEMININO   ("Feminino");

    private String descricao;

    SexoEnum(String descricao) {
        this.descricao = descricao;
    }

    public static SexoEnum getSexo(int pos){
        for (SexoEnum sexo : SexoEnum.values()) {
            if (sexo.ordinal() == pos){
                return sexo;
            }
        }
        return null;
    }

    public String getDescricao() {
        return descricao;
    }
}
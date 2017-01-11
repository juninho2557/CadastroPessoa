package com.wfghc.cadastrodepessoa.entidade.enums;

/**
 * Created by wesleygoes on 29/12/16.
 */

public enum ProfissaoEnum {
    ARQUITETO("Arquiteto"),
    PEDDREIRO("Pedreiro"),
    PROFESSOR("Professor"),
    DESENVOLVEDOR("Desenvolvedor"),
    ANALISTA("Analista de Sistemas"),
    ENGENHEIRO("Engenheiro de Software"),
    ZELADOR("Zelador"),
    CARTEIRO("Carteiro");

    private ProfissaoEnum(String descricao) {
        this.descricao = descricao;
    }

    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public static ProfissaoEnum getProfissao(int pos){
        for (ProfissaoEnum p : ProfissaoEnum.values()) {
            if(p.ordinal() == pos){
                return p;
            }
        }
        return null;
    }
}

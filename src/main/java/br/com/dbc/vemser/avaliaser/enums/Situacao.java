package br.com.dbc.vemser.avaliaser.enums;

public enum Situacao {
    ALOCADO("L"),
    DISPONIVEL("D"),
    RESERVADO("R"),
    ABERTO("A"),
    FECHADO("F"),

    ATIVO("S"),
    INATIVO("N");


    private final String situacao;

    Situacao(String situacao) {
        this.situacao = situacao;
    }

    public String getSituacao() {
        return this.situacao;
    }
}

package br.com.dbc.vemser.avaliaser.enums;

public enum Situacao {
    ALOCADO("A"),
    DISPONIVEL("D"),
    RESERVADO("R"),
    ABERTO("A"),
    FECHADO("F");

    private final String situacao;

    Situacao(String situacao) {
        this.situacao = situacao;
    }

    public String getSituacao() {
        return this.situacao;
    }
}

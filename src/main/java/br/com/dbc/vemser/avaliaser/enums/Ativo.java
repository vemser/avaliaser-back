package br.com.dbc.vemser.avaliaser.enums;

public enum Ativo {
    N("N"),
    S("S"),
    ABERTO("A"),
    FECHADO("F");

    private final String descricao;

    Ativo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }
}

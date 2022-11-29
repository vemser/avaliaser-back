package br.com.dbc.vemser.avaliaser.enums;

public enum Ativo {
    N('N'),
    S('S');

    private final char descricao;

    Ativo(char descricao) {
        this.descricao = descricao;
    }
    public char getDescricao() {
        return this.descricao;
    }
}

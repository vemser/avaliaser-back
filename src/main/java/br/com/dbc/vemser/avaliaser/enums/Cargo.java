package br.com.dbc.vemser.avaliaser.enums;

public enum Cargo {
    ADMIN(1),
    GESTOR(2),
    INSTRUTOR(3);

    private final Integer descricao;

    Cargo(Integer descricao) {
        this.descricao = descricao;
    }

}

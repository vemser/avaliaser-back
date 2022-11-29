package br.com.dbc.vemser.avaliaser.enums;

public enum Cargo {
    ADMIN(1, "Admin"),
    GESTOR(2, "Gestor de Pessoas"),
    INSTRUTOR(3, "Instrutor");

    private final Integer id;
    private final String descricao;

    Cargo(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getInteger() {
        return this.id;
    }
    public String getDescricao(){return this.descricao;}

}

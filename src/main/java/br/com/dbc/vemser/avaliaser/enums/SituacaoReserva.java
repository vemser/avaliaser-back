package br.com.dbc.vemser.avaliaser.enums;

public enum SituacaoReserva {
    ALOCADO("Alocado"),
    DISPONIVEL("Disponivel"),
    RESERVADO("Reservado"),
    CANCELADO("Cancelado");


    private final String situacao;

    SituacaoReserva(String situacao) {
        this.situacao = situacao;
    }

    public String getSituacao() {
        return this.situacao;
    }
}

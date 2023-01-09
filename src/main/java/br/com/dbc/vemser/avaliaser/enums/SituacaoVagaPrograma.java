package br.com.dbc.vemser.avaliaser.enums;

public enum SituacaoVagaPrograma {

    ABERTO("A"),
    FECHADO("F");


    private final String situacao;

    SituacaoVagaPrograma(String situacao) {
        this.situacao = situacao;
    }

    public String getSituacao() {
        return this.situacao;
    }
}

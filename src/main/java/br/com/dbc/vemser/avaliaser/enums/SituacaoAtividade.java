package br.com.dbc.vemser.avaliaser.enums;

public enum SituacaoAtividade {

    ENTREGUE("E"),
    PENDENTE("P");


    private final String situacao;

    SituacaoAtividade(String situacao) {
        this.situacao = situacao;
    }

    public String getSituacao() {
        return this.situacao;
    }
}

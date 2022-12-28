package br.com.dbc.vemser.avaliaser.enums;

import java.util.Arrays;

public enum AtividadeStatus {
    PENDENTE(0), CONCLUIDA(1);

    private Integer atividadeStatus;

    AtividadeStatus(Integer atividadeStatus) {
        this.atividadeStatus = atividadeStatus;
    }

    public Integer getAtividadeStatus() {
        return atividadeStatus;
    }

    public static AtividadeStatus ofStatus(Integer atividadeStatus) {
        return Arrays.stream(AtividadeStatus.values())
                .filter(st -> st.getAtividadeStatus().equals(atividadeStatus))
                .findFirst()
                .get();
    }
}

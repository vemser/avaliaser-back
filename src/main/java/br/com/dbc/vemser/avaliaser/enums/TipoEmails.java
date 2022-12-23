package br.com.dbc.vemser.avaliaser.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoEmails {
    CREATE("Cadastro realizado com sucesso!"),
    REC_SENHA("Recupere sua senha!"),
    UPDATE("Alteração de Dados Cadastrais!");
    private final String descricao;

}

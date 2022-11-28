package br.com.dbc.vemser.avaliaser.services.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoEmails {
    CREATE("Cadastro realizado com sucesso!"),

    REC_SENHA("Recupere sua senha!"),
    UPDATE("Alteração de Dados Cadastrais!"),
    DELETE("Acesso da conta encerrado!"),
    END_CREATE("Endereço Cadastrado com Sucesso!"),
    END_UPDATE("Endereço alterado com sucesso!"),
    END_DELETE("Endereço removido com sucesso!"),
    AVALIACAO("Sua Avaliação foi postada com sucesso!");
    private final String descricao;

}

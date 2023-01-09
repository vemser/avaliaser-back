package br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno;

import lombok.Data;

import java.util.List;

@Data
public class AlunoFiltroDTO {
    private Integer idPrograma;

    private List<Integer> idTrilhas;

}

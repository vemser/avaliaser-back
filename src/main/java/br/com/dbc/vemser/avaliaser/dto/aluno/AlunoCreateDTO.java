package br.com.dbc.vemser.avaliaser.dto.aluno;

import br.com.dbc.vemser.avaliaser.enums.Stack;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlunoCreateDTO {
    @NotNull
    @NotBlank
    @Schema(example = "Alexandre Bispo")
    private String nome;
    @NotNull
    @NotBlank
    @JsonIgnore
    @Schema(example = "BACKEND")
    private Stack stack;
    @NotNull
    @NotBlank
    @Schema(example = "alexandre.bispo@dbccompany.com.br")
    private String email;

}

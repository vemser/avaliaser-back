package br.com.dbc.vemser.avaliaser.dto.avalaliaser.aluno;

import br.com.dbc.vemser.avaliaser.enums.SituacaoReserva;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlunoCreateDTO {
    @NotNull(message = "Nome não pode ser nulo.")
    @NotBlank(message = "Nome não pode ficar em branco.")
    @Pattern(regexp = "[\\s[a-zA-Zá-úÁ-Ú]*]{0,}", message = "Não permitido números e caracteres especiais.")
    @Schema(example = "Alexandre Bispo")
    private String nome;

    @NotNull(message = "Telefone não pode ser nulo.")
    @NotBlank(message = "Telefone não pode ficar em branco.")
    @Schema(example = "(11)92345-1234 ou (11)2234-1234")
    @Pattern(regexp = "^\\([1-9]{2}\\)(?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}", message = "Permitido apenas Telefone Fixo ou Celular.")
    private String telefone;

    @NotNull(message = "Cidade não pode ficar nulo.")
    @NotBlank(message = "Cidade não pode ficar em branco.")
    @Schema(example = "São Paulo")
    @Pattern(regexp = "[\\s[a-zA-Zá-úÁ-Ú]*]{0,}", message = "Não é permitido números e caracteres especiais.")
    private String cidade;

    @NotNull(message = "Estado não pode ficar nulo.")
    @NotBlank(message = "Estado não pode ficar em branco.")
    @Schema(example = "São Paulo")
    @Pattern(regexp = "[\\s[a-zA-Zá-úÁ-Ú]*]{0,}", message = "Não é permitido números e caracteres especiais.")
    private String estado;

    @NotNull(message = "Email não pode ser nulo.")
    @Email(message = "Email inválido.", regexp = "^[A-Za-z0-9._%+-]+@dbccompany.com.br$")
    @Schema(example = "alexandre.bispo@dbccompany.com.br")
    private String email;

    @NotNull(message = "Situação não pode ficar nulo.")
    @Schema(example = "DISPONIVEL")
    private SituacaoReserva situacao;

    @NotNull(message = "Descrição não pode ser nulo.")
    @NotBlank(message = "Descrição não pode ficar em branco.")
    @Schema(example = "Descrição breve sobre o Aluno.")
    private String descricao;

    @NotNull(message = "Trilha não pode ficar nulo.")
    @Schema(example = "1")
    private Integer idTrilha;

    @NotNull(message = "Programa não pode ficar nulo.")
    @Schema(example = "1")
    private Integer idPrograma;

    @Schema(example = "[1, 2, 3]")
    private List<Integer> tecnologias;

}

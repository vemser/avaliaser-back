package br.com.dbc.vemser.avaliaser.factory;

import br.com.dbc.vemser.avaliaser.dto.aluno.AlunoCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.aluno.AlunoDTO;
import br.com.dbc.vemser.avaliaser.entities.AlunoEntity;
import br.com.dbc.vemser.avaliaser.enums.Ativo;
import br.com.dbc.vemser.avaliaser.enums.Stack;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class AlunoFactory {
    public static AlunoEntity getAlunoEntity() {

        AlunoEntity aluno = new AlunoEntity();
        aluno.setIdAluno(1);
        aluno.setNome("Paulo Sergio");
        aluno.setEmail("paulo.sergio@dbccompany.com");
        aluno.setStack(Stack.BACKEND);
        aluno.setAtivo(Ativo.S);
        aluno.setFoto(new byte[10 * 1024]);


        return aluno;
    }

    public static AlunoCreateDTO getAlunoCreateDTO() {
        AlunoCreateDTO alunoCreateDTO = new AlunoCreateDTO();
        alunoCreateDTO.setNome("Paulo Sergio");
        alunoCreateDTO.setEmail("paulo.sergio@dbccompany.com");
        return alunoCreateDTO;
    }

    public static AlunoDTO getAlunoDTO() throws IOException {
        byte[] imagemBytes = new byte[10 * 1024];
        MultipartFile imagem = new MockMultipartFile("imagem", imagemBytes);
        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setIdAluno(1);
        alunoDTO.setNome("Paulo Sergio");
        alunoDTO.setStack(Stack.BACKEND);
        alunoDTO.setFoto(imagem.getBytes());
        return alunoDTO;
    }

}

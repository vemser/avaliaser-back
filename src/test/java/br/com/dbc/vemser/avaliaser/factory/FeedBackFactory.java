package br.com.dbc.vemser.avaliaser.factory;

import br.com.dbc.vemser.avaliaser.dto.feedback.EditarFeedBackDTO;
import br.com.dbc.vemser.avaliaser.dto.feedback.FeedBackCreateDTO;
import br.com.dbc.vemser.avaliaser.dto.feedback.FeedBackDTO;
import br.com.dbc.vemser.avaliaser.entities.FeedBackEntity;
import br.com.dbc.vemser.avaliaser.enums.Tipo;

import java.io.IOException;

public class FeedBackFactory {
    public static FeedBackEntity getFeedBack() {

        FeedBackEntity feedBackEntity = new FeedBackEntity();
        feedBackEntity.setIdFeedBack(1);
        feedBackEntity.setIdAluno(1);
        feedBackEntity.setIdUsuario(1);
        feedBackEntity.setTipo(Tipo.POSITIVO);
        feedBackEntity.setDescricao("Texto para teste!");

        return feedBackEntity;
    }

    public static FeedBackCreateDTO getFeedBackCreateDTO(){
        FeedBackCreateDTO feedBackCreateDTO = new FeedBackCreateDTO();
        feedBackCreateDTO.setDescricao("Texto para teste!");
        feedBackCreateDTO.setTipo(Tipo.POSITIVO);
        feedBackCreateDTO.setIdAluno(1);

        return feedBackCreateDTO;
    }

    public static EditarFeedBackDTO getEditarFeedBack(){
        EditarFeedBackDTO editarFeedBackDTO = new EditarFeedBackDTO();
        editarFeedBackDTO.setDescricao("Texto para teste!");
        editarFeedBackDTO.setTipo(Tipo.POSITIVO);
        editarFeedBackDTO.setIdAluno(1);

        return editarFeedBackDTO;
    }

    public static FeedBackDTO getFeedBackDTO() throws IOException {

        FeedBackDTO feedBackDTO = new FeedBackDTO();
        feedBackDTO.setIdFeedBack(1);
        feedBackDTO.setIdUsuario(1);
        feedBackDTO.setIdAluno(1);
        feedBackDTO.setDescricao("Paulo Sergio");
        feedBackDTO.setTipo(Tipo.POSITIVO);
        return feedBackDTO;
    }
}

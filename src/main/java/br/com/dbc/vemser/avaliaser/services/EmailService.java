package br.com.dbc.vemser.avaliaser.services;

import br.com.dbc.vemser.avaliaser.dto.recuperacao.UsuarioRecuperacaoDTO;
import br.com.dbc.vemser.avaliaser.services.enums.TipoEmails;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {
    private final freemarker.template.Configuration fmConfiguration;
    @Value("${spring.mail.username}")
    private String from;
    private static final String TO = "moises.noah@dbccompany.com.br";
    private final JavaMailSender emailSender;

    public void sendSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(TO);
        message.setSubject("Assunto");
        message.setText("Teste \n minha mensagem \n\nAtt,\nSistema.");
        emailSender.send(message);
    }

    public void sendWithAttachment() throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                true);
        helper.setFrom(from);
        helper.setTo(TO);
        helper.setSubject("Subject");
        helper.setText("Teste\n minha mensagem \n\nAtt,\nSistema.");
        File file1 = new File("imagem.jpg");
        FileSystemResource file
                = new FileSystemResource(file1);
        helper.addAttachment(file1.getName(), file);
        emailSender.send(message);
    }

    public void sendEmail(UsuarioRecuperacaoDTO usuarioDTO, TipoEmails tipoEmails) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(usuarioDTO.getEmail());
            mimeMessageHelper.setSubject(tipoEmails.getDescricao());
            mimeMessageHelper.setText(geContentFromTemplate(usuarioDTO, tipoEmails), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public void sendEmailRecuperacao(UsuarioRecuperacaoDTO usuarioDTO, TipoEmails tipoEmails, String token) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(usuarioDTO.getEmail());
            mimeMessageHelper.setSubject(tipoEmails.getDescricao());
            mimeMessageHelper.setText(geContentFromTemplateRecuperacao(usuarioDTO, tipoEmails, token), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public String geContentFromTemplateRecuperacao(UsuarioRecuperacaoDTO usuarioDTO, TipoEmails tipoEmails, String token) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", usuarioDTO.getNome());
        dados.put("email", from);
        dados.put("texto1", "Solicitação de recuperação de senha feita com sucesso!" );
        dados.put("texto2", "Seu token de recuperação é: " + token);
        dados.put("texto3", "Após acessar o link acima, basta realizar o cadastro de sua nova senha!");

        Template template = fmConfiguration.getTemplate("email-template.html");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }

    public String geContentFromTemplate(UsuarioRecuperacaoDTO usuarioDTO, TipoEmails tipoEmails) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", usuarioDTO.getNome());
        dados.put("email", from);

        if (tipoEmails.equals(TipoEmails.CREATE)) {
            dados.put("texto1", "Estamos felizes em ter você em nosso sistema!");
            dados.put("texto2", "Seu cadastro foi realizado com sucesso, seu Login é: " + usuarioDTO.getEmail() + "!");
            dados.put("texto3", "Sua senha de acesso temporária é: " + usuarioDTO.getSenha());

        } else if (tipoEmails.equals(TipoEmails.UPDATE)) {
            dados.put("texto1", "Você atualizou seus dados com sucesso! ");
            dados.put("texto2", "--------------------------------------");

        } else if (tipoEmails.equals(TipoEmails.DELETE)) {
            dados.put("texto1", "Você perdeu o acesso ao nosso sistema!!");
            dados.put("texto2", "--------------------------------------");

        } else if (tipoEmails.equals(TipoEmails.END_CREATE)) {
            dados.put("texto1", "O cadastro do seu ENDEREÇO foi realizado com sucesso!");
            dados.put("texto2", "Obrigado!");

        } else if (tipoEmails.equals(TipoEmails.END_UPDATE)) {
            dados.put("texto1", "Você atualizou os dados do seu ENDEREÇO com sucesso!!");
            dados.put("texto2", "--------------------------------------");

        } else if (tipoEmails.equals(TipoEmails.END_DELETE)) {
            dados.put("texto1", "Seus dados de Endereço foram excluidos com sucesso!!");
            dados.put("texto2", "--------------------------------------");

        } else if (tipoEmails.equals(TipoEmails.AVALIACAO)) {
            dados.put("texto1", "Parabéns! Sua avaliação foi postada com sucesso!Obrigado pela preferencia!");
            dados.put("texto2", "--------------------------------------");
        }
        Template template = fmConfiguration.getTemplate("email-template.html");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }
}

package br.com.dbc.vemser.avaliaser.services;

import br.com.dbc.vemser.avaliaser.dto.recuperacao.UsuarioRecuperacaoDTO;
import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.avaliaser.enums.TipoEmails;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {
    private final freemarker.template.Configuration fmConfiguration;
    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender emailSender;

    public void sendEmail(UsuarioRecuperacaoDTO usuarioDTO, TipoEmails tipoEmails) throws RegraDeNegocioException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(usuarioDTO.getEmail());
            mimeMessageHelper.setSubject(tipoEmails.getDescricao());
            mimeMessageHelper.setText(geContentFromTemplate(usuarioDTO, tipoEmails), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            throw new RegraDeNegocioException("Não foi possivel enviar o email!");
        }
    }
    public void sendEmailRecuperacao(UsuarioRecuperacaoDTO usuarioDTO, TipoEmails tipoEmails, String token) throws RegraDeNegocioException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(usuarioDTO.getEmail());
            mimeMessageHelper.setSubject(tipoEmails.getDescricao());
            mimeMessageHelper.setText(geContentFromTemplateRecuperacao(usuarioDTO, token), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            throw new RegraDeNegocioException("Não foi possivel enviar o email!");
        }
    }
    public String geContentFromTemplateRecuperacao(UsuarioRecuperacaoDTO usuarioDTO, String token) throws IOException, TemplateException, RegraDeNegocioException {
        try {
            Map<String, Object> dados = new HashMap<>();
            dados.put("nome", usuarioDTO.getNome());
            dados.put("email", from);
            dados.put("linkComToken", "https://avaliaser-front-flame.vercel.app/recuperar-senha?token=" + token);
            Template template = fmConfiguration.getTemplate("index.html");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
            return html;
        }catch (IOException | TemplateException e){
            e.getMessage();
            throw new RegraDeNegocioException("Não foi possivel enviar o email!");
        }
    }
    public String geContentFromTemplate(UsuarioRecuperacaoDTO usuarioDTO, TipoEmails tipoEmails) throws IOException, TemplateException, RegraDeNegocioException {
        try {
            Map<String, Object> dados = new HashMap<>();
            dados.put("nome", usuarioDTO.getNome());
            dados.put("email", from);
            if (tipoEmails.equals(TipoEmails.CREATE)) {
                dados.put("texto1", "A DBC TE DESEJA AS MAIS CALOROSAS BOAS VINDAS!");
                dados.put("texto2", "Seu cadastro foi realizado com sucesso, seu Login é: " + usuarioDTO.getEmail() + "!");
                dados.put("texto3", "Sua senha de acesso temporária é: " + usuarioDTO.getSenha());
            } else if (tipoEmails.equals(TipoEmails.UPDATE)) {
                dados.put("texto1", "Você atualizou seus dados com sucesso! ");
                dados.put("texto2", "--------------------------------------");}
            Template template = fmConfiguration.getTemplate("email-template.html");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
            return html;
        }catch (IOException | TemplateException e){
            e.getMessage();
            throw new RegraDeNegocioException("Não foi possivel enviar o email!");
        }
    }
}

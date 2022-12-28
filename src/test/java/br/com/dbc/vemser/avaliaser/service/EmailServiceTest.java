//package br.com.dbc.vemser.avaliaser.service;
//
//import br.com.dbc.vemser.avaliaser.dto.avalaliaser.recuperacao.UsuarioRecuperacaoDTO;
//import br.com.dbc.vemser.avaliaser.enums.TipoEmails;
//import br.com.dbc.vemser.avaliaser.exceptions.RegraDeNegocioException;
//import br.com.dbc.vemser.avaliaser.factory.UsuarioFactory;
//import br.com.dbc.vemser.avaliaser.services.avaliaser.EmailService;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import javax.mail.internet.MimeMessage;
//import java.io.IOException;
//import java.io.Reader;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.Assert.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class EmailServiceTest {
//    @InjectMocks
//    private EmailService emailService;
//    @Mock
//    private freemarker.template.Configuration fmConfiguration;
//    @Mock
//    private JavaMailSender emailSender;
//    @Mock
//    private MimeMessage mimeMessage;
//    final String from = "noahtester@email.com.br";
//    final String token = "tokenImensoEextensoCheiodeAleatoriedades";
//
//    @Before
//    public void init() {
//        ReflectionTestUtils.setField(emailService, "from", from);
//    }
//
//    @Test
//    public void deveEnviarEmailDeBoasVindasEcredenciais() throws IOException, RegraDeNegocioException {
//        Template template = new Template("template.html", Reader.nullReader());
//        UsuarioRecuperacaoDTO usuarioRecuperacaoDTO = UsuarioFactory.getUsuarioRecuperacao();
//
//        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
//        when(fmConfiguration.getTemplate(any())).thenReturn(template);
//
//        emailService.sendEmail(usuarioRecuperacaoDTO, TipoEmails.CREATE);
//
//        verify(emailSender).send((MimeMessage) any());
//    }
//
//    @Test
//    public void deveEnviarEmaildeRecuperacao() throws IOException, RegraDeNegocioException {
//        Template template = new Template("template.html", Reader.nullReader());
//        UsuarioRecuperacaoDTO usuarioRecuperacaoDTO = UsuarioFactory.getUsuarioRecuperacao();
//
//
//        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
//        when(fmConfiguration.getTemplate(any())).thenReturn(template);
//
//        emailService.sendEmailRecuperacao(usuarioRecuperacaoDTO, TipoEmails.REC_SENHA, token);
//
//        verify(emailSender).send((MimeMessage) any());
//    }
//
//
//    @Test
//    public void deveTestarGetContentFromTemplate() throws IOException, TemplateException {
//        Template template = new Template("template.html", Reader.nullReader());
//        Map<String, Object> dados = new HashMap<>();
//        dados.put("nome", "nome");
//        dados.put("email", from);
//        dados.put("texto1", "nome");
//        dados.put("texto2", "nome");
//        dados.put("texto3", "nome");
//
//        UsuarioRecuperacaoDTO usuarioRecuperacaoDTO = UsuarioFactory.getUsuarioRecuperacao();
//
//        when(fmConfiguration.getTemplate(anyString())).thenReturn(template);
//
//        String content = emailService.getContentFromTemplate(usuarioRecuperacaoDTO);
//
//        assertNotNull(content);
//    }
//
//    @Test
//    public void deveTestarGetContentFromTemplateUpdate() throws IOException, TemplateException {
//        Template template = new Template("template.html", Reader.nullReader());
//        Map<String, Object> dados = new HashMap<>();
//        dados.put("nome", "nome");
//        dados.put("email", from);
//        dados.put("texto1", "nome");
//        dados.put("texto2", "nome");
//        dados.put("texto3", "nome");
//
//        UsuarioRecuperacaoDTO usuarioRecuperacaoDTO = UsuarioFactory.getUsuarioRecuperacao();
//
//        when(fmConfiguration.getTemplate(anyString())).thenReturn(template);
//
//        String content = emailService.getContentFromTemplate(usuarioRecuperacaoDTO);
//
//        assertNotNull(content);
//    }
//
//    @Test
//    public void deveTestarGetContentFromTemplateRecuperacao() throws IOException, TemplateException {
//        Template template = new Template("template.html", Reader.nullReader());
//        Map<String, Object> dados = new HashMap<>();
//        dados.put("nome", "nome");
//        dados.put("email", from);
//        dados.put("linkComToken", token);
//
//        UsuarioRecuperacaoDTO usuarioRecuperacaoDTO = UsuarioFactory.getUsuarioRecuperacao();
//
//        when(fmConfiguration.getTemplate(anyString())).thenReturn(template);
//
//        String content = emailService.getContentFromTemplateRecuperacao(usuarioRecuperacaoDTO, token);
//
//        assertNotNull(content);
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void deveTestarSendEmailComIOException() throws IOException, RegraDeNegocioException {
//
//        UsuarioRecuperacaoDTO usuarioRecuperacaoDTO = UsuarioFactory.getUsuarioRecuperacao();
//
//        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
//        doThrow(new IOException()).when(fmConfiguration).getTemplate(anyString());
//
//        emailService.sendEmail(usuarioRecuperacaoDTO, TipoEmails.CREATE);
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void deveTestarSendEmailUpdateComIOException() throws IOException, RegraDeNegocioException {
//
//        UsuarioRecuperacaoDTO usuarioRecuperacaoDTO = UsuarioFactory.getUsuarioRecuperacao();
//
//        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
//        doThrow(new IOException()).when(fmConfiguration).getTemplate(anyString());
//
//        emailService.sendEmail(usuarioRecuperacaoDTO, TipoEmails.UPDATE);
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void deveLancarExcecaoAoExecutarSendEmailRecuperacaoComIOException() throws IOException, RegraDeNegocioException {
//
//        UsuarioRecuperacaoDTO usuarioRecuperacaoDTO = UsuarioFactory.getUsuarioRecuperacao();
//
//        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
//        doThrow(new IOException()).when(fmConfiguration).getTemplate(anyString());
//
//        emailService.sendEmailRecuperacao(usuarioRecuperacaoDTO, TipoEmails.REC_SENHA, token);
//    }
//
//
//}

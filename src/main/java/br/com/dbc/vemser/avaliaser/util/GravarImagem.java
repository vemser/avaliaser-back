//package br.com.dbc.vemser.avaliaser.util;
//
//import java.io.IOException;
//
//import org.apache.tomcat.util.codec.binary.Base64;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.ikytus.fp.model.AbstractEntity;
//import com.ikytus.fp.service.AbstractService;
//
//@Service
//public class GravarImagem {
//
//    public String imagemString;
//
//    public static String encodeImagem(byte[] imageByteArray) {
//        return Base64.encodeBase64String(imageByteArray);
//    }
//
//    /*este método realiza a conversão da imagem recebido e a
//      devolve como uma string.
//    */
//    public String gravarImagemBase64(MultipartFile file){
//        try{
//            imagemString = encodeImagem(file.getBytes());
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        return imagemString;
//    }
//
//    /*Este será o método que iremos utilizar em nosso controller para manipular
//      a imagem recebido e gravar no campo adequado da tabela Produto
//      no banco de dados.
//      Como esta médoto poderá ser utilizado para gravar imagens em qualquer classe
//      do nosso modelo, em vez de receber objetos concretos ela recebe um
//      AbstractService e um AbstractEntity.
//    */
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    public String gravaImagemBase64Service(MultipartFile file, AbstractService service, AbstractEntity entidade){
//
//        /* testa se foi recebida uma imagem:
//           (1)Se não houver, salva a entidade no BD utilizando o primeiro médoto
//              Salvar do Service;
//           (2)Tendo recebido uma imagem, converte em string, atribui ao campo
//              apropriado e salva a entidade no BD utilizando o segundo método
//              salvar.
//        */
//        if(gravarImagemBase64(file).isEmpty()) {
//            service.salvar(entidade); //(1)
//        }else {
//            imagemString = "data:image/png;base64," + imagemString;
//            service.salvar(entidade,imagemString); //(2)
//        }
//
//        // devolve a imagem em string para ser utilizada em outro local
//        return gravarImagemBase64(file);
//    }
//}
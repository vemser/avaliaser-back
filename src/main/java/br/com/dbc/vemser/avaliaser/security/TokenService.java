package br.com.dbc.vemser.avaliaser.security;

import br.com.dbc.vemser.avaliaser.entities.CargoEntity;
import br.com.dbc.vemser.avaliaser.entities.UsuarioEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private static final String CARGO = "CARGOS";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.login}")
    private Integer login;

    public String getToken(UsuarioEntity usuario){

        Date dataAtual = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Date dataExpiracao = Date.from(LocalDateTime.now().plusDays(login).atZone(ZoneId.systemDefault()).toInstant());

        List<String> cargosUsuario = usuario.getCargos().stream()
                .map(CargoEntity::getAuthority)
                .toList();

        String token = Jwts.builder()
                .setIssuer("avaliaser")
                .claim(Claims.ID, usuario.getIdUsuario().toString())
                .claim(CARGO, cargosUsuario)
                .setIssuedAt(dataAtual)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return token;
    }
    public UsernamePasswordAuthenticationToken isValid(String token) {
        if(token == null){
            return null;
        }

        token = token.replace("Bearer ", "");

        Claims keys = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        String idUsuario = keys.get(Claims.ID, String.class);

        List<String> cargosUsuario = keys.get(CARGO, List.class);

        List<SimpleGrantedAuthority> listaCargosUsuario = cargosUsuario.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        UsernamePasswordAuthenticationToken userPassAuthToken =
                new UsernamePasswordAuthenticationToken(idUsuario, null, listaCargosUsuario);

        return userPassAuthToken;
    }
}

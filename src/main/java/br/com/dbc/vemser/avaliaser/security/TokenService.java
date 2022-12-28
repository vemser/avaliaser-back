package br.com.dbc.vemser.avaliaser.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private static final String CARGO = "CARGOS";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.login}")
    private Integer login;

    @Value("${jwt.expiration.recuperacao}")
    private Integer recuperacao;

    public UsernamePasswordAuthenticationToken isValid(String token) {
        if (token == null) {
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

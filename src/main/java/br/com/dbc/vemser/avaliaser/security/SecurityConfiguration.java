package br.com.dbc.vemser.avaliaser.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final TokenService tokenService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and()
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests((authz) ->
                        //autorizações -> auth
                        authz.antMatchers("/").permitAll()
//                                .antMatchers(HttpMethod.DELETE,"/teste/delete/aluno/{idAluno}").hasAnyRole("GESTOR", "INSTRUTOR")
//                                .antMatchers(HttpMethod.DELETE,"/teste/delete/{idUsuario}").hasAnyRole("ADMIN")
//
//                                .antMatchers(HttpMethod.PUT, "/auth/alterar-senha-usuario-recuperacao").hasRole("RECUPERACAO")
//                                .antMatchers(HttpMethod.DELETE, "/aluno").hasRole("GESTOR")
//
//                                .antMatchers(HttpMethod.GET, "/acompanhamento/buscar-acompanhamento/{idAcompanhamento}").hasAnyRole("GESTOR", "INSTRUTOR")
//                                .antMatchers(HttpMethod.GET, "/acompanhamento/listar-acompanhamento").hasAnyRole("GESTOR", "INSTRUTOR")
//
//                                .antMatchers(HttpMethod.GET, "/feedback/listar-feedback").hasAnyRole("GESTOR", "INSTRUTOR")
//                                .antMatchers(HttpMethod.GET, "/feedback/listar-feedback-por-id/{idAluno}").hasAnyRole("GESTOR", "INSTRUTOR")
//
//                                .antMatchers(HttpMethod.GET, "/buscar-feedback/{idFeedBack}").hasAnyRole("GESTOR", "INSTRUTOR")
//
//                                .antMatchers(HttpMethod.PUT, "/auth/atualizar-usuario-logado").hasAnyRole("ADMIN", "GESTOR", "INSTRUTOR")
//                                .antMatchers(HttpMethod.PUT, "/auth/alterar-senha-usuario-logado").hasAnyRole("ADMIN", "GESTOR", "INSTRUTOR")
//                                .antMatchers(HttpMethod.PUT, "/auth/upload-imagem/{idUsuario}").hasAnyRole("ADMIN", "GESTOR", "INSTRUTOR")
//                                .antMatchers(HttpMethod.PUT, "/auth//upload-imagem-usuario-logado/{idUsuario}").hasAnyRole("ADMIN", "GESTOR", "INSTRUTOR")
//
//                                .antMatchers(HttpMethod.GET, "/auth/usuario-logado", "/auth/recuperar-senha").hasAnyRole("RECUPERACAO",
//                                        "ADMIN", "GESTOR", "INSTRUTOR")
//                                .antMatchers(HttpMethod.GET, "/avaliacao-acompanhamento/**").hasAnyRole("GESTOR", "INSTRUTOR")
//                                .antMatchers("/aluno/**").hasAnyRole("GESTOR", "INSTRUTOR")
//                                .antMatchers("/avaliacao-acompanhamento/**").hasRole("GESTOR")
//                                .antMatchers("/acompanhamento/**").hasRole("GESTOR")
//                                .antMatchers("/feedback/**").hasRole("INSTRUTOR")
//
//
//                                .antMatchers(HttpMethod.PUT, "/**").hasRole("ADMIN")
//                                .antMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
//                                .antMatchers("/admininstrador/**").hasRole("ADMIN")
//                                .antMatchers("/auth/**").hasRole("ADMIN")


//                                .anyRequest().authenticated()
                );

        http.addFilterBefore(new TokenAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui/**");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .exposedHeaders("Authorization");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}


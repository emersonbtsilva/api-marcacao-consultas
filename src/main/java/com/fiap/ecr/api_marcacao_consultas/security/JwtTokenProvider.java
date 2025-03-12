package com.fiap.ecr.api_marcacao_consultas.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final SecretKey chaveSecreta;

    @Value("${jwt.expiration}")
    private long tempoExpiracao;

    public JwtTokenProvider(@Value("${jwt.secret}") String segredo) {
        // Decodifica a chave secreta a partir de Base64
        byte[] keyBytes = Decoders.BASE64.decode(segredo);
        this.chaveSecreta = Keys.hmacShaKeyFor(keyBytes);
    }

    // Gerar token JWT
    public String gerarToken(String email) {
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + tempoExpiracao);

        return Jwts.builder()
                .subject(email)
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(chaveSecreta, Jwts.SIG.HS256)
                .compact();
    }

    // Extrair email do token
    public String obterEmailDoToken(String token) {
        return Jwts.parser()
                .verifyWith(chaveSecreta)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // Validar token JWT
    public boolean validarToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(chaveSecreta)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}

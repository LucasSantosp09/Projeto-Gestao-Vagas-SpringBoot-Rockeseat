package br.com.devlucassantos.gestao_vagas.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTProvider {
    @Value("${security.token.secret}")
    private String secretKey;
        public String validateToken (String token) {
            token = token.replace("Bearer ", "");

            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            try{
                var subjetct = JWT.require(algorithm).build().verify(token).getSubject();

                return subjetct;
            } catch (JWTVerificationException ex) {
                ex.printStackTrace();
                return "";
            }


        }

}

package br.com.devlucassantos.gestao_vagas.modules.company.useCases;

import br.com.devlucassantos.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.devlucassantos.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String execute (AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
                () -> {
                    throw new UsernameNotFoundException("Company not found");
                });
        // Verificar se a senha é igual
        var passworddMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());
        // se não for igual -> Erro
        if (!passworddMatches) {
            throw new AuthenticationException();
        }

        // se for igual -> Gerar o token
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var token = JWT.create().withIssuer("javagas")
                .withExpiresAt(Instant.now().plus((Duration.ofHours(2))))
                .withSubject(company.getId().toString())
                .sign(algorithm);
        return token;
    }
}

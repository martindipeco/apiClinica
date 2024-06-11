package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.infra.security.secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario)
    {
        try
        {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); //secreto para validar firma
            return JWT.create().withIssuer("voll med")
                    .withSubject(usuario.getLogin())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm); //creo un string
        }
        catch(JWTCreationException e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }

    private Instant generarFechaExpiracion()//hardcodeado a 2 horas
    {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));//averiguar offset especifico por país
    }

}

package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    public String generarToken()
    {
        try
        {
            Algorithm algorithm = Algorithm.HMAC256("123456"); //DEBE SER SECRETO. secreto para validar firma
            return JWT.create().withIssuer("voll med")
                    .withSubject("martin") //DEBE SER DINAMICO
                    .sign(algorithm); //creo un string
        }
        catch(JWTCreationException e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }
}

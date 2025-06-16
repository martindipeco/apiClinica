package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DatosRegistrarUsuario;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity registrarUsuario(@RequestBody @Valid DatosRegistrarUsuario datosRegistrarUsuario){
        if (usuarioRepository.findByLogin(datosRegistrarUsuario.login()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = passwordEncoder.encode(datosRegistrarUsuario.clave());
        Usuario nuevoUsuario = new Usuario(datosRegistrarUsuario.login(), encryptedPassword);
        usuarioRepository.save(nuevoUsuario);

        return ResponseEntity.ok().build();
    }

}

package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/consultas")
public class ConsultaController {

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datosAgendarConsulta)
    {
        System.out.println(datosAgendarConsulta);
        return ResponseEntity.ok(new DatosDetalleConsulta(null, null, null, null));
    }
}

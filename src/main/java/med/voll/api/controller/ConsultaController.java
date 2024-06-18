package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private AgendaDeconsultaService agendaDeconsultaService;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datosAgendarConsulta)
    {
        System.out.println(datosAgendarConsulta);

        var consultaAgendada =  agendaDeconsultaService.agendar(datosAgendarConsulta);

        return ResponseEntity.ok(consultaAgendada);
        //return ResponseEntity.ok(new DatosDetalleConsulta(null, null, null, null));
    }

    //DANGER! Borrado físico
    //TODO: borrado lógico
    //TODO: migracion de Consulta con agregado de booleano "cancelada" o "activa" (como Medico y Paciente)
    //TODO: creación de DTO ConsultaCancelada q recibe Consulta + info de motivo
    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarConsulta(@PathVariable Long id)
    {
        Consulta consulta = consultaRepository.getReferenceById(id);
        consultaRepository.delete(consulta);
    }
}

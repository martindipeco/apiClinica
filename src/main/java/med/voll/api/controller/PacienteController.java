package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.direccion.DatosDireccion;
import med.voll.api.medico.DatosActualizarMedico;
import med.voll.api.medico.DatosRespuestaMedico;
import med.voll.api.medico.Medico;
import med.voll.api.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaPaciente> registrarPaciente(@RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente
    , UriComponentsBuilder uriComponentsBuilder)
    {
        Paciente paciente = pacienteRepository.save(new Paciente(datosRegistroPaciente));

        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(paciente.getId(),
                paciente.getNombre(), paciente.getEmail(), paciente.getTelefono()
                , new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getDistrito()
                , paciente.getDireccion().getCiudad(), paciente.getDireccion().getNumero().toString(),
                paciente.getDireccion().getComplemento()));

        URI url = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaPaciente);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoPaciente>> listarPaciente(Pageable paginacion)
    {
        return ResponseEntity.ok(pacienteRepository.findByActivoTrue(paginacion)
                .map(DatosListadoPaciente::new));
    }

    @GetMapping("/{id}") //los gets no necesitan transactional
    public ResponseEntity<DatosRespuestaPaciente> devuelveDatosPaciente(@PathVariable Long id)
    {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        var datosPaciente = new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(), paciente.getTelefono()
                , new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getDistrito()
                , paciente.getDireccion().getCiudad(), paciente.getDireccion().getNumero().toString(),
                paciente.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosPaciente);
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarPaciente(@RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente)
    {
        Paciente paciente = pacienteRepository.getReferenceById(datosActualizarPaciente.id());
        paciente.actualizarDatos(datosActualizarPaciente);
        return ResponseEntity.ok(new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(), paciente.getTelefono()
                , new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getDistrito()
                , paciente.getDireccion().getCiudad(), paciente.getDireccion().getNumero().toString(),
                paciente.getDireccion().getComplemento())));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity desactivarPaciente(@PathVariable Long id)
    {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente();
        return ResponseEntity.noContent().build();
    }

    //DANGER! Borrado físico
//    @DeleteMapping("/{id}")
//    @Transactional
//    public void eliminarPaciente(@PathVariable Long id)
//    {
//        Paciente paciente = pacienteRepository.getReferenceById(id);
//        pacienteRepository.delete(paciente);
//    }
}

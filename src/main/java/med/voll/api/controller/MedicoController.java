package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.direccion.DatosDireccion;
import med.voll.api.medico.*;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;
    @PostMapping
    public ResponseEntity <DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                          UriComponentsBuilder uriComponentsBuilder)
    {
        //System.out.println("Se recibió el pedido para registrar médico");
        //System.out.println(datosRegistroMedico);
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getEspecialidad().toString()
                , new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito()
                , medico.getDireccion().getCiudad(), medico.getDireccion().getNumero().toString(),
                medico.getDireccion().getComplemento()));
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> listarMedicos(@PageableDefault (size = 3) Pageable paginacion)
    {
//        return medicoRepository.findAll(paginacion)
//                .map(DatosListadoMedico::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion)
                .map(DatosListadoMedico::new));
    }

    @GetMapping("/{id}")
    //@Transactional : los gets no necesitan transactional
    public ResponseEntity<DatosRespuestaMedico> devuelveDatosMedico(@PathVariable Long id)
    {
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getEspecialidad().toString()
                , new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito()
                , medico.getDireccion().getCiudad(), medico.getDireccion().getNumero().toString(),
                medico.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosMedico);
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico)
    {
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getEspecialidad().toString()
        , new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito()
        , medico.getDireccion().getCiudad(), medico.getDireccion().getNumero().toString(),
                medico.getDireccion().getComplemento())));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity desactivarMedico(@PathVariable Long id)
    {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    //DANGER! Borrado físico
//    @DeleteMapping("/{id}")
//    @Transactional
//    public void eliminarMedico(@PathVariable Long id)
//    {
//        Medico medico = medicoRepository.getReferenceById(id);
//        medicoRepository.delete(medico);
//    }
}

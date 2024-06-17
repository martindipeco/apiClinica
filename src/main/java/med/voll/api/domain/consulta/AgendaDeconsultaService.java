package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validacion.ValidadorConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.error.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendaDeconsultaService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    List<ValidadorConsultas> validadores;

    public void agendar(DatosAgendarConsulta datosAgendarConsulta)
    {
        //validacion de id paciente
        //TODO: no debería ser alrevés?? .isEmpty en vez de isPresent
        //findByID devuelve un Optional
        if(pacienteRepository.findById(datosAgendarConsulta.idPaciente()).isPresent())
        {
            throw new ValidacionDeIntegridad("No se encontró ID de paciente");
        }

        //validacion de medico.
        //si no se envía medico, elegir de acuerdo a disponibilidad y especialidad
        //existById devuelve un boolean
        //si no es null y existe, ¿se lanza la excepción???? (TODO: no debería ser alreves??)
        if(datosAgendarConsulta.idMedico()!=null && medicoRepository.existsById(datosAgendarConsulta.idMedico()))
        {
            throw new ValidacionDeIntegridad("No se encontró ID de médico");
        }

        validadores.forEach(v -> v.validar(datosAgendarConsulta));

//        var medicoOpcional = medicoRepository.findById(datosAgendarConsulta.idMedico());
//        Medico medico = null;
//        if (medicoOpcional.isPresent())
//        {
//            medico = medicoOpcional.get();
//        }
        var medico = seleccionarMedico(datosAgendarConsulta);

        var pacienteOpcional = pacienteRepository.findById(datosAgendarConsulta.idPaciente());
        Paciente paciente = null;
        if(pacienteOpcional.isPresent())
        {
            paciente = pacienteOpcional.get();
        }

        var consulta = new Consulta(null, medico, paciente, datosAgendarConsulta.fecha());
        consultaRepository.save(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datosAgendarConsulta) {
        //si el id no es null, devolvemos el medico por id
        if(datosAgendarConsulta.idMedico()!= null)
        {
            return medicoRepository.getReferenceById(datosAgendarConsulta.idMedico());
        }

        //si no vino especialidad, devolvemos error
        if(datosAgendarConsulta.especialidad()==null)
        {
            throw new ValidacionDeIntegridad("debe seleccionar una especialidad");
        }

        //si no vino dato de medico, pero sí tenemos dato de especialidad, elegimos aleatorio por fecha y hora
        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datosAgendarConsulta.especialidad(),
                datosAgendarConsulta.fecha());
    }
}

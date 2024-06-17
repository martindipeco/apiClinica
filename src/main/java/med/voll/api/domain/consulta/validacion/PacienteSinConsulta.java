package med.voll.api.domain.consulta.validacion;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteSinConsulta implements ValidadorConsultas{

    //private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DatosAgendarConsulta datosAgendarConsulta)
    {
        var primerHorario = datosAgendarConsulta.fecha().withHour(7);
        var ultimoHorario = datosAgendarConsulta.fecha().withHour(18);

        var pacienteConConsulta = consultaRepository.existsByPacienteIdAndFechaBetween(
                datosAgendarConsulta.idPaciente(), primerHorario, ultimoHorario);

//        var pacienteConConsultaMismoDia = pacienteRepository.findAgendadoById(datosAgendarConsulta.idPaciente(),
//                datosAgendarConsulta.fecha());

        if(pacienteConConsulta)
        {
            throw new ValidationException("El paciente ya tiene una consulta ese día");
        }
    }
}

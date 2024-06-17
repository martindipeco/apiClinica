package med.voll.api.domain.consulta.validacion;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;

public class MedicoSinConsulta {

    private ConsultaRepository consultaRepository;

    public void validar(DatosAgendarConsulta datosAgendarConsulta) {

        if(datosAgendarConsulta.idMedico()==null)
        {
            return;
        }

        var medicoConConsulta = consultaRepository.existsByMedicoIdAndFecha(
                datosAgendarConsulta.idPaciente(), datosAgendarConsulta.fecha());

        if (medicoConConsulta) {
            throw new ValidationException("El médico ya tiene una consulta en ese horario");
        }
    }
}

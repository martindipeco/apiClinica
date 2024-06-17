package med.voll.api.domain.consulta.validacion;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoSinConsulta implements ValidadorConsultas {

    @Autowired
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

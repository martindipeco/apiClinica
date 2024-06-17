package med.voll.api.domain.consulta.validacion;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteActivo implements ValidadorConsultas{

    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar (DatosAgendarConsulta datosAgendarConsulta)
    {
        if(datosAgendarConsulta.idPaciente()==null)
        {
            return;
        }

        var pacienteActivo = pacienteRepository.findActivoById(datosAgendarConsulta.idPaciente());

        if(!pacienteActivo)
        {
            throw new ValidationException("El Paciente no está activo");
        }
    }
}

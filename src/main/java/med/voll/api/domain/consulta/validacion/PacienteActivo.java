package med.voll.api.domain.consulta.validacion;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;

public class PacienteActivo {

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

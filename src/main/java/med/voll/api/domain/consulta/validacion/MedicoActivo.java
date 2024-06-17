package med.voll.api.domain.consulta.validacion;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoActivo implements ValidadorConsultas{

    @Autowired
    private MedicoRepository medicoRepository;

    public void validar (DatosAgendarConsulta datosAgendarConsulta)
    {
        if(datosAgendarConsulta.idMedico()==null)
        {
            return;
        }

        var medicoActivo = medicoRepository.findActivoById(datosAgendarConsulta.idMedico());

        if(!medicoActivo)
        {
            throw new ValidationException("El Médico no está activo");
        }
    }
}

package med.voll.api.domain.consulta.validacion;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelacionConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaCancelacion")
public class AnticipacionParaCancelarTurno implements ValidadorCancelacion{

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DatosCancelacionConsulta datosCancelacionConsulta)
    {
        var consulta = consultaRepository.getReferenceById(datosCancelacionConsulta.idConsulta());
        var ahora = LocalDateTime.now();
        var diferenciaEnHoras = Duration.between(ahora, consulta.getFecha()).toHours();

        if (diferenciaEnHoras<24)
        {
            throw new ValidationException("No es posible cancelar con menos de 24 hs de anticipación");
        }
    }
}

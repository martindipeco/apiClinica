package med.voll.api.domain.consulta.validacion;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;

import java.time.Duration;
import java.time.LocalDateTime;

public class HorarioAnticipacion {

    public void validar(DatosAgendarConsulta datosAgendarConsulta)
    {
        var ahora = LocalDateTime.now();
        var horaConsulta = datosAgendarConsulta.fecha();

        var diferenciaMenorA30min = Duration.between(ahora, horaConsulta).toMinutes() < 30;

        if(diferenciaMenorA30min)
        {
            throw new ValidationException("debe haber 30 min al menos de anticipación para la consulta");
        }
    }
}

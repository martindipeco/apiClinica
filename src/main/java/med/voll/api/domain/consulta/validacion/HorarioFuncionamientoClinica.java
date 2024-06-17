package med.voll.api.domain.consulta.validacion;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class HorarioFuncionamientoClinica implements ValidadorConsultas{

    public void validar(DatosAgendarConsulta datosAgendarConsulta)
    {
        var domingo = DayOfWeek.SUNDAY.equals(datosAgendarConsulta.fecha().getDayOfWeek());
        var antesApertura = datosAgendarConsulta.fecha().getHour()<7;
        var despuesCierre = datosAgendarConsulta.fecha().getHour()>19;

        if (domingo || antesApertura || despuesCierre)
        {
            throw new ValidationException("La clínica abre de lunes a sábados de 7 a 19 hs");
        }
    }
}

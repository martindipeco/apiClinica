package med.voll.api.paciente;

import med.voll.api.direccion.DatosDireccion;

public record DatosRespuestaPaciente(Long id, String nombre, String email, String telefono
        , DatosDireccion datosDireccion) {
}

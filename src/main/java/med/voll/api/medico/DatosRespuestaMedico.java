package med.voll.api.medico;

import med.voll.api.direccion.DatosDireccion;
import med.voll.api.direccion.Direccion;

public record DatosRespuestaMedico(Long id, String nombre, String email, String telefono
, String documento, DatosDireccion datosDireccion) {
}

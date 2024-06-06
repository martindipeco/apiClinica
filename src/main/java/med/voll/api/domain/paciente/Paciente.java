package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    @Embedded
    private Direccion direccion;
    private boolean activo;

    public Paciente (DatosRegistroPaciente datosRegistroPaciente)
    {
        this.nombre = datosRegistroPaciente.nombre();
        this.documento = datosRegistroPaciente.documento();
        this.telefono = datosRegistroPaciente.telefono();
        this.direccion = new Direccion(datosRegistroPaciente.direccion());
        this.email = datosRegistroPaciente.email();
        this.activo = true;
    }

    public void actualizarDatos(DatosActualizarPaciente datosActualizarPaciente)
    {
        if (datosActualizarPaciente.nombre() != null)
        {
            this.nombre = datosActualizarPaciente.nombre();
        }
        if (datosActualizarPaciente.documento() != null)
        {
            this.documento = datosActualizarPaciente.documento();
        }
        if (datosActualizarPaciente.direccion() != null)
        {
            this.direccion = new Direccion(datosActualizarPaciente.direccion());
        }
    }

    public void desactivarPaciente()
    {
        this.activo = false;
    }


}

package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;
    private boolean activo;

    public Medico (DatosRegistroMedico datosRegistroMedico)
    {
        this.nombre = datosRegistroMedico.nombre();
        this.documento = datosRegistroMedico.documento();
        this.telefono = datosRegistroMedico.telefono();
        this.direccion = new Direccion(datosRegistroMedico.direccion());
        this.email = datosRegistroMedico.email();
        this.especialidad = datosRegistroMedico.especialidad();
        this.activo = true;
    }

    public Medico(String nombre, String email, String documento, Especialidad especialidad)
    {
        this.nombre = nombre;
        this.email = email;
        this.documento = documento;
        this.especialidad = especialidad;
        this.telefono = "test";
        this.direccion = new Direccion("test", "test", "test", 1, "test");
        this.activo = true;
    }



    public void actualizarDatos(DatosActualizarMedico datosActualizarMedico)
    {
        if (datosActualizarMedico.nombre() != null)
        {
            this.nombre = datosActualizarMedico.nombre();
        }
        if (datosActualizarMedico.documento() != null)
        {
            this.documento = datosActualizarMedico.documento();
        }
        if (datosActualizarMedico.direccion() != null)
        {
            this.direccion = new Direccion(datosActualizarMedico.direccion());
        }
    }

    public void desactivarMedico()
    {
        this.activo = false;
    }
}

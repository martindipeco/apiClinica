package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Debería devolver null cuando el médico ya está ocupado")
    void seleccionarMedicoConEspecialidadEnFechaOcupado() {

        //given
        var proximoLunes10hs = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

        var medico = registrarMedico("Jose", "jos@mail.com", "762036", Especialidad.CARDIOLOLOGIA);

        var paciente = registrarPaciente("Lurdes", "lurdes@mail.com", "224367");

        //when
        var medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOLOGIA, proximoLunes10hs);

        //then
        assertThat(medicoLibre).isNull();
    }

    @Test
    @DisplayName("Debería devolver un médico cuando esté disponible de acuerdo a fecha y especialidad")
    void seleccionarMedicoConEspecialidadEnFechaDisponible() {

        //given
        var proximoMartes10hs = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.TUESDAY)).atTime(10, 0);

        var medico = registrarMedico("Jose", "joe@mail.com", "703546", Especialidad.CARDIOLOLOGIA);

        //when
        var medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOLOGIA, proximoMartes10hs);

        //then
        assertThat(medicoLibre).isEqualTo(medico);
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad)
    {
        var medico = new Medico(nombre, email, documento, especialidad);
        testEntityManager.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento)
    {
        var paciente = new Paciente(nombre, email, documento);
        testEntityManager.persist(paciente);
        return paciente;
    }

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha)
    {
        testEntityManager.persist(new Consulta(null, medico, paciente, fecha, null));
    }
}
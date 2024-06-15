package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AgendaDeconsultaService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    public void agendar(DatosAgendarConsulta datosAgendarConsulta)
    {
        var medicoOpcional = medicoRepository.findById(datosAgendarConsulta.idMedico());
        Medico medico = null;
        if (medicoOpcional.isPresent())
        {
            medico = medicoOpcional.get();
        }

        var pacienteOpcional = pacienteRepository.findById(datosAgendarConsulta.idPaciente());
        Paciente paciente = null;
        if(pacienteOpcional.isPresent())
        {
            paciente = pacienteOpcional.get();
        }

        var consulta = new Consulta(null, medico, paciente, datosAgendarConsulta.fecha());
        consultaRepository.save(consulta);
    }
}

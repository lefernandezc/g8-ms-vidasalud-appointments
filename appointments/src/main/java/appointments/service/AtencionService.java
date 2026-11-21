package appointments.service;

import appointments.dto.AtencionDTO;
import appointments.dto.CitaEventoDTO;
import appointments.exception.NotFoundException;
import appointments.kafka.CitaKafkaProducer;
import appointments.mapper.Mapper;
import appointments.model.Atencion;
import appointments.repository.AtencionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AtencionService implements AtencionServiceImpl {

    @Autowired
    private AtencionRepository repo;

    @Autowired
    private CitaKafkaProducer citaKafkaProducer;

    @Override
    public List<AtencionDTO> traerAtencion() {
        return repo.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public AtencionDTO crearAtencion(AtencionDTO atencionDto) {
        var doc = Atencion.builder()
                .paciente(atencionDto.getPaciente())
                .doctor(atencionDto.getDoctor())
                .horaFecha(atencionDto.getHoraFecha())
                .build();

        Atencion guardada = repo.save(doc);
        AtencionDTO resultado = Mapper.toDTO(guardada);

        citaKafkaProducer.publicarEventoCita(CitaEventoDTO.builder()
                .eventId(UUID.randomUUID().toString())
                .tipoEvento("CITA_CREADA")
                .atencionId(guardada.getId())
                .paciente(guardada.getPaciente())
                .doctor(guardada.getDoctor())
                .horaFecha(guardada.getHoraFecha())
                .timestamp(LocalDateTime.now())
                .build());

        return resultado;
    }

    @Override
    public AtencionDTO actualizarAtencion(Long id, AtencionDTO atencionDto) {
        Atencion ate = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Atencion no encontrada"));

        ate.setPaciente(atencionDto.getPaciente());
        ate.setDoctor(atencionDto.getDoctor());
        ate.setHoraFecha(atencionDto.getHoraFecha());

        Atencion actualizada = repo.save(ate);
        AtencionDTO resultado = Mapper.toDTO(actualizada);

        citaKafkaProducer.publicarEventoCita(CitaEventoDTO.builder()
                .eventId(UUID.randomUUID().toString())
                .tipoEvento("CITA_ACTUALIZADA")
                .atencionId(actualizada.getId())
                .paciente(actualizada.getPaciente())
                .doctor(actualizada.getDoctor())
                .horaFecha(actualizada.getHoraFecha())
                .timestamp(LocalDateTime.now())
                .build());

        return resultado;
    }

    @Override
    public void eliminarAtencion(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Atencion no encontrado para eliminar");
        }
        repo.deleteById(id);

        citaKafkaProducer.publicarEventoCita(CitaEventoDTO.builder()
                .eventId(UUID.randomUUID().toString())
                .tipoEvento("CITA_ELIMINADA")
                .atencionId(id)
                .timestamp(LocalDateTime.now())
                .build());
    }
}

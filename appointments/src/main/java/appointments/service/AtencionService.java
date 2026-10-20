package appointments.service;

import appointments.dto.AtencionDTO;
import appointments.exception.NotFoundException;
import appointments.mapper.Mapper;
import appointments.model.Atencion;
import appointments.repository.AtencionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtencionService implements AtencionServiceImpl{

    @Autowired
    private AtencionRepository repo;

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

        return Mapper.toDTO(repo.save(doc));
    }

    @Override
    public AtencionDTO actualizarAtencion(Long id, AtencionDTO atencionDto) {

        Atencion ate = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Atencion no encontrada"));

        ate.setPaciente(atencionDto.getPaciente());
        ate.setDoctor(atencionDto.getDoctor());
        ate.setHoraFecha(atencionDto.getHoraFecha());

        return Mapper.toDTO(repo.save(ate));
    }

    @Override
    public void eliminarAtencion(Long id) {

        if (!repo.existsById(id)){
            throw new NotFoundException("Atencion no encontrado para eliminar");
        }

        repo.deleteById(id);
    }
}

package appointments.model.service;

import appointments.model.Atencion;
import appointments.model.EstadoAtencion;
import appointments.model.repository.AtencionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AtencionService {
    private final AtencionRepository repository;
    private final NotificacionService notificador; //para hacer despues en el repo de notis

    @Transactional
    public Atencion crear(Atencion atencion){
        LocalDateTime inicio = atencion.getFechaHora().minusMinutes(15);
        LocalDateTime fin = atencion.getFechaHora().plusMinutes(15);

        if (repository.existsByProfesionalIdAndFechaHoraBetweenAndEstadoNot(
                atencion.getProfesionalId(), inicio, fin, EstadoAtencion.CANCELADA)) {
            throw new IllegalStateException("Sin cupo: El profesional ya tiene una atencion en ese horario.");
        }

        atencion.setEstado(EstadoAtencion.SOLICITADA);
        Atencion guardada = repository.save(atencion);
        notificador.enviar(atencion.getPacienteId(), "Su atención ha sido registrada.");
        return guardada;
    }

    @Transactional
    public Atencion cambiarEstado(Long id, EstadoAtencion nuevoEstado){
        Atencion atencion = repository.findById(id).orElseThrow();
        EstadoAtencion actual = atencion.getEstado();

        boolean transicionValida = switch (actual) {
            case SOLICITADA -> nuevoEstado == EstadoAtencion.CONFIRMADA || nuevoEstado == EstadoAtencion.CANCELADA;
            case CONFIRMADA -> nuevoEstado == EstadoAtencion.EN_ESPERA || nuevoEstado == EstadoAtencion.CANCELADA;
            case EN_ESPERA -> nuevoEstado == EstadoAtencion.EN_ATENCION || nuevoEstado == EstadoAtencion.CANCELADA;
            case EN_ATENCION -> nuevoEstado == EstadoAtencion.CERRADA;
            case CERRADA, CANCELADA -> false;
        };

        if (!transicionValida) {
            throw new IllegalStateException("Regla de negocio violada: No se puede pasar de " + actual + " a " + nuevoEstado);
        }

        atencion.setEstado(nuevoEstado);
        Atencion actualizada = repository.save(atencion);
        notificador.enviar(actualizada.getPacienteId(), "Su atención ahora está: " + nuevoEstado);
        return actualizada;
    }

    public List<Atencion> listar(){
        return  repository.findAll();
    }
}

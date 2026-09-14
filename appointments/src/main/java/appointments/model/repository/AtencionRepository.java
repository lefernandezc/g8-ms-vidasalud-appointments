package appointments.model.repository;

import appointments.model.Atencion;
import appointments.model.EstadoAtencion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AtencionRepository extends JpaRepository<Atencion, Long> {
    boolean existsByProfesionalIdAndFechaHoraBetweenAndEstadoNot(
            Long profesionalId,
            LocalDateTime inicio,
            LocalDateTime fin,
            EstadoAtencion estado
    );
}

package appointments.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="ATENCIONES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Atencion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long pacienteId;
    private Long profesionalId;
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    private EstadoAtencion estado;
    private String observaciones;
}

package appointments.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtencionDTO {

    private Long id;
    private String paciente;
    private String doctor;
    private LocalDateTime horaFecha;
}

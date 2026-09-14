package appointments.model.view;

import java.time.LocalDateTime;
import appointments.model.EstadoAtencion;
public record AtencionResponse (
    Long ticketId,
    Long pacienteId,
    LocalDateTime fechaProgramada,
    EstadoAtencion estadoAtencion,
    String mensajeSistema
){
    public static AtencionResponse desdeModelo(appointments.model.Atencion modelo, String mensaje){
        return new AtencionResponse(
                modelo.getId(),
                modelo.getPacienteId(),
                modelo.getFechaHora(),
                modelo.getEstado(),
                mensaje
        );
    }
}

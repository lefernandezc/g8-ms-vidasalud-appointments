//package appointments.view;
//
//import java.time.LocalDateTime;
//import appointments.model.Paciente;
//public record AtencionResponse (
//    Long ticketId,
//    Long pacienteId,
//    LocalDateTime fechaProgramada,
//    Paciente estadoAtencion,
//    String mensajeSistema
//){
//    public static AtencionResponse desdeModelo(appointments.model.Atencion modelo, String mensaje){
//        return new AtencionResponse(
//                modelo.getId(),
//                modelo.getPacienteId(),
//                modelo.getFechaHora(),
//                modelo.getEstado(),
//                mensaje
//        );
//    }
//}

package appointments.model.service;

public interface NotificacionService {
    void enviar(Long pacienteId, String mensaje);
}

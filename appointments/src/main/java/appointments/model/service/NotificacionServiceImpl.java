package appointments.model.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class NotificacionServiceImpl implements NotificacionService{

    private final RestClient restClient;

    public NotificacionServiceImpl(@Value("${notificaciones.api.url}")String baseUrl){
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public void enviar(Long pacienteId, String mensaje){

        restClient.post()
                .uri(uriBuilder -> uriBuilder
                .path("/api/v1/notificaciones/enviar")
                .queryParam("pacienteId", pacienteId)
                .queryParam("mensaje", mensaje)
                .build())
                .retrieve()
                .toBodilessEntity();
    }

}

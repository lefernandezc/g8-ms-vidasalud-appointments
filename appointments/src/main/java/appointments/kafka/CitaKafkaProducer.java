package appointments.kafka;

import appointments.dto.CitaEventoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CitaKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper kafkaObjectMapper;

    @Value("${vidasalud.kafka.topic.citas:vidasalud.citas.eventos}")
    private String citasTopic;

    public void publicarEventoCita(CitaEventoDTO evento) {
        try {
            String key = evento.getAtencionId() != null ? evento.getAtencionId().toString() : evento.getEventId();
            String json = kafkaObjectMapper.writeValueAsString(evento);
            kafkaTemplate.send(citasTopic, key, json).whenComplete((result, ex) -> {
                if (ex != null) {
                    log.error("[KAFKA-PRODUCER] Error enviando evento {} a {}: {}", evento.getEventId(), citasTopic, ex.getMessage());
                } else {
                    log.info("[KAFKA-PRODUCER] Evento {} publicado en topico={} particion={} offset={}",
                            evento.getTipoEvento(), citasTopic,
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset());
                }
            });
        } catch (Exception e) {
            log.error("[KAFKA-PRODUCER] Error serializando evento de cita: {}", e.getMessage(), e);
        }
    }
}

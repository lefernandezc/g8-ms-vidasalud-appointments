package appointments.kafka;

import appointments.dto.AuditoriaStreamDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditoriaStreamConsumer {

    private final ObjectMapper kafkaObjectMapper;

    @KafkaListener(
            topics = "${vidasalud.kafka.topic.auditoria:vidasalud.auditoria.stream}",
            groupId = "appointments-audit-group"
    )
    public void recibirConfirmacionAuditoria(String payload) throws Exception {
        if (payload == null || payload.isBlank()) {
            throw new IllegalArgumentException("Payload vacio recibido desde vidasalud.auditoria.stream");
        }
        AuditoriaStreamDTO evento = kafkaObjectMapper.readValue(payload, AuditoriaStreamDTO.class);
        if (evento.getStreamId() == null || evento.getTipoEvento() == null) {
            throw new IllegalArgumentException("DTO de auditoria incompleto: " + payload);
        }
        log.info("[KAFKA-CONSUMER][appointments] Confirmacion de auditoria recibida: streamId={}, tipo={}, resumen={}",
                evento.getStreamId(), evento.getTipoEvento(), evento.getResumen());
    }
}

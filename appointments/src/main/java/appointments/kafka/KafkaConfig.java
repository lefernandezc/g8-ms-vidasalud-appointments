package appointments.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@Configuration
public class KafkaConfig {

    @Bean
    public ObjectMapper kafkaObjectMapper() {
        return new ObjectMapper().findAndRegisterModules();
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
            ConsumerFactory<String, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

        // Reintentos (3 intentos cada 2s) antes de descartar mensaje corrupto sin detener el consumidor
        DefaultErrorHandler errorHandler = new DefaultErrorHandler((record, ex) ->
                log.error("[KAFKA-ERROR] Mensaje fallido tras reintentos en topico={}, offset={}, payload={}, error={}",
                        record.topic(), record.offset(), record.value(), ex.getMessage()),
                new FixedBackOff(2000L, 3L));
        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }
}

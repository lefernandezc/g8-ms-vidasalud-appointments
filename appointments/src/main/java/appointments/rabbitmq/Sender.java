//package appointments.rabbitmq;
//
//
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//@Component
//public class Sender {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    public void sendMessage(String message) {
//        try {
//            String timestamp = LocalDateTime.now()
//                    .format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
//
//            String fullMessage = String.format(
//                    "[%s] $s",
//                    timestamp,
//                    message
//            );
//
//            rabbitTemplate.convertAndSend("hello", fullMessage);
//
//            System.out.println("[‼] Mensaje enviado" + fullMessage + " ");
//
//        }catch (Exception e){
//            System.err.println("[X] Error enviando mensaje" +
//                    e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    public void sendMessage(String exchange, String routingKey, String message){
//        try {
//            rabbitTemplate.convertAndSend(exchange, routingKey, message);
//            System.out.println("[ ] Mensaje enviado a exchange='" +
//                    exchange
//                            + "', routingKey='" + routingKey + "': '" + message + "'");
//        } catch (Exception e) {
//            System.err.println("[ ] Error enviando mensaje: " +
//                    e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}
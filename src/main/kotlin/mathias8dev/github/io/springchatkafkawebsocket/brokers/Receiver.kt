package mathias8dev.github.io.springchatkafkawebsocket.brokers

import mathias8dev.github.io.springchatkafkawebsocket.Message
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.messaging.simp.user.SimpUserRegistry
import org.springframework.stereotype.Service


@Service
class Receiver(
    private val userRegistry: SimpUserRegistry,
    private val messagingTemplate: SimpMessageSendingOperations
) {

    private val logger = LoggerFactory.getLogger(javaClass)



    @KafkaListener(topics = ["messaging"], groupId = "chat")
    fun consume(chatMessage: Message) {
        logger.info("Received message from Kafka: $chatMessage")
        for (session in userRegistry.users.flatMap { it.sessions }) {
            if (session.id != chatMessage.sessionId) {
                messagingTemplate.convertAndSendToUser(session.id, "/topic/public", chatMessage)
            }
        }
    }
}
package mathias8dev.github.io.springchatkafkawebsocket.listeners

import mathias8dev.github.io.springchatkafkawebsocket.Message
import mathias8dev.github.io.springchatkafkawebsocket.MessageTypeEnum
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent


@Component
class WebSocketEventListener(
    private val messagingTemplate: SimpMessageSendingOperations
) {

    private val logger = LoggerFactory.getLogger(javaClass)


    @EventListener
    fun handleWebSocketConnectListener(event: SessionConnectEvent) {
        logger.info("New user connected")
    }


    @EventListener
    fun handleWebSocketDisconnectListener(event: SessionDisconnectEvent) {
        val headerAccessor = StompHeaderAccessor.wrap(event.message)
        val username = headerAccessor.sessionAttributes?.get("username") as String?

        if (username != null) {
            logger.info("User disconnected: $username")
            val chatMessage = Message()
            chatMessage.type = MessageTypeEnum.DISCONNECT
            chatMessage.sender = username

            messagingTemplate.convertAndSend("/topic/public", chatMessage)
        }
    }
}
package mathias8dev.github.io.springchatkafkawebsocket.controllers

import mathias8dev.github.io.springchatkafkawebsocket.Message
import mathias8dev.github.io.springchatkafkawebsocket.brokers.Sender
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Controller


@Controller
class MessageController(
    private val sender: Sender,
    private val messagingTemplate: SimpMessageSendingOperations
) {

    private val logger = LoggerFactory.getLogger(javaClass)


    @MessageMapping("chat.send-message")
    fun sendMessage(@Payload chatMessage: Message, headerAccessor: SimpMessageHeaderAccessor) {
        chatMessage.sessionId = headerAccessor.sessionId
        sender.send("messaging", chatMessage)
        logger.info("Sending message to /topic/puclic: $chatMessage")
        messagingTemplate.convertAndSend("/topic/public", chatMessage)
        logger.info("Message sent to /topic/public: $chatMessage")
    }

    @MessageMapping("chat.add-user")
    @SendTo("/topic/public")
    fun addUser(@Payload chatMessage: Message, headerAccessor: SimpMessageHeaderAccessor): Message {
        headerAccessor.sessionAttributes?.put("username", chatMessage.sender)
        return chatMessage
    }
}
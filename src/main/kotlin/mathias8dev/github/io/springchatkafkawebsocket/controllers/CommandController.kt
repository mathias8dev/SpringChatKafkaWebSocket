package mathias8dev.github.io.springchatkafkawebsocket.controllers

import mathias8dev.github.io.springchatkafkawebsocket.Message
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class CommandController(
    private val kafkaTemplate: KafkaTemplate<String, Message>,
    private val messagingTemplate: SimpMessageSendingOperations
) {


    @PostMapping("/send")
    fun send(@RequestBody message: Message) {
        kafkaTemplate.send("messaging", message)
        messagingTemplate.convertAndSend("/topic/public", message)
    }
}
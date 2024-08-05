package mathias8dev.github.io.springchatkafkawebsocket.brokers

import mathias8dev.github.io.springchatkafkawebsocket.Message
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service


@Service
class Sender(
    private val kafkaTemplate: KafkaTemplate<String, Message>
) {


    fun send(topic: String, message: Message) {
        kafkaTemplate.send(topic, message)
    }

}
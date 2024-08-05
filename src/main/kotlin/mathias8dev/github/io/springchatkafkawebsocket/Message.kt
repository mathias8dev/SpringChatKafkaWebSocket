package mathias8dev.github.io.springchatkafkawebsocket

data class Message(
    var type: MessageTypeEnum? = null,
    var content: String? = null,
    var sender: String? = null,
    var sessionId: String? = null
)

enum class MessageTypeEnum {
    CHAT,
    CONNECT,
    DISCONNECT
}
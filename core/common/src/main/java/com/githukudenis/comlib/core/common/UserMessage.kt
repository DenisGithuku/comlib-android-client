package com.githukudenis.comlib.core.common

data class UserMessage(
    val id: Int = 0,
    val message: String? = null,
    val messageType: MessageType = MessageType.INFO
)

enum class MessageType {
    INFO,
    ERROR
}

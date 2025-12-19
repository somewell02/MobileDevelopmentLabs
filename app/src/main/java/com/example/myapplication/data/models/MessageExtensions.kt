package com.example.myapplication.data.models

import com.example.myapplication.data.database.MessageEntity

fun Message.toEntity(): MessageEntity {
    return MessageEntity(
        id = this.id,
        postId = this.postId,
        name = this.name,
        email = this.email,
        body = this.body
    )
}

fun MessageEntity.toMessage(): Message {
    return Message(
        id = this.id,
        postId = this.postId,
        name = this.name,
        email = this.email,
        body = this.body
    )
}

fun List<Message>.toEntities(): List<MessageEntity> {
    return this.map { it.toEntity() }
}

fun List<MessageEntity>.toMessages(): List<Message> {
    return this.map { it.toMessage() }
}
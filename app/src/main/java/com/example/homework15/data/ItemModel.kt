package com.example.homework15.data

import com.squareup.moshi.Json

data class ItemModel(
    val id: Int?,
    val image: String?,
    val owner: String?,
    @Json(name = "last_message")
    val lastMessage: String?,
    @Json(name = "last_active")
    val lastActive: String?,
    @Json(name = "unread_messages")
    val unreadMessages: Int?,
    @Json(name = "is_typing")
    val isTyping: Boolean?,
    @Json(name = "laste_message_type")
    val lasteMessageType: String?
)


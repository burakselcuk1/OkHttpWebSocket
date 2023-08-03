package com.example.socket.socket.model

data class Subscription(
    val event : String,
    val data : SubscriptionData = SubscriptionData()
)
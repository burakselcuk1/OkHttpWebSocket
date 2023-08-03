package com.example.socket.socket.model

data class WebSocketData(
    val data: TradeData,
    val channel: String,
    val event: String
)

data class TradeData(
    val id: Long,
    val timestamp: String,
    val amount: Double,
    val amount_str: String,
    val price: Int,
    val price_str: String,
    val type: Int,
    val microtimestamp: String,
    val buy_order_id: Long,
    val sell_order_id: Long
)

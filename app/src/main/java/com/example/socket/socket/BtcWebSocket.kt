package com.example.socket.socket

import android.util.Log
import com.example.socket.socket.model.Subscription
import com.example.socket.socket.model.WebSocketData
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import javax.inject.Inject

class BtcWebSocket @Inject constructor(
    private val gson: Gson,
    val okhttpClient : OkHttpClient,
    val webSocketRequest : Request,
) {
    lateinit var socket : WebSocket
    var messageListener: ((WebSocketData) -> Unit)? = null
    var onOpenListener: (() -> Unit)? = null
    var onFailureListener: (() -> Unit)? = null

    init {
        val listener = websocketListener()
        socket = okhttpClient.newWebSocket(webSocketRequest,listener)
    }

    fun websocketListener() : WebSocketListener {
        return object : WebSocketListener(){
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.d("WebSocketopen", "Gelen Veri: $response")
                subscribe()

            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Log.d("WebSocket", "Gelen Veri: $text")
                val webSocketData = gson.fromJson(text, WebSocketData::class.java)
                messageListener?.invoke(webSocketData)
                onOpenListener?.invoke()

            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.d("WebSocketfail", "Gelen Veri: $response")
                onFailureListener?.invoke()

            }
        }
    }

    private fun subscribe(){
        socket.send(gson.toJson(SUBSCRIBE_MESSAGE))
    }

    companion object{
        private val SUBSCRIBE_MESSAGE = Subscription(event = "bts:subscribe")
        private val UNSUBSCRIBE_MESSAGE = Subscription(event = "bts:unsubscribe")
    }

}

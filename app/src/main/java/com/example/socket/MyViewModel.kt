package com.example.socket

import androidx.lifecycle.ViewModel
import com.example.socket.socket.BtcWebSocket
import com.example.socket.socket.model.WebSocketData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.Response
import okio.ByteString

@HiltViewModel
class MyViewModel @Inject constructor(private val btcWebSocket: BtcWebSocket) : ViewModel() {

    private val _webSocketData = MutableStateFlow<WebSocketData?>(null)
    val webSocketData: StateFlow<WebSocketData?> = _webSocketData

    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        btcWebSocket.messageListener = { message ->
            _webSocketData.tryEmit(message)
        }

        btcWebSocket.onOpenListener = {
            _isLoading.tryEmit(false)
        }
        btcWebSocket.onFailureListener = {
            _isLoading.tryEmit(true)
        }
    }
}


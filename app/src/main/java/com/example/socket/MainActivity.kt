package com.example.socket



import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.MainScope
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.socket.MyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MyViewModel by lazy { ViewModelProvider(this).get(MyViewModel::class.java) }
    private val textView: TextView by lazy { findViewById<TextView>(R.id.textview) }
    private val progress: ProgressBar by lazy { findViewById<ProgressBar>(R.id.progress_bar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MainScope().launch {
            viewModel.webSocketData.collect { data ->
                textView.text = data?.data?.price.toString()
            }

        }

        MainScope().launch {
            viewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    progress.visibility = View.VISIBLE
                    textView.visibility = View.GONE

                } else {
                    progress.visibility = View.GONE
                    textView.visibility = View.VISIBLE
                }
            }
        }
    }
}

package com.example.task_final_g7.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.task_final_g7.databinding.ActivityChuckerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class Chucker : AppCompatActivity() {
    private lateinit var binding : ActivityChuckerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChuckerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(this))
            .build()


        // Chucker Collector
        val myChuckerCollector = ChuckerCollector(
            context = this,// Context on which you are
            showNotification = true, // Boolean for showing Notification
            retentionPeriod = RetentionManager.Period.ONE_WEEK // Period taken to retain the collected data
        )
        // Chucker Interceptor
        val myChuckerInterceptor = ChuckerInterceptor.Builder(this) // `this` is the context
            // The previously created ChuckerCollector
            .collector(myChuckerCollector)
            // The maximum body content length in bytes, after this responses will be truncated.
            .maxContentLength(250_000L)
            // List of headers to replace with ** in the Chucker UI
            .redactHeaders("Auth-Token", "Bearer")
            // Read the whole response body even when the client does not consume the response completely.
            // This is useful in case of parsing errors or when the response body
            // is closed before being read like in Retrofit with Void and Unit types.
            .alwaysReadResponseBody(true)
            .build()
        // OkHttp Client
        val client = OkHttpClient.Builder()
            .addInterceptor(myChuckerInterceptor)
            .build()

        // OkHttp network request
        val request = Request.Builder()
            .url("https://elephant-api.herokuapp.com/elephants/")
            .build()

        // OkHttp request should run in the Background thread
        CoroutineScope(Dispatchers.IO).launch {

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val myResponse: String? = response.body?.string()

                        // To access the TextView, switch to the Main thread
                        CoroutineScope(Dispatchers.Main).launch {
                            binding.textView3.text = myResponse
                        }
                    }
                }
            })
        }
    }
}
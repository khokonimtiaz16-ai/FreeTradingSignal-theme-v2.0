package com.example.freetradingsignal

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class MainActivity : AppCompatActivity() {

    private var tfliteInterpreter: Interpreter? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize local TFLite Model
        try {
            tfliteInterpreter = Interpreter(loadModelFile("signal_model.tflite"))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Setup TradingView WebView
        val webView: WebView = findViewById(R.id.tradingView)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = WebViewClient()

        val htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
                <style>
                    html, body { margin: 0; padding: 0; width: 100%; height: 100%; background-color: #131722; overflow: hidden; }
                    .tradingview-widget-container { width: 100%; height: 100%; }
                </style>
            </head>
            <body>
                <div class="tradingview-widget-container">
                    <div id="tradingview_chart" style="height: 100%; width: 100%;"></div>
                    <script type="text/javascript" src="https://s3.tradingview.com/tv.js"></script>
                    <script type="text/javascript">
                        new TradingView.widget({
                            "autosize": true,
                            "symbol": "OANDA:XAUUSD",
                            "interval": "D",
                            "timezone": "Etc/UTC",
                            "theme": "dark",
                            "style": "1",
                            "locale": "en",
                            "allow_symbol_change": true,
                            "watchlist": ["OANDA:XAUUSD", "FX:USDZAR", "FX:EURUSD"],
                            "container_id": "tradingview_chart"
                        });
                    </script>
                </div>
            </body>
            </html>
        """.trimIndent()

        webView.loadDataWithBaseURL("https://www.tradingview.com", htmlContent, "text/html", "UTF-8", null)
    }

    // Helper function to map TFLite file from assets
    private fun loadModelFile(modelPath: String): MappedByteBuffer {
        val fileDescriptor = assets.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    // Function to run local model inference
    fun predictSignal(inputFeatures: FloatArray): String {
        if (tfliteInterpreter == null) return "Model Not Loaded"

        // Input shape: [1, number_of_features]
        val inputs = arrayOf(inputFeatures)
        
        // Output shape: [1, 3] representing probabilities for [BUY, SELL, HOLD]
        val outputs = Array(1) { FloatArray(3) }

        tfliteInterpreter?.run(inputs, outputs)

        val probabilities = outputs[0]
        val maxIndex = probabilities.indices.maxByOrNull { probabilities[it] } ?: -1

        return when (maxIndex) {
            0 -> "BUY"
            1 -> "SELL"
            else -> "HOLD"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tfliteInterpreter?.close()
    }
}

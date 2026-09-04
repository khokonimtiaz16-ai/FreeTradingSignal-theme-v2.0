package com.example.freetradingsignal

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                    html, body {
                        margin: 0;
                        padding: 0;
                        width: 100%;
                        height: 100%;
                        background-color: #131722;
                        overflow: hidden;
                    }
                    .tradingview-widget-container {
                        width: 100%;
                        height: 100%;
                    }
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
                            "toolbar_bg": "#f1f3f6",
                            "enable_publishing": false,
                            "allow_symbol_change": true,
                            "watchlist": [
                                "OANDA:XAUUSD",
                                "FX:USDZAR",
                                "FX:EURUSD",
                                "FX:GBPUSD",
                                "FX:USDJPY"
                            ],
                            "container_id": "tradingview_chart"
                        });
                    </script>
                </div>
            </body>
            </html>
        """.trimIndent()

        webView.loadDataWithBaseURL("https://www.tradingview.com", htmlContent, "text/html", "UTF-8", null)
    }
}

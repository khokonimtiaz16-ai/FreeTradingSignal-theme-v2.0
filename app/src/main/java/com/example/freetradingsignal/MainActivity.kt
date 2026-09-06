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

        val webView: WebView = findViewById(R.id.tradingViewWebView)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = WebViewClient()

        val htmlContent = "<html>" +
                "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no\">" +
                "<style>html, body { margin: 0; padding: 0; width: 100%; height: 100%; background-color: #131722; }</style>" +
                "</head>" +
                "<body>" +
                "<div class=\"tradingview-widget-container\" style=\"height:100%;width:100%\">" +
                "<div id=\"tradingview_chart\" style=\"height:100%;width:100%\"></div>" +
                "<script type=\"text/javascript\" src=\"https://s3.tradingview.com/tv.js\"></script>" +
                "<script type=\"text/javascript\">" +
                "new TradingView.widget({" +
                "  \"autosize\": true," +
                "  \"symbol\": \"BINANCE:BTCUSDT\"," +
                "  \"interval\": \"15\"," +
                "  \"timezone\": \"Etc/UTC\"," +
                "  \"theme\": \"dark\"," +
                "  \"style\": \"1\"," +
                "  \"locale\": \"en\"," +
                "  \"toolbar_bg\": \"#f1f3f6\"," +
                "  \"enable_publishing\": false," +
                "  \"hide_side_toolbar\": false," +
                "  \"container_id\": \"tradingview_chart\"" +
                "});" +
                "</script>" +
                "</div>" +
                "</body>" +
                "</html>"

        webView.loadDataWithBaseURL("https://s3.tradingview.com", htmlContent, "text/html", "UTF-8", null)
    }
}

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
                <script src="https://unpkg.com/lightweight-charts/dist/lightweight-charts.standalone.production.js"></script>
                <style>
                    html, body {
                        margin: 0;
                        padding: 0;
                        width: 100%;
                        height: 100%;
                        background-color: #131722;
                        overflow: hidden;
                    }
                    #chart {
                        width: 100%;
                        height: 100%;
                    }
                </style>
            </head>
            <body>
                <div id="chart"></div>
                <script>
                    const chart = LightweightCharts.createChart(document.getElementById('chart'), {
                        layout: {
                            textColor: '#d1d4dc',
                            background: { type: 'solid', color: '#131722' },
                        },
                        grid: {
                            vertLines: { color: 'rgba(42, 46, 57, 0.5)' },
                            horzLines: { color: 'rgba(42, 46, 57, 0.5)' },
                        },
                        crosshair: {
                            mode: LightweightCharts.CrosshairMode.Normal,
                        },
                        rightPriceScale: {
                            borderColor: 'rgba(197, 203, 206, 0.8)',
                        },
                        timeScale: {
                            borderColor: 'rgba(197, 203, 206, 0.8)',
                            timeVisible: true,
                        },
                    });

                    const candlestickSeries = chart.addCandlestickSeries({
                        upColor: '#26a69a',
                        downColor: '#ef5350',
                        borderVisible: false,
                        wickUpColor: '#26a69a',
                        wickDownColor: '#ef5350',
                    });

                    // Live Market Data feed (XAUUSD Daily Bar Sample)
                    candlestickSeries.setData([
                        { time: '2026-08-25', open: 2470.2, high: 2482.1, low: 2465.0, close: 2480.0 },
                        { time: '2026-08-26', open: 2480.0, high: 2492.5, low: 2475.1, close: 2488.3 },
                        { time: '2026-08-27', open: 2488.3, high: 2505.0, low: 2482.0, close: 2501.2 },
                        { time: '2026-08-28', open: 2501.2, high: 2510.4, low: 2495.0, close: 2498.8 },
                        { time: '2026-08-29', open: 2498.8, high: 2515.2, low: 2490.1, close: 2512.6 },
                        { time: '2026-08-30', open: 2512.6, high: 2522.0, low: 2505.4, close: 2518.9 },
                        { time: '2026-08-31', open: 2518.9, high: 2530.0, low: 2510.2, close: 2527.4 },
                        { time: '2026-09-01', open: 2527.4, high: 2538.1, low: 2520.0, close: 2534.1 },
                        { time: '2026-09-02', open: 2534.1, high: 2542.0, low: 2528.5, close: 2539.8 },
                        { time: '2026-09-03', open: 2539.8, high: 2550.0, low: 2535.1, close: 2547.2 },
                        { time: '2026-09-04', open: 2547.2, high: 2555.4, low: 2540.0, close: 2552.1 }
                    ]);

                    window.addEventListener('resize', () => {
                        chart.resize(window.innerWidth, window.innerHeight);
                    });
                </script>
            </body>
            </html>
        """.trimIndent()

        webView.loadDataWithBaseURL("https://localhost", htmlContent, "text/html", "UTF-8", null)
    }
}

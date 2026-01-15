package com.example.webview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.obigo.WebSettings
import com.obigo.WebView
import com.obigo.WebViewClient
import java.net.URL


class MainActivity : AppCompatActivity() {
    private var mWebView: WebView? = null
    private var mURL: String? = null

    val TAG: String = "ObigoWebViewExampleActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        mURL = "https://www.w3schools.com/howto/tryit.asp?filename=tryhow_css_login_form_modal";
        mWebView = findViewById(R.id.wvLayout);
        /*
        mWebView!!.settings.javaScriptEnabled = true
        mWebView!!.settings.domStorageEnabled = true
        mWebView!!.settings.useWideViewPort = true
        mWebView!!.settings.loadWithOverviewMode = true
        mWebView!!.settings.allowFileAccess = true
        mWebView!!.settings.allowContentAccess = true
        mWebView!!.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        mWebView!!.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        mWebView!!.settings.loadWithOverviewMode = true
        mWebView!!.settings.mediaPlaybackRequiresUserGesture = false
        mWebView!!.setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_IMPORTANT, false)
        mWebView!!.setInitialScale(100)*/
        mWebView!!.getSettings().setJavaScriptEnabled(true);
        // mWebView.getSettings().setUserAgentString("Mozilla/5.0 (X11; ccNC; Linux aarch64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Safari/537.36");
        mWebView!!.getSettings().setDomStorageEnabled(true);
        mWebView!!.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView!!.getSettings().setSupportMultipleWindows(true);

        mWebView!!.loadUrl(mURL.toString());
        mWebView!!.requestFocus();
        /*mObaJsInterface = ObaJsInterface(mWebView, this)
        mWebView!!.addJavascriptInterface(mObaJsInterface, ObaJsInterface.interfaceName)
        if (mObaJsInterface.isConnectedGamepad()) {
            mObaJsInterface.stopGamepad()
        }*/

        mWebView!!.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, loadedUrl: String) {/*
                try {
                    val sLoadedUrl = URL(loadedUrl)
                    val sRequestUrl = URL(mURL)
                    val loadedHost = sLoadedUrl.host
                    val requestHost = sRequestUrl.host

                    if (loadedHost.contains(requestHost) || requestHost.contains(loadedHost)) {

                    } else {
                        Log.i(
                            TAG,
                            java.lang.String.format(
                                "Load done but loaded: %s url is different request url: %s",
                                loadedUrl,
                                mURL
                            )
                        )
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "[URL parse Error] URL : $mURL, loaded Url : $loadedUrl")
                }*/
            }

            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
            }
        }
    }
}
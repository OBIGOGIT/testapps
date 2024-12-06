package com.roya.customtab

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsIntent.ACTIVITY_HEIGHT_FIXED
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.roya.customtab.ui.theme.CustomtabTheme
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : ComponentActivity() {
    lateinit var context_: Context
    lateinit var btn_ctab_open_: Button
    var agb_installed = false
    lateinit var btn_idt_open_: Button
    lateinit var btn_json_: Button
    lateinit var txtview_log_: TextView
    var isTest = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        context_ = getApplicationContext()
        agb_installed = isPackageInstalled(context_,"com.obigo.automotivebrowser" )

        btn_ctab_open_ = findViewById<Button>(R.id.btn_ctab_open)
        btn_idt_open_ = findViewById<Button>(R.id.btn_idt_open)
        btn_json_ = findViewById<Button>(R.id.btn_idt_json)

        txtview_log_ = findViewById<TextView>(R.id.view_json)
        btn_idt_open_.setOnClickListener {
            openBrsByIntent()
        }
        btn_ctab_open_.setOnClickListener {
            openBrsByCustomTab()
        }
        btn_json_.setOnClickListener {
            txtview_log_.setText(makeIntetJsonData())
        }
    }
    fun makeIntetJsonData() : String {
        val jsonMain = JSONObject()
        val jsonArray = JSONArray()
        var jsonObject = JSONObject();
//hostUrl
        jsonObject.put("hosturl", "https://www.naver.com/")
///zoom factor
        jsonObject.put("zoomFactor", 1)
///user agent
        jsonObject.put("userAgent", "Mozilla/5.0 (X11; ccNC; Linux aarch64) AppleWebKit/537.36 (KHTML' like Gecko) Chrome/92.0.4515.131 Mobile Safari/537.36 ")
        jsonArray.put(jsonObject)
///white list
        var wlist = JSONArray();
        wlist.put("https://watcha.com/");
        wlist.put("https://watcha.com/2");
        jsonObject.put("whiteList",wlist)
        Log.e("ROYA", "json: " + jsonObject.toString());
        return jsonObject.toString();
    }

    fun openBrsByCustomTab(){
        txtview_log_.setText("isChromeEnabled " + agb_installed)
        val uri = Uri.parse("https://rroya.tistory.com")
        val intentBuilder = CustomTabsIntent.Builder().setInitialActivityHeightPx(
            100,
            ACTIVITY_HEIGHT_FIXED
        );
        intentBuilder.setUrlBarHidingEnabled(true)
        //intentBuilder.putExtra("roya", "working.....") not working
        val customTabsIntent = intentBuilder.build()
        customTabsIntent.launchUrl(this, uri)
    }

    fun openBrsByIntent() {
        val i = Intent("com.obigo.automotivebrowser")
        val uri = Uri.parse("https://watcha.com/automobile/intro")
        if(i == null ) {
        } else {
            /*
            {"serviceName":"왓챠","hostUrl":"https://watcha.com/automobile/intro","zoomFactor":1,"userAgent":"Mozilla/5.0 (X11; ccNC; Linux aarch64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Mobile Safari/537.36","whiteList":["https://watcha.com/"]}
            */

            i.setAction(Intent.ACTION_VIEW);
            i.setData(uri);
            i.putExtra("oba.openurl.url","https://rroya.tistory.com/")//KEY_EXTRA_OPENURL_URL
            i.putExtra("oba.openurl.type","1")//KEY_EXTRA_OPENURL_TYPE

            //i.putExtra("oba.openurl.config",config)//KEY_EXTRA_OPENURL_CONFIG
            var w = findViewById<EditText>(R.id.input_width)
            var h = findViewById<EditText>(R.id.input_height)
            if (w.text.toString().isEmpty() || h.text.toString().isEmpty()) {
                Log.e("ROYA", "please input width / height");
            } else {
                var commandline = w.text.toString() + "x" + h.text.toString()
                i.putExtra("agb-content-window-size", commandline.toString())//width,height
            }
            //Log.e("ROYA", "json: " + jsontest());
            //i.putExtra("oba.content.config", jsontest())
            i.putExtra("oba.content.config", makeIntetJsonData())


            startActivity(i);
        }
    }
    fun isPackageInstalled(ctx: Context, packageName: String): Boolean {
        return try {
            txtview_log_.setText("isChromeEnabled " + 40)
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: Exception) {
            Log.e("ROYA", "isPackageInstalled failed");
            false
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CustomtabTheme {
        Greeting("Android")
    }
}
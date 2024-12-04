package com.roya.customtab

//

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsIntent.ACTIVITY_HEIGHT_FIXED
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.roya.customtab.ui.theme.CustomtabTheme

class MainActivity : ComponentActivity() {
    lateinit var context_: Context
    lateinit var btn_ctab_open_: Button
    var agb_installed = false
    lateinit var btn_idt_open_: Button
    lateinit var txtview_log_: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        context_ = getApplicationContext()
        agb_installed = isPackageInstalled(context_,"com.obigo.automotivebrowser" )


        btn_ctab_open_ = findViewById<Button>(R.id.btn_ctab_open)
        btn_idt_open_ = findViewById<Button>(R.id.btn_idt_open)

        txtview_log_ = findViewById<TextView>(R.id.txtview_log)
        btn_idt_open_.setOnClickListener {
            openBrsByIntent()
        }
        btn_ctab_open_.setOnClickListener {
            openBrsByCustomTab()
        }

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
        val uri = Uri.parse("https://brk.obigo.com/launcher-v2")
        if(i == null ) {
        } else {
            i.setAction(Intent.ACTION_VIEW);
            i.setData(uri);
            //i.putExtra(OBAConstants.KEY_EXTRA_DIALOG_MSG, getString(R.string.OBAGB_26000_SharedScreen));
            i.putExtra("roya", "working.....")
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
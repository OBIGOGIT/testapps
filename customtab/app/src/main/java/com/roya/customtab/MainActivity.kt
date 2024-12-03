package com.roya.customtab

//

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsIntent.ACTIVITY_HEIGHT_ADJUSTABLE
import androidx.browser.customtabs.CustomTabsIntent.ACTIVITY_HEIGHT_FIXED
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.roya.customtab.ui.theme.CustomtabTheme


//

class MainActivity : ComponentActivity() {
    lateinit var context_: Context
    lateinit var btn_ctab_open_: Button
    lateinit var btn_idt_open_: Button
    lateinit var txtview_log_: TextView
    lateinit var uri_: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        context_ = getApplicationContext()
        uri_ = Uri.parse("http://rroya.tistory.com/")

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
        var enable = isPackageInstalled(context_,"com.obigo.automotivebrowser" )
        txtview_log_.setText("isChromeEnabled " + enable)
        //

        val intentBuilder = CustomTabsIntent.Builder().setInitialActivityHeightPx(
            100,
            ACTIVITY_HEIGHT_FIXED
        );
        intentBuilder.setToolbarColor(ContextCompat.getColor(this, R.color.teal_200))
        intentBuilder.setSecondaryToolbarColor(
            ContextCompat.getColor(
                this,
                R.color.teal_200
            )
        )
        intentBuilder.setUrlBarHidingEnabled(true)
        val customTabsIntent = intentBuilder.build()
        customTabsIntent.launchUrl(this, uri_)
        //

    }
    fun openBrsByIntent() {
        val i = Intent("com.obigo.automotivebrowser")
        if(i == null ) {
            //txtview_log_.setText("OBIGO", "BOOT brs failed.... not existed ")
        } else {
            //txtview_log_.setText("OBIGO", "BOOT brs load START  ")
            i.setAction(Intent.ACTION_VIEW);
            i.setData(uri_);
            startActivity(i);
        }

    }
    fun isPackageInstalled(ctx: Context, packageName: String): Boolean {
        return try {
            txtview_log_.setText("isChromeEnabled " + 40)
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: Exception) {
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
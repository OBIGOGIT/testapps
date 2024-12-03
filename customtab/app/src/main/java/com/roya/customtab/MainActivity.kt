package com.roya.customtab

import android.content.Context
import android.content.pm.PackageInfo
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.roya.customtab.ui.theme.CustomtabTheme


class MainActivity : ComponentActivity() {
    lateinit var context_: Context
    lateinit var btn_open_: Button
    lateinit var txtview_log_: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        context_ = getApplicationContext()
        btn_open_ = findViewById<Button>(R.id.btn_open)
        txtview_log_ = findViewById<TextView>(R.id.txtview_log)
        btn_open_.setOnClickListener {
            var enable = isPackageInstalled(context_,"com.obigo.automotivebrowser" )
            txtview_log_.setText("isChromeEnabled " + enable)
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
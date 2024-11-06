package com.arin.app

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arin.app.ui.theme.ComarinappTheme

class MainActivity : ComponentActivity() {
    //START values
    var TAG = "ARIN"
    lateinit var context_: Context
    var mom_number_: String = "01095444074"
    var dad_number_: String = "01099597899"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActionBar()!!.setTitle("장아린 전용 앱")
        setContentView(R.layout.main)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.menu_change_bg_color -> {
                Toast.makeText(this, "menu_change_bg_color", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.menu_change_bg_image -> {
                Toast.makeText(this, "menu_change_bg_image", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.menu_image_library -> {
                Toast.makeText(this, "menu_image_library", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.menu_change_sms_text -> {
                Toast.makeText(this, "menu_change_sms_text", Toast.LENGTH_SHORT).show()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
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
    ComarinappTheme {
        Greeting("Android")
    }
}
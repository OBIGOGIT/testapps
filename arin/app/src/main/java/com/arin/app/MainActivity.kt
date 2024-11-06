package com.arin.app

import android.content.Context
import android.os.Bundle
import android.view.Window
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
package com.arin.app

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arin.app.ui.theme.ComarinappTheme
import java.io.File
import java.io.FileNotFoundException
import java.io.BufferedReader
import java.io.FileReader

//import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    var TAG = "ARIN_MAIN"
    lateinit var context_: Context
    var mom_number_: String = "01095444074"
    var dad_number_: String = "01099597899"
    lateinit var view_bg_image_: ImageView
    lateinit var smsText : SmsTextValue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActionBar()!!.setTitle("장아린 전용 앱")
        setContentView(R.layout.main)
        view_bg_image_ = findViewById(R.id.bg)
        context_ = getApplicationContext();
        setImageViewImage(getContext().getFilesDir().getPath() + "/arin_bg.png")
        setBgColor()
        smsText = SmsTextValue()
        smsText.LoadSmsTextValue(getContext().getFilesDir().getPath())

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.menu_change_bg -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.menu_change_sms_text -> {
                val intent = Intent(this, EditSmsActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.menu_image_library -> {
                //Toast.makeText(this, "menu_image_library", Toast.LENGTH_SHORT).show()
                val image = ImageView(this)
                image.setImageResource(R.drawable.library_card);
                val builder: AlertDialog.Builder =
                    AlertDialog.Builder(this)
                builder.setView(image);
                builder.create().show();
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
    fun setBgColor() {
        var main = findViewById(R.id.main_layout) as LinearLayout
        var color = getBtBackgroundColor()
        if(!color.isNullOrBlank()) {
            //Toast.makeText(applicationContext, color, Toast.LENGTH_SHORT).show()
            main.setBackgroundColor(color.toInt());
     //       var btn = findViewById<Button>(R.id.btn_sms1)
     //       btn.setBackgroundColor(color.toInt())
        }
    }
    fun setSmsTextValue() {

    }
    fun getBtBackgroundColor() : String {
        try {
            val inFs = getContext()!!.openFileInput("bg_color.txt")

            val txt = ByteArray(inFs.available()) //byte[]형의 변수 txt를 선언
            inFs.read(txt) //읽어온 데이터를 저장
            val str = String(txt) //txt를 문자열로 변환
            Toast.makeText(applicationContext, str, Toast.LENGTH_SHORT).show()
            Log.d(TAG, "color value " + str)//
            inFs.close()
            return str;
        } catch (e : FileNotFoundException) {
            e.printStackTrace();
        }
        return ""
    }
    fun setImageViewImage(filepath : String) {
        val imgFile = File(filepath)
        if (imgFile.exists()) {
            val imgBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            view_bg_image_.setImageBitmap(imgBitmap)
        }
    }
    fun getContext(): Context {
        return context_
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
package com.roya.customtab

import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView


class JsonConfigListActivity : AppCompatActivity() {
    private lateinit var btnJsonOk: Button
    //private lateinit var JsonConfigData1: JsonConfigData
    private val JsonConfigDataArr = arrayOfNulls<JsonConfigData>(6)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.json_config_list_layout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initListValue()
        DisplayJonList()
    }
    fun DisplayJonList() {
        var btnJson1 = findViewById<Button>(R.id.btn_json_sample1)
        btnJson1.setText(JsonConfigDataArr[1]!!.InfoLog())
    }
    fun initListValue() {

        JsonConfigDataArr[0] = JsonConfigData()
        JsonConfigDataArr[1] = JsonConfigData()
        JsonConfigDataArr[2] = JsonConfigData()
        JsonConfigDataArr[3] = JsonConfigData()
        JsonConfigDataArr[4] = JsonConfigData()
        JsonConfigDataArr[5] = JsonConfigData()


        JsonConfigDataArr[0]!!.setJsonConfigData(
            "1",
            "2",
            "1.5",
            "https://m.youtube.com",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36",
            "https://m.youtube.com;https://accounts.google.com;https://accounts.youtube.com;https://www.google.com/recaptcha;https://youtube.com/signin;https://gds.google.com",
            "",
            "false",
            "false",
            "false"
        );
        JsonConfigDataArr[1]!!.setJsonConfigData("1","2",
            "1.5",
            "https://m.youtube.com",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36",
            "https://m.youtube.com;https://accounts.google.com;https://accounts.youtube.com;https://www.google.com/recaptcha;https://youtube.com/signin;https://gds.google.com",
            "",
            "false",
            "false",
            "false",
        );
        JsonConfigDataArr[2]!!.setJsonConfigData("","",
            "1",
            "https://www.hotstar.com",
            "Mozilla/5.0 (X11; ccNC; Linux aarch64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Mobile Safari/537.36",
            "https://www.hotstar.com/in",
            "",
            "false",
            "false",
            "false",
        );
        JsonConfigDataArr[3]!!.setJsonConfigData("","",
            "2",
            "https://open.spotify.com",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36",
            "https://open.spotify.com;https://accounts.google.com;https://www.facebook.com/login.php?;" +
                    "https://appleid.apple.com/auth/;https://accounts.spotify.com;https://accounts.spotify.com;" +
                    "https://policies.google.com/privacy;https://policies.google.com/terms;https://support.spotify.com;https://www.spotify.com",
            "",
            "false",
            "false",
            "true",
        );
        JsonConfigDataArr[4]!!.setJsonConfigData("","",
            "1",
            "https://music.amazon.com",
            "Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36",
            "https://music.amazon.com;https://www.amazon.com",
            "",
            "false",
            "false",
            "true",
        );
        JsonConfigDataArr[5]!!.setJsonConfigData("","",
            "2",
            "https://m.cricbuzz.com",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36",
            "https://m.cricbuzz.com;https://auth.cricbuzz.com;https://accounts.google.com;https://appleid.apple.com/auth/",
            "",
            "false",
            "false",
            "true",
        );
    }

}
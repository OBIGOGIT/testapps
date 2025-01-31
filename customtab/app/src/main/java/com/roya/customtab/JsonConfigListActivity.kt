package com.roya.customtab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.util.Log

class JsonConfigListActivity : AppCompatActivity() {
    private var tag = "JsonConfigList"
    private lateinit var btnJsonOk: Button
    private val JsonConfigDataArr = arrayOfNulls<JsonConfigData>(6)
    private val JsonConfigDataBtnArr = arrayOfNulls<Button>(6)

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
        AddBtnEvent()
    }
    fun AddBtnEvent() {
        JsonConfigDataBtnArr[0] = findViewById<Button>(R.id.btn_json_sample0)
        JsonConfigDataBtnArr[1] = findViewById<Button>(R.id.btn_json_sample1)
        JsonConfigDataBtnArr[2] = findViewById<Button>(R.id.btn_json_sample2)
        JsonConfigDataBtnArr[3] = findViewById<Button>(R.id.btn_json_sample3)
        JsonConfigDataBtnArr[4] = findViewById<Button>(R.id.btn_json_sample4)
        JsonConfigDataBtnArr[5] = findViewById<Button>(R.id.btn_json_sample5)
        for(i: Int in 0..5) {
            JsonConfigDataBtnArr[i]!!.setOnClickListener {
                Log.e(tag , "clicked! : " + i + " th " + JsonConfigDataArr[i]!!.MakeJsonData())
                SetResIntent(JsonConfigDataArr[i]!!.MakeJsonData());
            }
        }

        var btn_cancel = findViewById<Button>(R.id.btn_json_configlist_cancel)
        btn_cancel.setOnClickListener {
            finish()
        }
    }
    private fun SetResIntent(json_str : String) {
        var intent = Intent(this@JsonConfigListActivity,MainActivity::class.java);
        intent.putExtra("from", "JsonConfigListActivity");
        intent.putExtra("json", json_str);

        setResult(RESULT_OK , intent)
        finish()
    }

    fun DisplayJonList() {
        var Json0 = findViewById<TextView>(R.id.txtview_json_sample0)
        Json0.setText(JsonConfigDataArr[0]!!.InfoLog())

        var Json1 = findViewById<TextView>(R.id.txtview_json_sample1)
        Json1.setText(JsonConfigDataArr[1]!!.InfoLog())

        var Json2 = findViewById<TextView>(R.id.txtview_json_sample2)
        Json2.setText(JsonConfigDataArr[2]!!.InfoLog())

        var Json3 = findViewById<TextView>(R.id.txtview_json_sample3)
        Json3.setText(JsonConfigDataArr[3]!!.InfoLog())

        var Json4 = findViewById<TextView>(R.id.txtview_json_sample4)
        Json4.setText(JsonConfigDataArr[4]!!.InfoLog())

        var Json5 = findViewById<TextView>(R.id.txtview_json_sample5)
        Json5.setText(JsonConfigDataArr[5]!!.InfoLog())

    }
    fun initListValue() {

        JsonConfigDataArr[0] = JsonConfigData()
        JsonConfigDataArr[1] = JsonConfigData()
        JsonConfigDataArr[2] = JsonConfigData()
        JsonConfigDataArr[3] = JsonConfigData()
        JsonConfigDataArr[4] = JsonConfigData()
        JsonConfigDataArr[5] = JsonConfigData()


        JsonConfigDataArr[0]!!.setJsonConfigData(
            "1.0",
            "https://ivi.wavve.com",
            "Mozilla/5.0 (X11; ccIC; Linux aarch64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Mobile Safari/537.36",
            "https://ivi.wavve.com",
            "",
            "false",
            "false",
            "false"
        );
        JsonConfigDataArr[1]!!.setJsonConfigData(
            "1.5",
            "https://m.youtube.com",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36",
            "https://m.youtube.com;https://accounts.google.com;https://accounts.youtube.com;https://www.google.com/recaptcha;" + "\n" +
                    "https://youtube.com/signin;https://gds.google.com",
            "",
            "false",
            "false",
            "false",
        );
        JsonConfigDataArr[2]!!.setJsonConfigData(
            "1",
            "https://www.nate.com/",
            "Mozilla/5.0 (X11; ccNC; Linux aarch64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Mobile Safari/537.36",
            "https://www.nate.com/",
            "",
            "false",
            "false",
            "false",
        );
        JsonConfigDataArr[3]!!.setJsonConfigData(
            "2",
            "https://open.spotify.com",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36",
            "https://open.spotify.com;https://accounts.google.com;" +
                    "https://appleid.apple.com/auth/;https://accounts.spotify.com;" +
                    "https://www.spotify.com",
            "",
            "false",
            "false",
            "true",
        );
        JsonConfigDataArr[4]!!.setJsonConfigData(
            "1.2",
            "https://music.amazon.com",
            "Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36",
            "https://music.amazon.com;https://www.amazon.com",
            "",
            "false",
            "false",
            "true",
        );
        JsonConfigDataArr[5]!!.setJsonConfigData(
            "1.3",
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
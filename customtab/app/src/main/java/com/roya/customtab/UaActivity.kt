package com.roya.customtab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UaActivity : AppCompatActivity() {

    lateinit var btn_ua_config_save_: Button
    lateinit var btn_ua_config_close_: Button
    lateinit var btn_ua_config_reset_: Button
    lateinit var input_ua_config_ua1_: EditText
    lateinit var input_ua_config_ua2_: EditText
    lateinit var input_ua_config_ua3_: EditText
    lateinit var input_ua_config_ua4_: EditText
    lateinit var input_ua_config_ua5_: EditText
    lateinit var input_ua_config_ua6_: EditText
    lateinit var input_ua_config_ua7_: EditText
    lateinit var input_ua_config_ua8_: EditText
    lateinit var input_ua_config_ua9_: EditText
    lateinit var input_ua_config_ua10_: EditText

    lateinit var input_editTextList: Array<EditText>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.ua_layout)
        setEditTextUiArray()
        SetButtonEvent()

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
    }
    fun setEditTextUiArray() {
        var index = 0
        input_editTextList = arrayOf<EditText>(
            findViewById<EditText>(R.id.input_ua_config_1),
            findViewById<EditText>(R.id.input_ua_config_2),
            findViewById<EditText>(R.id.input_ua_config_3),
            findViewById<EditText>(R.id.input_ua_config_4),
            findViewById<EditText>(R.id.input_ua_config_5),
            findViewById<EditText>(R.id.input_ua_config_6),
            findViewById<EditText>(R.id.input_ua_config_7),
            findViewById<EditText>(R.id.input_ua_config_8),
            findViewById<EditText>(R.id.input_ua_config_9),
            findViewById<EditText>(R.id.input_ua_config_10)
        )

        var uaList = UserAgentList.getInstance().getUaList()
        index = 0
        for (ua in uaList) {
            if(ua.isNotEmpty()) {
                input_editTextList[index++].setText(ua)
            }
        }
    }
    fun SetResIntent() {
        var intent = Intent(this@UaActivity,MainActivity::class.java);
        intent.putExtra("from", "UaActivity");
        setResult(RESULT_OK , intent)
    }

    fun SetButtonEvent() {
        btn_ua_config_reset_ = findViewById<Button>(R.id.btn_ua_config_reset)
        btn_ua_config_reset_.setOnClickListener {
            UserAgentList.getInstance().resetUaList()
            SetResIntent()
            finish()
        }

        btn_ua_config_save_ = findViewById<Button>(R.id.btn_ua_config_save)
        btn_ua_config_save_.setOnClickListener {
            Log.e("ROYA", "try ua save")
            var uaList = arrayOf<String>("","","","","","","","","","")
            var index = 0
            for (ua in input_editTextList) {
                if(ua.text.toString().isNotEmpty()) {
                    Log.e("ROYA", "try ua save" + ua.text.toString())
                    uaList[index++] = ua.text.toString()
                }
            }
            UserAgentList.getInstance().SaveUaText(uaList)
            SetResIntent()
            finish()
        }

    }

}
package com.roya.customtab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class UaActivity : AppCompatActivity() {

    private lateinit var btnUaConfigSave: Button
    private lateinit var btnUaConfigReset: Button

    private lateinit var inputEdittextlist: Array<EditText>


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
    private fun setEditTextUiArray() {
        var index = 0
        inputEdittextlist = arrayOf<EditText>(
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
                inputEdittextlist[index++].setText(ua)
            }
        }
    }
    private fun SetResIntent() {
        var intent = Intent(this@UaActivity,MainActivity::class.java);
        intent.putExtra("from", "UaActivity");
        setResult(RESULT_OK , intent)
    }

    private fun SetButtonEvent() {
        btnUaConfigReset = findViewById<Button>(R.id.btn_ua_config_reset)
        btnUaConfigReset.setOnClickListener {
            UserAgentList.getInstance().resetUaList()
            SetResIntent()
            finish()
        }

        btnUaConfigSave = findViewById<Button>(R.id.btn_ua_config_save)
        btnUaConfigSave.setOnClickListener {
            Log.e("ROYA", "try ua save")
            val uaList : Array<String> = emptyArray()
            var index = 0
            for (ua in inputEdittextlist) {
                if(ua.text.toString().isNotEmpty()) {
                    uaList[index++] = ua.text.toString()
                }
            }
            UserAgentList.getInstance().SaveUaText(uaList)
            SetResIntent()
            finish()
        }
    }
}
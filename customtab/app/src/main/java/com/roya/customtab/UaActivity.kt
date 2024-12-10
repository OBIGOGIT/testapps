package com.roya.customtab

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UaActivity : AppCompatActivity() {

    lateinit var btn_ua_config_save_: Button
    lateinit var btn_ua_config_close_: Button
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

    lateinit var input_editTextList: ArrayList<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.ua_layout)
        setEditTextUiArray()

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
    }
    fun setEditTextUiArray() {
        var index = 0
        input_editTextList = arrayListOf<EditText>(
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

}
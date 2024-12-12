package com.roya.customtab

import android.os.Bundle
import android.widget.Button
import android.util.Log
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DefaultConfigActivity : AppCompatActivity() {
    var TAG = "CONFIG"
    lateinit var btn_close_: Button
    lateinit var btn_save_: Button

    lateinit var input_default_config_width_: EditText
    lateinit var input_default_config_height_: EditText
    lateinit var input_default_config_host_url_: EditText
    lateinit var input_default_config_ua_: EditText
    lateinit var input_default_config_zoomfactor_: EditText
    lateinit var input_default_config_white_list_: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.default_config_layout)
        btn_close_ = findViewById<Button>(R.id.btn_default_config_close)
        btn_close_.setOnClickListener {
            Log.e(TAG, "config finish")
            finish()
        }

        btn_save_ = findViewById<Button>(R.id.btn_default_config_save)
        btn_save_.setOnClickListener {
            Log.e(TAG, "config SAVE")

            DefaultConfigData.getInstance().setDataFromUi(input_default_config_width_.text.toString(),
                                                    input_default_config_height_.text.toString(),
                                                    input_default_config_host_url_.text.toString(),
                                                    input_default_config_ua_.text.toString(),
                                                    input_default_config_zoomfactor_.text.toString(),
                                                    input_default_config_white_list_.text.toString())

            DefaultConfigData.getInstance().saveDefaultValueToFile(DefaultConfigData.getInstance().makeIntetJsonData())
        }
        input_default_config_width_ = findViewById<EditText>(R.id.input_default_config_width)
        input_default_config_height_ = findViewById<EditText>(R.id.input_default_config_height)
        input_default_config_host_url_ = findViewById<EditText>(R.id.input_default_config_hosturl)
        input_default_config_ua_ = findViewById<EditText>(R.id.input_default_config_ua)
        input_default_config_zoomfactor_ = findViewById<EditText>(R.id.input_default_config_zoomfactor)
        input_default_config_white_list_ = findViewById<EditText>(R.id.input_default_config_whitelist)


        input_default_config_width_.setText(DefaultConfigData.getInstance().width_)
        input_default_config_height_.setText(DefaultConfigData.getInstance().height_)
        input_default_config_host_url_.setText(DefaultConfigData.getInstance().host_url_)
        input_default_config_ua_.setText(DefaultConfigData.getInstance().user_agent_)
        input_default_config_white_list_.setText(DefaultConfigData.getInstance().white_list_)
        input_default_config_zoomfactor_.setText(DefaultConfigData.getInstance().zoom_factor_)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.config)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
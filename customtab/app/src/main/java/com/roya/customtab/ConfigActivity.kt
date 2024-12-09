package com.roya.customtab

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log
import android.widget.Toast

class ConfigActivity : AppCompatActivity() {
    var TAG = "CONFIG"
    lateinit var btn_close_: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.config_layout)
        //Toast.makeText(this@ConfigActivity, "config ", Toast.LENGTH_SHORT).show()
        btn_close_ = findViewById<Button>(R.id.btn_close)
        btn_close_.setOnClickListener {
            Log.e(TAG, "config finish")
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.config)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
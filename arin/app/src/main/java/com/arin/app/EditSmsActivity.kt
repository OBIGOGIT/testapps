package com.arin.app

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EditSmsActivity : AppCompatActivity() {
    var TAG = "ARIN_EDITSMS"
    lateinit var context_: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        context_ = getApplicationContext();
        setContentView(R.layout.activity_edit_sms)
        var bSmsapply = findViewById<Button>(R.id.btn_smsApply)
        bSmsapply!!.setOnClickListener(View.OnClickListener {
            Log.e(TAG, "user sms text apply")
            EditSmsTextApply()
            finish()
        })
        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
    }
    fun EditSmsTextApply() {
        var sms0 = findViewById<EditText>(R.id.editText_sms0)
        var sms1 = findViewById<EditText>(R.id.editText_sms1)
        var sms2 = findViewById<EditText>(R.id.editText_sms2)
        var sms3 = findViewById<EditText>(R.id.editText_sms3)
        var smsArray = arrayOf(sms0.text.toString(),
                                  sms1.text.toString(),
                                  sms2.text.toString(),
                                  sms3.text.toString())

        SmsTextValue.getInstance().setSmsText(context_, smsArray)
    }
}
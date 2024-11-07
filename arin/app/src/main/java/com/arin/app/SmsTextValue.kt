package com.arin.app

import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
//getContext().getFilesDir().getPath() + "/arin_bg.png"
class SmsTextValue {
    var TAG = "ARIN_SmsTextValue"
    lateinit var smsTextFile_ : String
    var default_smsTextArray:Array<String> = arrayOf("엄마 나 일어났어",
                                     "엄마 어디야?",
                                     "엄마 휴대폰 시간 더 줘")
    lateinit var smsTextArray_ : Array<String>
    fun LoadSmsTextValue(path: String) {
        var file = path + "/smsText.txt"
        Log.d(TAG, "smsText file " + file)
        smsTextFile_ = file
        //getText()
    }
    fun getText() : Array<String> {
        val imgFile = File(smsTextFile_)
        if (!imgFile.exists()) {
            return default_smsTextArray;
        }
        var reader: BufferedReader? = null
        var i : Int = 0
        try {
            reader = BufferedReader(FileReader(smsTextFile_))
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                // Process each line
                Log.d(TAG, "smsText " + i + " : " + line)
                smsTextArray_[i++] = line.toString()
            }
        } catch (e: Exception) {
            Log.e(TAG, "smsText ERROR ${e.message}")
        } finally {
            try {
                reader?.close()
            } catch (e: Exception) {
                Log.e(TAG, "smsText ERROR:An error occurred while closing the file: ${e.message}")
            }
        }
        if (i == 0)
            return default_smsTextArray;
        else
            return smsTextArray_
    }
}
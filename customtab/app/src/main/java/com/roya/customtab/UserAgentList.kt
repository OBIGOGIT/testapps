package com.roya.customtab

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.PrintWriter

class UserAgentList {
    var TAG = "UA"
    lateinit var context_: Context
    lateinit var ua_list_file_ : String
    var default_UaArray:Array<String> = arrayOf("Mozilla/5.0 (X11; ccNC; Linux aarch64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Mobile Safari/537.36",
                                                "Mozilla/5.0 (X11; ccIC; Linux aarch64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Mobile Safari/537.36",
                                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36",
                                                "Mozilla/5.0 (X11; CrOS x86_64 14526.89.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.133 Safari/537.36",
                                                "Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30",
                                                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36"
        )
    companion object {
        private var instance: UserAgentList? = null

        fun getInstance() =
            instance ?: UserAgentList().also {
                instance = it
            }
    }
    fun Initialize(path: String, context: Context) {
        Log.e("ROYA" , "Initialize")
        context_ = context
        var file = path + "/ua" + ".txt"
        ua_list_file_ = file
    }
    fun getUaList() : Array<String> {
        val file = File(ua_list_file_)
        if (!file.exists()) {
            return default_UaArray;
        }
        var uaArray = arrayOf<String>()

        var reader: BufferedReader? = null
        var i : Int = 0
        try {
            reader = BufferedReader(FileReader(ua_list_file_))
            var line: String

            while (reader.readLine().also { line = it } != null) {
                // Process each line
                Log.d(TAG, "ua: " + i + " : " + line)
                uaArray[i++] = line.toString()
            }
        } catch (e: Exception) {
            Log.e(TAG, "ua read ERROR ${e.message}")
        } finally {
            try {
                reader?.close()
            } catch (e: Exception) {
                Log.e(TAG, "ua  ERROR: while closing the file: ${e.message}")
            }
        }
        if (i == 0)
            return default_UaArray;
        else
            return uaArray
    }
    fun SaveUaText(uaArr :Array<String>) {
        val fos = context_.openFileOutput("ua" + ".txt", Context.MODE_PRIVATE)
        val out = PrintWriter(fos)
        for (s in uaArr) {
            Log.e(TAG, "save ua : " + s)
        }
        out.close();

    }
}
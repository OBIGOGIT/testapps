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
    var default_UaArray:Array<String> = arrayOf("ua 1",
        "ua 2",
        "ua 3",
        "ua 4")
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
    fun getText() : Array<out String?> {
        val file = File(ua_list_file_)
        if (!file.exists()) {
            return default_UaArray;
        }
        val uaArray = arrayOfNulls<String>(4)

        var reader: BufferedReader? = null
        var i : Int = 0
        try {
            reader = BufferedReader(FileReader(ua_list_file_))
            var line: String?

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
    fun setSmsText(smsArr :Array<String>) {
        val fos = context_.openFileOutput("ua" + ".txt", Context.MODE_PRIVATE)
        val out = PrintWriter(fos)
        for (s in smsArr) {
            out.println(s);
        }
        out.close();

    }
}
package com.roya.customtab

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
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
        ua_list_file_ = path + "/ua" + ".txt"
    }

    fun getUaList() : Array<String> {
        val file = File(ua_list_file_)
        if (!file.exists()) {
            Log.e("ROYA","default_UaArray")
            return default_UaArray;
        }
        var index = 0
        var uaList = arrayOf<String>("","","","","","","","","","")
        try {
            BufferedReader(FileReader(file)).use { br ->
                var line: String?
                while (br.readLine().also { line = it } != null) {
                    //println(line)
                    uaList[index++] = line.toString()
                    Log.e("ROYA","getline :" +line)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return uaList
    }
    fun SaveUaText(uaArr :Array<String>) {
        var uafile =  File(ua_list_file_)
        uafile.delete()
        uafile.createNewFile()
        val fileWriter = FileWriter(uafile)
        for (s in uaArr) {
            //uafile.writeText(s)
            if(s.isNotEmpty()) {
                fileWriter.write(s + System.lineSeparator())
                //fileWriter.write()
                Log.e("ROYA", "file Write : " + s)
            }
        }
        fileWriter.close()
        /*
        Log.e("ROYA","ua_list_file_  : "+ua_list_file_)
        val fos = context_.openFileOutput("ua" + ".txt", Context.MODE_PRIVATE)
        val out = PrintWriter(fos)
        for (s in uaArr) {
            Log.e(TAG, "save ua : " + s)
            out.println(s);
        }
        out.close();
    */}
}
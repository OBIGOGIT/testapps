package com.roya.customtab
import android.view.View

import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.io.PrintWriter

class SettingData {
    lateinit var context_: Context
    lateinit var default_value_file_ : String


    lateinit var user_agent_: String
    lateinit var host_url_: String
    lateinit var zoom_factor_: String
    lateinit var width_: String
    lateinit var height_: String
    lateinit var white_list_: String

    companion object {
        private var instance: SettingData? = null
        fun getInstance() =
            instance ?: SettingData().also {
                instance = it
            }
    }

    fun Initialize(path: String, context: Context) {
        Log.e("ROYA" , "Initialize")
        context_ = context
        default_value_file_ = path + "/default" + ".txt"

    }

    fun Wihtelist(input : String) : List<String> {
        val splitData = input.split(";")
        return splitData
    }
    /*
        input_default_config_width_ = findViewById<EditText>(R.id.input_default_config_width)
        input_default_config_height_ = findViewById<EditText>(R.id.input_default_config_height)
        input_default_config_host_url_ = findViewById<EditText>(R.id.input_default_config_hosturl)
        input_default_config_ua_ = findViewById<EditText>(R.id.input_default_config_ua)
        input_default_config_zoomfactor_ = findViewById<EditText>(R.id.input_default_config_zoomfactor)
*/
    fun setDataFromUi(width:String ,height:String,hurl:String,ua:String,zoom:String,wlist:String) {
        width_ = width;
        height_ = height;
        host_url_ = hurl
        zoom_factor_ = zoom
        white_list_ = wlist
    }
    fun makeIntetJsonData() : String {
        val jsonMain = JSONObject()
        val jsonArray = JSONArray()
        var jsonObject = JSONObject();
//hostUrl
        jsonObject.put("hosturl", host_url_.toString())
///zoom factor
        jsonObject.put("zoomFactor", zoom_factor_.toString())
///user agent
        jsonObject.put("userAgent", user_agent_.toString())
        jsonArray.put(jsonObject)
///white list
        Log.e("ROYA", "white list string: " + white_list_.toString())
        var user_wlist = Wihtelist(white_list_.toString())
        var wlist = JSONArray();

        for(w in user_wlist) {
            wlist.put(w.toString());
        }
        jsonObject.put("whiteList",wlist)
        return jsonObject.toString();
    }
    fun saveDefaultValue(default :String) {
        var uafile =  File(default_value_file_)
        uafile.delete()
        uafile.createNewFile()
        val fileWriter = FileWriter(uafile)
        fileWriter.write(default + System.lineSeparator())
        Log.e("ROYA", "saveDefaultValue : " + default)
        fileWriter.close()
    }
    fun resetDefaultValue() {
        var uafile =  File(default_value_file_)
        uafile.delete()
    }
    fun getDefaultValueFromFile() {
        var file =  File(default_value_file_)
        try {
            BufferedReader(FileReader(file)).use { br ->
                var line: String?
                while (br.readLine().also { line = it } != null) {
                    Log.e("ROYA","getline :" + line.toString())
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}
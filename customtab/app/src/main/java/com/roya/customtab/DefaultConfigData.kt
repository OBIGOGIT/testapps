package com.roya.customtab



import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

class DefaultConfigData {
    private var tag = "DefaultConfig"
    private lateinit var context_: Context
    lateinit var default_value_file_ : String
    lateinit var user_agent_: String
    lateinit var host_url_: String
    lateinit var zoom_factor_: String
    lateinit var width_: String
    lateinit var height_: String
    lateinit var white_list_: String

    companion object {
        private var instance: DefaultConfigData? = null
        fun getInstance() =
            instance ?: DefaultConfigData().also {
                instance = it
            }
    }

    fun Initialize(path: String, context: Context) {
        context_ = context
        default_value_file_ = path + "/default" + ".txt"
        width_ = "700";
        height_ = "500";
        host_url_ = ""
        zoom_factor_ = "1"
        white_list_ = ""
        user_agent_ = ""

        readDefaultValueFromFile()
    }

    private fun WihtelistArray(input : String) : List<String> {
        val splitData = input.split(";")
        return splitData
    }

    fun setDataFromUi(width:String ,height:String,hurl:String,ua:String,zoom:String,wlist:String) {
        width_ = width;
        height_ = height;
        host_url_ = hurl
        zoom_factor_ = zoom
        white_list_ = wlist
        user_agent_ = ua
    }

    fun makeJsonData() : String {
        val jsonMain = JSONObject()
        val jsonArray = JSONArray()
        var jsonObject = JSONObject();

        if(width_.toString().isNotEmpty())
            jsonObject.put("width", width_.toString())
        if(height_.toString().isNotEmpty())
            jsonObject.put("height", height_.toString())
        if(host_url_.toString().isNotEmpty())
            jsonObject.put("hosturl", host_url_.toString())
        if(zoom_factor_.toString().isNotEmpty())
            jsonObject.put("zoomFactor", zoom_factor_.toString())
        if(user_agent_.toString().isNotEmpty())
            jsonObject.put("userAgent", user_agent_.toString())
        jsonArray.put(jsonObject)

        if(white_list_.toString().isNotEmpty()) {
            var user_wlist = WihtelistArray(white_list_.toString())
            var wlist = JSONArray();

            for (w in user_wlist) {
                wlist.put(w.toString());
            }
            jsonObject.put("whiteList", wlist)
        }
        return jsonObject.toString();
    }
    fun saveDefaultValueToFile(default :String) {
        var uafile =  File(default_value_file_)
        uafile.delete()
        uafile.createNewFile()
        val fileWriter = FileWriter(uafile)
        fileWriter.write(default + System.lineSeparator())
        fileWriter.close()
    }

    fun readDefaultValueFromFile() {
        val file = File(default_value_file_)
        if (!file.exists()) {
            Log.e("ROYA","file is not exist..return default_value_file_")
            return;
        }
        var data = file.readText(Charsets.UTF_8)
        parsingJsonData(data)
    }
    fun parsingJsonData(json : String) {
        val jsonObject = JSONObject(json)
        if(jsonObject.has("hosturl")) {
            host_url_ = jsonObject["hosturl"].toString()
        }
        if(jsonObject.has("zoomFactor")) {
            zoom_factor_ = jsonObject["zoomFactor"].toString()
        }
        if(jsonObject.has("userAgent")) {
            user_agent_ = jsonObject["userAgent"].toString()
        }
        if(jsonObject.has("whiteList")) {
            white_list_ = ""
            val wlist = jsonObject.getJSONArray("whiteList")
            for (i in 0..wlist.length() - 1) {
                var w = wlist[i].toString()
                white_list_ = white_list_ + w + ";"
            }
        }

        if(jsonObject.has("width")) {
            width_ = jsonObject["width"].toString()
        }

        if(jsonObject.has("height")) {
            height_ = jsonObject["height"].toString()
        }
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
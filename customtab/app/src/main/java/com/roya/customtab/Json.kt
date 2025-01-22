package com.roya.customtab

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject


class JsonConfigData {
    private var tag = "DefaultConfig"

    lateinit var zoom_factor_: String
    lateinit var url_: String
    lateinit var user_agent_: String
    lateinit var white_list_: String
    lateinit var block_list_: String
    lateinit var force_cale_: String
    lateinit var embed_cookie_: String
    lateinit var mobile_page_: String

    fun JsonConfigData() {
        force_cale_ = "false"
        embed_cookie_ = "false"
        mobile_page_ = "false"
    }

    fun setJsonConfigData(zoom_factor : String,
                      url : String,
                      user_agent : String,
                      white_list : String,
                      block_list : String,
                      force_cale : String,
                      embed_cookie: String,
                      mobile_page : String) {
        zoom_factor_ = zoom_factor
        url_ = url
        user_agent_ = user_agent
        white_list_ = white_list
        block_list_ = block_list
        force_cale_ = force_cale
        embed_cookie_ = embed_cookie
        mobile_page_ = mobile_page
    }

    fun setJsonConfigUiData(json_str : String) {
        val jsonObject = JSONObject(json_str)
        url_ = jsonObject["hosturl"].toString()
        zoom_factor_ = jsonObject["zoomFactor"].toString()
        user_agent_ = jsonObject["userAgent"].toString()
        force_cale_ = jsonObject["forceScale"].toString()
        embed_cookie_ = jsonObject["embedCookie"].toString()
        mobile_page_ = jsonObject["mobilePage"].toString()
        white_list_ = ""
        val jsonArray = jsonObject.getJSONArray("whiteList")
        for (i in 0 until jsonArray.length()) {
            white_list_ = white_list_ + jsonArray[i].toString() + ";"
        }

    }
    private fun WihtelistArray(input : String) : List<String> {
        val splitData = input.split(";")
        return splitData
    }
    fun MakeJsonData() : String {
//        if (!checkInputData()) {
//            return "data missing";
//        }
        val jsonMain = JSONObject()
        val jsonArray = JSONArray()
        var jsonObject = JSONObject();
        /*hostUrl*/
        if(url_.isNotEmpty())
            jsonObject.put("hosturl", url_.toString())
        /*zoom factor*/
        if(zoom_factor_.isNotEmpty())
            jsonObject.put("zoomFactor", zoom_factor_.toString().toFloat())

        /*force scale */
        if (force_cale_.isNotEmpty()) {
            jsonObject.put("forceScale", force_cale_.toString())
        } else {
            jsonObject.put("forceScale", "false")
        }
        /*embeded cookie */
        if (embed_cookie_.isNotEmpty()) {
            jsonObject.put("embedCookie", embed_cookie_.toString())
        } else {
            jsonObject.put("embedCookie", "false")
        }
        /*mobile Page */
        if (mobile_page_.isNotEmpty()) {
            jsonObject.put("mobilePage", mobile_page_.toString())
        } else {
            jsonObject.put("mobilePage", "false")
        }

        /*user agent*/
        if(user_agent_.isNotEmpty())
            jsonObject.put("userAgent", user_agent_.toString())

        jsonArray.put(jsonObject)
        /*white list*/
        Log.e(tag, "white list string: " + white_list_.toString())
        var user_wlist = WihtelistArray(white_list_.toString())
        var wlist = JSONArray();

        for(w in user_wlist) {
            if(w.isNotEmpty())
                wlist.put(w.toString());
        }
        jsonObject.put("whiteList",wlist)
        return jsonObject.toString();

    }
    fun InfoLog() : String {
        var log_str = "url : " + url_  +  "\n" +
                 "zoom factor : " + zoom_factor_  + "\n" +
                "user agent : " + user_agent_ + "\n" +
                "white list : " + white_list_ + "\n" +
                //"block list : " + block_list_  + "\n" +
                "force scale : " + force_cale_ + " / " +
                "embeded cookie : " + embed_cookie_+ " / " +
                "mobile page : " + mobile_page_;
        return log_str;
    }
}
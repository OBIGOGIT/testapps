package com.roya.customtab


class JsonConfigData {
    lateinit var width_: String
    lateinit var height_: String
    lateinit var zoom_factor_: String
    lateinit var url_: String
    lateinit var user_agent_: String
    lateinit var white_list_: String
    lateinit var block_list_: String
    lateinit var force_cale_: String
    lateinit var embed_cookie_: String
    lateinit var mobile_page_: String

    fun setUIJsonData(width : String,
                      height : String,
                      zoom_factor : String,
                      url : String,
                      user_agent : String,
                      white_list : String,
                      block_list : String,
                      force_cale : String,
                      embed_cookie: String,
                      mobile_page : String) {
        width_ = width
        height_ = height
        zoom_factor_ = zoom_factor
        url_ = url
        user_agent_ = user_agent
        white_list_ = white_list
        block_list_ = block_list
        force_cale_ = force_cale
        embed_cookie_ = embed_cookie
        mobile_page_ = mobile_page
    }

    fun parsingJsonData(json : JsonConfigData) {

    }

    fun MakeJsonData(json : JsonConfigData) {

    }
    fun InfoLog() : String {
        var log_str = "width : " + width_ + "\n" +
                "height : " + height_  + "\n" +
                "zoom factor : " + zoom_factor_  + "\n" +
                "url : " + url_  + "\n" +
                "user agent : " + user_agent_  + "\n" +
                "white list : " + white_list_  + "\n" +
                "block list : " + block_list_  + "\n" +
                "force scale : " + force_cale_  + "\n" +
                "embeded cookie : " + embed_cookie_  + "\n" +
                "mobile page : " + mobile_page_;
        return log_str;
    }
}
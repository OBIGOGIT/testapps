package com.roya.customtab


class JsonConfigData {
    lateinit var width_: String
    lateinit var height_: String
    lateinit var zoom_factor_: String
    lateinit var url_: String
    lateinit var user_agent_: String
    lateinit var white_list_: String
    lateinit var block_list_: String
    lateinit var forces_cale_: String
    lateinit var embed_cookie_: String
    lateinit var mobile_page_: String



    fun setUIJsonData(width : String,
                      height : String,
                      zoom_factor : String,
                      url : String,
                      user_agent : String,
                      white_list : String,
                      block_list : String,
                      forces_cale : String,
                      embed_cookie: String,
                      mobile_page : String) {
        width_ = width
        height_ = height
        zoom_factor_ = zoom_factor
        url_ = url
        user_agent_ = user_agent
        white_list_ = white_list
        block_list_ = block_list
        forces_cale_ = forces_cale
        embed_cookie_ = embed_cookie
        mobile_page_ = mobile_page
    }

    fun parsingJsonData(json : JsonConfigData) {

    }
    fun MakeJsonData(json : JsonConfigData) {

    }
}
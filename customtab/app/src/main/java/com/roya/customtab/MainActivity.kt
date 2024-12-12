package com.roya.customtab

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsIntent.ACTIVITY_HEIGHT_FIXED
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.roya.customtab.ui.theme.CustomtabTheme
import org.json.JSONArray
import org.json.JSONObject
//for config

import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.ActivityResultLauncher

class MainActivity : ComponentActivity() {
    var TAG = "MAIN"
    lateinit var context_: Context
    lateinit var btn_load_default_setting_: Button
    var agb_installed = false
    lateinit var btn_idt_open_: Button
    lateinit var btn_json_: Button
    lateinit var txtview_log_: TextView

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    //edittexts
    lateinit var input_width_: EditText
    lateinit var input_height_: EditText
    //lateinit var input_ua_: EditText
    lateinit var input_zoomFactor_: EditText
    lateinit var input_hosturl_: EditText
    lateinit var input_whitelist_: EditText
    lateinit var user_agent_:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        context_ = getApplicationContext()
        agb_installed = isPackageInstalled(context_,"com.obigo.automotivebrowser" )
        btn_load_default_setting_ = findViewById<Button>(R.id.btn_load_default_setting)
        btn_idt_open_ = findViewById<Button>(R.id.btn_idt_open)
        btn_json_ = findViewById<Button>(R.id.btn_idt_json)
        input_hosturl_ = findViewById<EditText>(R.id.input_hosturl)
        input_zoomFactor_ = findViewById<EditText>(R.id.input_zoomfactor)
        input_whitelist_ = findViewById<EditText>(R.id.input_whitelist)
        txtview_log_ = findViewById<TextView>(R.id.view_json)
        Log.e("TAG" , "path : " + context_.getFilesDir().getPath())
        UserAgentList.getInstance().Initialize(context_.getFilesDir().getPath(), context_)
        DefaultConfigData.getInstance().Initialize(context_.getFilesDir().getPath(), context_)

        user_agent_ = UserAgentList.getInstance().getUaList()[0]

        setDefaultValues()
        btn_idt_open_.setOnClickListener {
            openBrsByIntent()
        }
        btn_load_default_setting_.setOnClickListener {
            Log.e("ROYA", "TODO load default setting value")
            DefaultConfigData.getInstance().readDefaultValueFromFile()
            input_hosturl_.setText(DefaultConfigData.getInstance().host_url_)
            input_zoomFactor_.setText(DefaultConfigData.getInstance().zoom_factor_)
            input_whitelist_.setText("")
            input_whitelist_.setText(DefaultConfigData.getInstance().white_list_)
            user_agent_ = DefaultConfigData.getInstance().user_agent_
            input_width_.setText(DefaultConfigData.getInstance().width_)
            input_height_.setText(DefaultConfigData.getInstance().height_)

            //user_agent_.setText(DefaultConfigData.getInstance().user_agent_)
            //val spinner = findViewById<Spinner>(R.id.spinner)
            //spinner.

            input_hosturl_.setText(DefaultConfigData.getInstance().host_url_)
            input_hosturl_.setText(DefaultConfigData.getInstance().host_url_)

        }
        btn_json_.setOnClickListener {
            txtview_log_.setText(makeIntetJsonData())
        }
        setResultSignUp()
        findViewById<Button>(R.id.btn_config).setOnClickListener {
            DefaultConfigData.getInstance().setDataFromUi(input_width_.text.toString(),
                input_height_.text.toString(),
                input_hosturl_.text.toString(),
                user_agent_,
                input_zoomFactor_.text.toString(),
                input_whitelist_.text.toString())
            DefaultConfigData.getInstance().saveDefaultValueToFile(DefaultConfigData.getInstance().makeIntetJsonData())
        }
        findViewById<Button>(R.id.btn_ua_config).setOnClickListener {
            val intent = Intent(this, UaActivity::class.java)
            resultLauncher.launch(intent)
        }
        setUaSpinner()
    }
    fun setUaSpinner () {
        val spinner = findViewById<Spinner>(R.id.spinner)
        var spinnerItems = UserAgentList.getInstance().getUaList()


        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = spinnerItems[position]
                user_agent_ = selectedItem;
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }

        }
    }
    private fun setResultSignUp() {
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == RESULT_OK) {
                val name = result.data?.getStringExtra("from") ?: ""
                Log.d("ROYA", "setResultSignUp " + name)
                if( name == "UaActivity") {
                    setUaSpinner()
                }
            }
        }
    }

    fun setDefaultValues() {
        val display = this.applicationContext?.resources?.displayMetrics
        val deviceWidth = display?.widthPixels
        val deviceHeight = display?.heightPixels

        input_width_ = findViewById<EditText>(R.id.input_width)
        input_width_.setText(deviceWidth.toString())

        input_height_ = findViewById<EditText>(R.id.input_height)
        input_height_.setText(deviceHeight.toString())

        //input_ua_.setText("Mozilla/5.0 (X11; ccNC; Linux aarch64) AppleWebKit/537.36 (KHTML' like Gecko) Chrome/92.0.4515.131 Mobile Safari/537.36 ")
    }
    fun Wihtelist(input : String) : List<String> {
        val splitData = input.split(";")
        return splitData
    }
    fun checkInputData() :Boolean {
        if(input_zoomFactor_.text.isEmpty() ||
            //input_ua_.text.isEmpty()||
            input_hosturl_.text.isEmpty()||
            input_whitelist_.text.isEmpty())
            return false;
        return true
    }
    fun makeIntetJsonData() : String {
        val jsonMain = JSONObject()
        val jsonArray = JSONArray()
        var jsonObject = JSONObject();
//hostUrl
        jsonObject.put("hosturl", input_hosturl_.text.toString())
///zoom factor
        jsonObject.put("zoomFactor", input_zoomFactor_.text.toString().toInt())
///user agent
        jsonObject.put("userAgent", user_agent_.toString())
        jsonArray.put(jsonObject)
///white list
        Log.e("ROYA", "white list string: " + input_whitelist_.text.toString())
        var user_wlist = Wihtelist(input_whitelist_.text.toString())
        var wlist = JSONArray();

        for(w in user_wlist) {
            wlist.put(w.toString());
        }
        jsonObject.put("whiteList",wlist)
        return jsonObject.toString();
    }

    fun openBrsByCustomTab(){
        txtview_log_.setText("isChromeEnabled " + agb_installed)
        val uri = Uri.parse("https://rroya.tistory.com")
        val intentBuilder = CustomTabsIntent.Builder().setInitialActivityHeightPx(
            100,
            ACTIVITY_HEIGHT_FIXED
        );
        intentBuilder.setUrlBarHidingEnabled(true)
        //intentBuilder.putExtra("roya", "working.....") not working
        val customTabsIntent = intentBuilder.build()
        customTabsIntent.launchUrl(this, uri)
    }

    fun openBrsByIntent() {
        if (!checkInputData()) {
            Toast.makeText(this@MainActivity, "미입력있음", Toast.LENGTH_SHORT).show()
            return;
        }
        val i = Intent("com.obigo.automotivebrowser")
        val uri = Uri.parse(input_hosturl_.text.toString())
        if(i == null ) {
        } else {
            /*
            {"serviceName":"왓챠","hostUrl":"https://watcha.com/automobile/intro","zoomFactor":1,"userAgent":"Mozilla/5.0 (X11; ccNC; Linux aarch64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Mobile Safari/537.36","whiteList":["https://watcha.com/"]}
            */

            i.setAction(Intent.ACTION_VIEW);
            i.setData(uri);
            i.putExtra("oba.openurl.url",input_hosturl_.text.toString())//KEY_EXTRA_OPENURL_URL
            i.putExtra("oba.openurl.type","1")//KEY_EXTRA_OPENURL_TYPE

            //i.putExtra("oba.openurl.config",config)//KEY_EXTRA_OPENURL_CONFIG
            if (input_width_.text.toString().isEmpty() || input_height_.text.toString().isEmpty()) {
                Log.e("ROYA", "please input width / height");
            } else {
                var commandline = input_width_.text.toString() + "x" + input_height_.text.toString()
                i.putExtra("agb-content-window-size", commandline.toString())//width,height
            }

            var json = makeIntetJsonData();
            txtview_log_.setText(json)
            i.putExtra("oba.content.config", json)
            startActivity(i);
        }
    }
    fun isPackageInstalled(ctx: Context, packageName: String): Boolean {
        return try {
            txtview_log_.setText("isChromeEnabled " + 40)
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: Exception) {
            Log.e("ROYA", "isPackageInstalled failed");
            false
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CustomtabTheme {
        Greeting("Android")
    }
}
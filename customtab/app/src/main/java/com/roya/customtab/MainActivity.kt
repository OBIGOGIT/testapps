package com.roya.customtab

/*
<Spinner
android:id="@+id/spinner"
android:layout_width="match_parent"
android:layout_height="wrap_content"/>

import android.widget.Spinner*/
//for config

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsIntent.ACTIVITY_HEIGHT_FIXED
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.roya.customtab.ui.theme.CustomtabTheme
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader


class MainActivity : ComponentActivity() {
    private var tag = "MAIN"
    private lateinit var btnLoadDefaultSetting: Button
    private lateinit var btnJson: Button
    private lateinit var btn_run_brs: Button
    private lateinit var txtView_encoded_json_text: TextView

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private lateinit var inputWidth: EditText
    private lateinit var inputHeight: EditText
    private lateinit var inputZoomfactor: EditText
    private lateinit var inputHosturl: EditText
    private lateinit var inputWhitelist: EditText
    private lateinit var inputUserAgent: EditText
    private lateinit var inputBrsCommandline: EditText
    private lateinit var mobilePage: CheckBox
    private lateinit var forceScale: CheckBox
    private lateinit var embededCookie: CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        var context = applicationContext
        btnLoadDefaultSetting = findViewById<Button>(R.id.btn_load_default_setting)
        btn_run_brs = findViewById<Button>(R.id.btn_run_brs)
        btnJson = findViewById<Button>(R.id.btn_idt_json)
        inputHosturl = findViewById<EditText>(R.id.input_hosturl)
        inputZoomfactor = findViewById<EditText>(R.id.input_zoomfactor)
        inputWhitelist = findViewById<EditText>(R.id.input_whitelist)
        txtView_encoded_json_text = findViewById<TextView>(R.id.txtView_encoded_json_text)
        inputUserAgent = findViewById<EditText>(R.id.input_user_agent)
        inputBrsCommandline = findViewById<EditText>(R.id.input_brs_commandline)
        mobilePage = findViewById<CheckBox>(R.id.checkbox_mobilePage)
        forceScale = findViewById<CheckBox>(R.id.checkbox_forceScale)
        embededCookie = findViewById<CheckBox>(R.id.checkbox_embededCookie)

        Log.e(tag , "path : " + (context.filesDir.path))

        UserAgentList.getInstance().Initialize(context.filesDir.path, context)
        DefaultConfigData.getInstance().Initialize(context.filesDir.path, context)
        setDefaultValues()

        btnLoadDefaultSetting.setOnClickListener {
            DefaultConfigData.getInstance().readDefaultValueFromFile()
            inputHosturl.setText(DefaultConfigData.getInstance().host_url_)
            inputZoomfactor.setText(DefaultConfigData.getInstance().zoom_factor_)
            //inputWhitelist.setText("")
            inputWhitelist.setText(DefaultConfigData.getInstance().white_list_)
            inputUserAgent.setText(DefaultConfigData.getInstance().user_agent_)
            inputWidth.setText(DefaultConfigData.getInstance().width_)
            inputHeight.setText(DefaultConfigData.getInstance().height_)

            inputHosturl.setText(DefaultConfigData.getInstance().host_url_)
            inputHosturl.setText(DefaultConfigData.getInstance().host_url_)

        }
        btnJson.setOnClickListener {
            txtView_encoded_json_text.text = makeIntentJsonData()
        }
        btn_run_brs.setOnClickListener {
            openBrsByIntent()
        }

        setResultSignUp()
        findViewById<Button>(R.id.btn_config_save_as_default).setOnClickListener {
            DefaultConfigData.getInstance().setDataFromUi(inputWidth.text.toString(),
                inputHeight.text.toString(),
                inputHosturl.text.toString(),
                inputUserAgent.text.toString(),
                inputZoomfactor.text.toString(),
                inputWhitelist.text.toString())
            DefaultConfigData.getInstance().saveDefaultValueToFile(DefaultConfigData.getInstance().makeJsonData())
        }
        /*
Should be add xml
    <Button
        android:id="@+id/btn_ua_config"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="ua config" />


        findViewById<Button>(R.id.btn_ua_config).setOnClickListener {
            val intent = Intent(this, UaActivity::class.java)
            resultLauncher.launch(intent)
        }

         */
        findViewById<Button>(R.id.btn_json_list).setOnClickListener {
            val intent = Intent(this, JsonConfigListActivity::class.java)
            resultLauncher.launch(intent)
        }
        //setUaSpinner()
    }
    /*
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
                inputUserAgent.setText(selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }

        }
    }*/
    private fun setResultSignUp() {
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == RESULT_OK) {
                val name = result.data?.getStringExtra("from") ?: ""
                Log.d(tag, "setResultSignUp " + name)
                if (name == "UaActivity") {
                    //setUaSpinner()
                } else if (name == "JsonConfigListActivity") {
                    val json = result.data?.getStringExtra("json") ?: ""
                    Log.d(tag, ">>>> setResultSignUp  : JsongConfigListActivity")
                    Log.d(tag, ">>>>receive!! " + json)
                    SetFillMainUIData(json)
                }
            }
        }
    }
    fun SetFillMainUIData(json_str : String) {
        var uiData = JsonConfigData()
        uiData.setJsonConfigUiData(json_str)
        inputHosturl.setText(uiData.url_)
        inputZoomfactor.setText(uiData.zoom_factor_)
        //inputWhitelist.setText(uiData.white_list_)
        inputUserAgent.setText(uiData.user_agent_)
        inputWhitelist.setText(uiData.white_list_)
        if(uiData.mobile_page_ == "true")
            mobilePage.setChecked(true);
        else
            mobilePage.setChecked(false);
        if(uiData.embed_cookie_ == "true")
            embededCookie.setChecked(true);
        else
            embededCookie.setChecked(false);

        if(uiData.force_cale_ == "true")
            forceScale.setChecked(true);
        else
            forceScale.setChecked(false);
        txtView_encoded_json_text.setText("")
    }

    fun setDefaultValues() {
        val display = this.applicationContext?.resources?.displayMetrics
        val deviceWidth = display?.widthPixels
        val deviceHeight = display?.heightPixels

        inputWidth = findViewById<EditText>(R.id.input_width)
        inputWidth.setText(deviceWidth.toString())

        inputHeight = findViewById<EditText>(R.id.input_height)
        inputHeight.setText(deviceHeight.toString())

        inputUserAgent.setText(UserAgentList.getInstance().getUaList()[0])

    }
    private fun WihtelistArray(input : String) : List<String> {
        val splitData = input.split(";")
        return splitData
    }
    private fun checkInputData() :Boolean {
        if(inputZoomfactor.text.isEmpty() ||
            //input_ua_.text.isEmpty()||
            inputHosturl.text.isEmpty())
            return false;
        return true
    }

    private fun makeIntentJsonData() : String {
        if (!checkInputData()) {
            return "data missing";
        }
        val jsonMain = JSONObject()
        val jsonArray = JSONArray()
        var jsonObject = JSONObject();
/*hostUrl*/
        if(inputHosturl.text.isNotEmpty())
            jsonObject.put("hosturl", inputHosturl.text.toString())
/*zoom factor*/
        if(inputZoomfactor.text.isNotEmpty())
            jsonObject.put("zoomFactor", inputZoomfactor.text.toString().toFloat())
/*mobile Page */
        if (mobilePage.isChecked()) {
            jsonObject.put("mobilePage", "true")
        }
/*embeded cookie */
        if (embededCookie.isChecked()) {
            jsonObject.put("embedCookie", "true")
        }

/*force scale */
        if (forceScale.isChecked()) {
            jsonObject.put("forceScale", "true")
        }
/*user agent*/
        if(inputUserAgent.text.isNotEmpty())
            jsonObject.put("userAgent", inputUserAgent.text.toString())
        jsonArray.put(jsonObject)
/*white list*/
        Log.e(tag, "white list string: " + inputWhitelist.text.toString())
        var user_wlist = WihtelistArray(inputWhitelist.text.toString())
        var wlist = JSONArray();

        for(w in user_wlist) {
            if(w.isNotEmpty())
                wlist.put(w.toString());
        }
        jsonObject.put("whiteList",wlist)
        return jsonObject.toString();
    }

    fun openBrsByCustomTab(){
        val uri = Uri.parse("https://rroya.tistory.com")
        val intentBuilder = CustomTabsIntent.Builder().setInitialActivityHeightPx(
            100,
            ACTIVITY_HEIGHT_FIXED
        );
        intentBuilder.setUrlBarHidingEnabled(true)
        val customTabsIntent = intentBuilder.build()
        customTabsIntent.launchUrl(this, uri)
    }

    fun openBrsByIntent() {
        if (!checkInputData()) {
            Toast.makeText(this@MainActivity, "미입력있음", Toast.LENGTH_SHORT).show()
            return;
        }
        val i = Intent("com.obigo.automotivebrowser")
        val uri = Uri.parse(inputHosturl.text.toString())
        if(i == null ) {
        } else {
            /*
            {"serviceName":"왓챠","hostUrl":"https://watcha.com/automobile/intro","zoomFactor":1,"userAgent":"Mozilla/5.0 (X11; ccNC; Linux aarch64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Mobile Safari/537.36","whiteList":["https://watcha.com/"]}
            */

            i.setAction(Intent.ACTION_VIEW);
            i.setData(uri);
            i.putExtra("oba.openurl.url",inputHosturl.text.toString())//KEY_EXTRA_OPENURL_URL
            i.putExtra("oba.openurl.type","1")//KEY_EXTRA_OPENURL_TYPE

            //i.putExtra("oba.openurl.config",config)//KEY_EXTRA_OPENURL_CONFIG
            if (inputWidth.text.toString().isEmpty() || inputHeight.text.toString().isEmpty()) {
                Log.e(tag, "please input width / height");
            } else {
                var commandline = inputWidth.text.toString() + "x" + inputHeight.text.toString()
                i.putExtra("agb-content-window-size", commandline.toString())//width,height
            }
            //ROYA HERE
            var brs_commandline = inputBrsCommandline.text.toString()
            i.putExtra(brs_commandline.toString(),"true")


            var json = makeIntentJsonData();
            txtView_encoded_json_text.setText(json)
            i.putExtra("oba.content.config", json)
            startActivity(i);
        }
    }
    fun isPackageInstalled(ctx: Context, packageName: String): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: Exception) {
            Log.e(tag, "isPackageInstalled failed");
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
package com.arin.app

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arin.app.ui.theme.ComarinappTheme
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

//import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    //START values
    var TAG = "ARIN"
    lateinit var context_: Context
    var mom_number_: String = "01095444074"
    var dad_number_: String = "01099597899"
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    lateinit var view_bg_image_: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActionBar()!!.setTitle("장아린 전용 앱")
        setContentView(R.layout.main)
        view_bg_image_ = findViewById(R.id.bg)
        context_ = getApplicationContext();
        setImageViewImage(getContext().getFilesDir().getPath() + "/arin_bg.png")
        registerBackgroundBgPicker()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.menu_change_bg -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                //return true
            }
            R.id.menu_image_library -> {
                Toast.makeText(this, "menu_image_library", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.menu_change_sms_text -> {
                Toast.makeText(this, "menu_change_sms_text", Toast.LENGTH_SHORT).show()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
    fun registerBackgroundBgPicker() {
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val intent = checkNotNull(result.data)
                var currentImageUri  = intent.data
                try {
                    currentImageUri?.let {
                        if(Build.VERSION.SDK_INT < 28) {
                            val bitmap = MediaStore.Images.Media.getBitmap(
                                this.contentResolver,
                                currentImageUri
                            )
                            view_bg_image_?.setImageBitmap(bitmap)
                        } else {
                            val source = ImageDecoder.createSource(this.contentResolver, currentImageUri)
                            val bitmap = ImageDecoder.decodeBitmap(source)
                            view_bg_image_?.setImageBitmap(bitmap)
                            val back_dir_: String = getContext().getFilesDir().getPath()
                            Log.e(TAG, "save dir : " + back_dir_)//
                            saveBitmapAsFile(bitmap,back_dir_+"/arin_bg.png")
                        }
                    }
                } catch(e : Exception) {
                    Log.e(TAG, "ERROR " + e)//
                    e.printStackTrace()
                }
            }
        }
    }
    private fun saveBitmapAsFile(bitmap: Bitmap, filepath: String) {
        val file = File(filepath)
        var os: OutputStream? = null

        try {
            file.createNewFile()
            os = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
            os.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
    fun setImageViewImage(filepath : String) {
        val imgFile = File(filepath)
        if (imgFile.exists()) {
            val imgBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            view_bg_image_.setImageBitmap(imgBitmap)
        }
    }
    fun getContext(): Context {
        return context_
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
    ComarinappTheme {
        Greeting("Android")
    }
}
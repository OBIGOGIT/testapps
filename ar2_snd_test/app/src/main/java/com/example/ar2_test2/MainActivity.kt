package com.example.ar2_test2


import android.content.Context
import android.database.ContentObserver
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ecarx.xui.adaptapi.device.ext.BtExtension
import com.ecarx.xui.adaptapi.device.ext.IA2dpCallback
import com.ecarx.xui.adaptapi.device.ext.IA2dpExtension
import com.ecarx.xui.adaptapi.device.ext.common.BtDef


import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView

class MainActivity : AppCompatActivity() {
    private var TAG = "[ROYA]"
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    private var mAudioFocusWrapper: AudioFocusWrapper? = null
    private var mBtA2dpExtension: IA2dpExtension? = null
    private val BT_HEADSET_STATE = "bt_headset_state" // 1: connect, 2: disconnect
    private var mIsOnPsd = false
    private var mIsPlaying = false
    private var mIsBtHeadsetConnected = false
    private var player: ExoPlayer? = null
    private var playerView: PlayerView? = null
    private lateinit var audioManager: AudioManager


    fun updatePsdBtHeadsetConnected(value: Boolean) {
        Log.i(TAG, "updatePsdBtHeadsetConnected : " + value)
    }

    private val mBtHeadsetStateObserver = object : ContentObserver(null) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            mIsBtHeadsetConnected = Settings.System.getInt(contentResolver, BT_HEADSET_STATE, -1) == 1
            btHeadsetStateChanged(mIsBtHeadsetConnected, mIsOnPsd, mIsPlaying)
        }
    }
    private fun btHeadsetStateChanged(isBtHeadsetConnected: Boolean, isOnPsd: Boolean, playerIsPlaying: Boolean) {
        Log.d(TAG, "btHeadsetStateChanged " + isBtHeadsetConnected)
        val isPsdBtHeadsetConnected = isBtHeadsetConnected
        updatePsdBtHeadsetConnected(isPsdBtHeadsetConnected)

        // case: when running on PSD, if BT headset connection changed
        Log.e("ROYA" , " isPsdBtHeadsetConnected : " + isPsdBtHeadsetConnected)
        if (isPsdBtHeadsetConnected) {
            Log.e(TAG, "BT_HEADSET_STATE::onChange When running on PSD, BT headset connection changes. isOnPsd=$mIsOnPsd,isBtHeadsetConnected=$mIsBtHeadsetConnected")

            mAudioFocusWrapper!!.setPlayWhenReady(true)

            /*serviceScope.launch {
                mPlayer?.stop()
                mPlayer?.prepare()
                mPlayer?.play()
            }*/
        }
    }
    private val mBtA2dpCallback = object : IA2dpCallback {
        override fun onA2dpServiceReady() {
            Log.e(TAG, "mBtA2dpCallback onA2dpServiceReady")
        }

        override fun onA2dpStateChanged(address: String?, prevState: Int, newState: Int) {
            Log.e(TAG, "mBtA2dpCallback::onA2dpStateChanged address=" + address + "prevState = " + prevState + " newState="+newState)

            mIsBtHeadsetConnected = when (newState) {
                BtDef.STATE_CONNECTED -> {

                    // case: 연결 state changed.
                    // STATE_READY -> STATE_CONNECTING -> STATE_CONNECTED
                    true
                }
                BtDef.STATE_READY -> {
                    // case: 연결 해제 state changed.
                    // STATE_CONNECTED -> STATE_DISCONNECTING -> STATE_READY
                    false
                }
                else -> {
                    return
                }
            }
            Log.e(TAG, "onA2dpStateChanged : " + mIsBtHeadsetConnected + " "+ mIsOnPsd + " "+ mIsPlaying)
            btHeadsetStateChanged(mIsBtHeadsetConnected, mIsOnPsd, mIsPlaying)
        }
    }

    fun initBtHeadset(ar2InitTask: () -> Unit) {
        Log.d(TAG, "initBtHeadset start")
        ar2InitTask();
        Log.d(TAG, "initBtHeadset end")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "string display app")
        audioManager = applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        setContentView(R.layout.activity_main)

        initBtHeadset(
            ar2InitTask = {
                Log.e(TAG, "initBtHeadset start 1")
                mBtA2dpExtension = BtExtension(this).a2dpExtension
                Log.e(TAG, "initBtHeadset start 2")
                mIsBtHeadsetConnected = mBtA2dpExtension?.isA2dpConnected ?: false
                Log.e(TAG, "initBtHeadset start 3")
                mBtA2dpExtension?.registerA2dpCallback(mBtA2dpCallback)
                Log.e(TAG, "initBtHeadset start 4")
            }
        )

        // PlayerView 연결
        playerView = findViewById<PlayerView>(R.id.player_view)

        media_play();
    }
     fun media_play() {


         // ExoPlayer 생성
         Log.e("ROYA","media play.............start ")
         val bluetoothval=true;
        player = ExoPlayer.Builder(this).build()



        // PlayerView에 Player 설정
        playerView!!.setPlayer(player)


        // 재생할 미디어 아이템 설정
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.sample}") // 여기에 실제 URL 입력
        val mediaItem: MediaItem = MediaItem.fromUri(videoUri)
        player!!.setMediaItem(mediaItem)

         player!!.repeatMode = Player.REPEAT_MODE_ALL
         // 준비 및 재생
             //player!!.prepare()
             //player!!.play()
         mAudioFocusWrapper = AudioFocusWrapper(this, audioManager, player!!,bluetoothval)

    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
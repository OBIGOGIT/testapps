package com.example.ar2_test2
import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.util.Log
import com.google.android.exoplayer2.ExoPlayer
import com.ecarx.xui.adaptapi.policy.AudioAttributesImp
import com.ecarx.xui.adaptapi.policy.Policy
import com.google.android.exoplayer2.C

class AudioFocusWrapper(private val context: Context,
    private val audioManager: AudioManager,
    private val player: ExoPlayer,
    isPsdBtHeadsetConnected: Boolean,
) : ExoPlayer by player{

    companion object {
        private const val TAG = "Yna AudioFocusWrapper"
        private const val MEDIA_VOLUME_DEFAULT = 1.0f
        private const val MEDIA_VOLUME_DUCK = 0.3f
    }

    private var audioFocusRequest: AudioFocusRequest? = null
    private var shouldPlayWhenReady = false
    private var mNeedToPlayPsdBtHeadset = true
    private var mIsUsedAudioAttributesForPsd = false

    // audio attributes required when playing in CSD (Center Stack Display)
    private val mAudioAttributesForCsd = AudioAttributes.Builder()
        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
        .setUsage(AudioAttributes.USAGE_MEDIA)
        .build()

    private val mPlayerAudioAttributesForCsd = com.google.android.exoplayer2.audio.AudioAttributes.Builder()
        .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
        .setUsage(C.USAGE_MEDIA)
        .build()

    private val mAudioAttributesForPsd = buildAudioAttributesForPsd()
    private val mPlayerAudioAttributesForPsd = buildPlayerAudioAttributesForPsd()

    private val audioFocusListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
        when (focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> {
                Log.d(TAG, "[audioFocusListener] AUDIOFOCUS_GAIN:shouldPlayWhenReady=$shouldPlayWhenReady,player.playWhenReady=${player.playWhenReady}")
                if (shouldPlayWhenReady || player.playWhenReady) {
                    player.playWhenReady = true
                    player.volume = MEDIA_VOLUME_DEFAULT
                }
                shouldPlayWhenReady = false
            }

            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                Log.d(TAG, "[audioFocusListener] AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:player.playWhenReady=${player.playWhenReady}")
                if (player.playWhenReady) {
                    player.volume = MEDIA_VOLUME_DUCK
                }
            }

            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                Log.d(TAG, "[audioFocusListener] AUDIOFOCUS_LOSS_TRANSIENT:player.playWhenReady=${player.playWhenReady}")
                shouldPlayWhenReady = player.playWhenReady
                player.playWhenReady = false
            }

            AudioManager.AUDIOFOCUS_LOSS -> {
                Log.d(TAG, "[audioFocusListener] AUDIOFOCUS_LOSS")
                abandonAudioFocus()
            }
        }
    }

    fun setNeedToPlayPsdBtHeadset(need: Boolean) {
        mNeedToPlayPsdBtHeadset = true
    }

    fun isUsedAudioAttributesForPsd() = mIsUsedAudioAttributesForPsd

    override fun setPlayWhenReady(playWhenReady: Boolean) {
        Log.d(TAG, "[setPlayWhenReady] playWhenReady=$playWhenReady")
        if (playWhenReady) {
            requestAudioFocus()
        } else {
            abandonAudioFocus()
        }
    }

    override fun play() {
        playWhenReady = true
    }

    override fun pause() {
        playWhenReady = false
    }


    @SuppressLint("WrongConstant")
    private fun buildAudioAttributesForPsd(): AudioAttributes {
        val audioAttrBuilder = AudioAttributes.Builder()
        //if (TargetDeviceAgent.isAr1()) {
         //   audioAttrBuilder.setLegacyStreamType(TargetDeviceAgent.getAudioAttributesLegacyStreamTypeForPsd())
        //}

         val mPolicy = Policy.create(context)

        audioAttrBuilder
            .setContentType(
                mPolicy.audioAttributes.getAudioAtrributesUsage(AudioAttributesImp.USAGE_PSD_BT_EAR_PHONE)
            )
            .setUsage(AudioAttributes.CONTENT_TYPE_MUSIC)
        return audioAttrBuilder.build()
    }

    @SuppressLint("WrongConstant")
    private fun buildPlayerAudioAttributesForPsd(): com.google.android.exoplayer2.audio.AudioAttributes {
        val mPolicy = Policy.create(context)

        val audioAttrBuilder = com.google.android.exoplayer2.audio.AudioAttributes.Builder()
        audioAttrBuilder
            .setContentType(C.CONTENT_TYPE_MUSIC)
            .setUsage(mPolicy.audioAttributes.getAudioAtrributesUsage(AudioAttributesImp.USAGE_PSD_BT_EAR_PHONE))
        return audioAttrBuilder.build()
    }

    private fun requestAudioFocus() {
        abandonAudioFocus()

        val audioAttributes = if (mNeedToPlayPsdBtHeadset) {
            mIsUsedAudioAttributesForPsd = true
            mAudioAttributesForPsd
        } else {
            mIsUsedAudioAttributesForPsd = false
            mAudioAttributesForCsd
        }

        if(mNeedToPlayPsdBtHeadset){
            player.setAudioAttributes(mPlayerAudioAttributesForPsd, false)
        }else {
            player.setAudioAttributes(mPlayerAudioAttributesForCsd, false)
        }


        Log.d(TAG, "[requestAudioFocus] isUsedAudioAttributesForPsd=$mIsUsedAudioAttributesForPsd")
        audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
            .setAudioAttributes(audioAttributes)
            .setWillPauseWhenDucked(false)
            .setOnAudioFocusChangeListener(audioFocusListener)
            .build()
        val result = audioManager.requestAudioFocus(audioFocusRequest!!)

        // Call the listener whenever focus is granted - even the first time!
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            shouldPlayWhenReady = true
            audioFocusListener.onAudioFocusChange(AudioManager.AUDIOFOCUS_GAIN)
        } else {
            Log.w(TAG, "Playback not started: Audio focus request denied.")
        }
    }

    private fun abandonAudioFocus() {
        player.playWhenReady = false
        if (audioFocusRequest != null) {
            audioManager.abandonAudioFocusRequest(audioFocusRequest!!)
        }
    }

}
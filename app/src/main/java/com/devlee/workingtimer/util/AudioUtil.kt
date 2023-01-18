package com.devlee.workingtimer.util

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import com.devlee.workingtimer.R

object AudioUtil {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var audioManager: AudioManager


    fun initAudioPlayer(context: Context) {
        audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        mediaPlayer = MediaPlayer.create(context, R.raw.maiden_prayer)
    }

    fun startAudio() {
        PreferencesUtil.setSystemVolume(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC))
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume / 2, 0)

        mediaPlayer.apply {
            isLooping = true
            start()
        }
    }

    fun stopAudio() {
        if (mediaPlayer.isPlaying) {
            val v = PreferencesUtil.getSystemVolume()
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, v, 0)
            mediaPlayer.apply {
                stop()
                release()
            }
        }
    }
}
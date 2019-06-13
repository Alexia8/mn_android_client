package com.alexiacdura.musicnerds

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.alexiacdura.mn_ui.MusicnerdsActivity
import com.alexiacdura.mn_ui.core.utils.resources.AppConstants
import java.util.*
import kotlin.concurrent.schedule


class SplashActivity : AppCompatActivity() {

    private var set: AnimatorSet? = null
    private var splash: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        set = AnimatorInflater.loadAnimator(this@SplashActivity, R.animator.flip_animation) as AnimatorSet
        setLogoAnimation()

        Timer("SettingUp", false).schedule(AppConstants.TIMER_SPLASH_DELAY) {
            startMusicnerdsActivity()
        }
    }

    private fun setLogoAnimation() {
        splash = findViewById<View>(R.id.splashLogo) as ImageView
        set!!.setTarget(splash)
        set!!.start()
    }

    private fun startMusicnerdsActivity() {
        val intent = MusicnerdsActivity.newIntent(this@SplashActivity, 1)
        startActivity(intent)
    }
}
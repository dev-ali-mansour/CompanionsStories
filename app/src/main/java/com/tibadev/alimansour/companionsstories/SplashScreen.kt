package com.tibadev.alimansour.companionsstories

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * TibaDev Solutions Inc
 * Created by Ali Mansour on 2016-09-09.
 */
class SplashScreen : AppCompatActivity() {

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.splash_screen)
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000L)
            val mainIntent = Intent(this@SplashScreen, MainActivity::class.java)
            this@SplashScreen.startActivity(mainIntent)
            finish()
        }
    }
}
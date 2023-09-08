package com.tibadev.alimansour.companionsstories.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.tibadev.alimansour.companionsstories.R

fun getAdRequest(): AdRequest {
    val testDeviceIds =
        listOf(
            AdRequest.DEVICE_ID_EMULATOR,
            "2C5D114F97480510E158672ABF2719AD",
            "147A1C089F81F65CD1C5F563BD30DE80"
        )
    val configuration =
        RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
    MobileAds.setRequestConfiguration(configuration)
    return AdRequest.Builder().build()
}

fun showInterstitialAd(context: Context) {
    var interstitialAd: InterstitialAd?
    val adRequest = getAdRequest()
    InterstitialAd.load(
        context,
        context.getString(R.string.admob_interstitial_unit_id),
        adRequest,
        object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(p0: InterstitialAd) {
                interstitialAd = p0
                interstitialAd?.show(context as AppCompatActivity)
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                interstitialAd = null
            }
        })
}
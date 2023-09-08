package com.tibadev.alimansour.companionsstories

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat

class AboutActivity : AppCompatActivity() {
    private var mShareActionProvider: ShareActionProvider? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val version = findViewById<TextView>(R.id.txt_version_value)
        try {
            val pInfo = packageManager.getPackageInfo(packageName, 0)
            version.text = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        val imgLogo: ImageView = findViewById(R.id.img_logo)
        val imgFacebook: ImageView = findViewById(R.id.img_facebook)
        val imgTwitter: ImageView = findViewById(R.id.img_twitter)
        val imgYoutube: ImageView = findViewById(R.id.img_youtube)
        val imgLinkedin: ImageView = findViewById(R.id.img_linkedin)

        imgLogo.setOnClickListener { v: View? ->
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.data = Uri.parse(getString(R.string.website_url))
            startActivity(intent)
        }
        imgFacebook.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.data = Uri.parse(getString(R.string.facebook_url))
            startActivity(intent)
        }
        imgTwitter.setOnClickListener { v: View? ->
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.data = Uri.parse(getString(R.string.twitter_url))
            startActivity(intent)
        }
        imgYoutube.setOnClickListener { v: View? ->
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.data = Uri.parse(getString(R.string.youtube_channel_url))
            startActivity(intent)
        }
        imgLinkedin.setOnClickListener { v: View? ->
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.data = Uri.parse(getString(R.string.linkedin_url))
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val shareItem = menu.findItem(R.id.action_share)

        mShareActionProvider = MenuItemCompat.getActionProvider(shareItem) as ShareActionProvider?
        mShareActionProvider!!.setShareIntent(doShare())
        val rateItem = menu.findItem(R.id.action_rate)
        rateItem.setOnMenuItemClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("market://details?id=com.tibadev.alimansour.companionsstories")
            if (!myStartActivity(intent)) {
                intent.data =
                    Uri.parse("https://play.google.com/store/apps/details?com.tibadev.alimansour.companionsstories")
                if (!myStartActivity(intent)) {
                    Toast.makeText(
                        this@AboutActivity,
                        "عفواً لا يمكن فتح تطبيق جوجل بلاي ، برجاء تثبيت التطبيق.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            false
        }
        return true
    }

    private fun doShare(): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "تطبيق قصص الأنبياء - أندرويد")
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "قصص الأنبياء - أندرويد\nطيبة ديف للبرمجيات\n\nhttps://play.google.com/store/apps/details?com.tibadev.alimansour.companionsstories"
        )
        return intent
    }

    private fun myStartActivity(aIntent: Intent): Boolean {
        return try {
            startActivity(aIntent)
            true
        } catch (e: ActivityNotFoundException) {
            false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {}
            R.id.action_about -> {
                val intent = Intent(this@AboutActivity, AboutActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
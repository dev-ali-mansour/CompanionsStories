package com.tibadev.alimansour.companionsstories

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat
import com.google.android.gms.ads.AdView
import com.tibadev.alimansour.companionsstories.util.getAdRequest

class StoryActivity : AppCompatActivity() {
    private lateinit var txtProphet: TextView
    private lateinit var txtTitle: TextView
    private lateinit var txtContent: TextView
    private lateinit var bannerStory: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        bannerStory = findViewById(R.id.banner_story)
        try {
            bannerStory.loadAd(getAdRequest())

            val bundle = intent.extras
            txtProphet = findViewById(R.id.txt_companion)
            txtContent = findViewById(R.id.txt_content)
            txtTitle = findViewById(R.id.txt_title)
            bundle?.let {
                txtProphet.text = it.getString("companion")
                txtTitle.text = it.getString("title")
                txtContent.text = it.getString("content")
            }

        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, e.message.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_story, menu)
        val shareItem = menu.findItem(R.id.action_share)
        val mShareActionProvider =
            MenuItemCompat.getActionProvider(shareItem) as ShareActionProvider
        mShareActionProvider.setShareIntent(doShare())
        val rateItem = menu.findItem(R.id.action_rate)
        rateItem.setOnMenuItemClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            // Try Google play
            intent.data = Uri.parse("market://details?id=com.tibadev.alimansour.companionsstories")
            if (!MyStartActivity(intent)) {
                // Market (Google play) app seems not installed, let's try to open a webbrowser
                intent.data =
                    Uri.parse("https://play.google.com/store/apps/details?com.tibadev.alimansour.companionsstories")
                if (!MyStartActivity(intent)) {
                    // Well if this also fails, we have run out of options, inform the user.
                    Toast.makeText(
                        this@StoryActivity,
                        "عفواً لا يمكن فتح تطبيق جوجل بلاي ، برجاء تثبيت التطبيق.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            false
        }
        return true
    }

    fun doShare(): Intent {
        // populate the share intent with data
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "قصص الصحابة - " + txtProphet.text)
        intent.putExtra(
            Intent.EXTRA_TEXT, """
     قصص الصحابة - أندرويد
     طيبة ديف للبرمجيات
     
     ${txtProphet.text}
     ${txtContent.text}
     """.trimIndent()
        )
        return intent
    }

    private fun MyStartActivity(aIntent: Intent): Boolean {
        return try {
            startActivity(aIntent)
            true
        } catch (e: ActivityNotFoundException) {
            false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.action_our_apps -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("market://dev?id=8256417273842663752")
                if (!MyStartActivity(intent)) {
                    intent.data =
                        Uri.parse("https://play.google.com/store/apps/dev?id=8256417273842663752")
                    if (!MyStartActivity(intent)) {
                        Toast.makeText(
                            this@StoryActivity,
                            "عفواً لا يمكن فتح تطبيق جوجل بلاي ، برجاء تثبيت التطبيق.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            R.id.action_size_up -> txtContent.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                txtContent.textSize + 1
            )

            R.id.action_size_down -> txtContent.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                txtContent.textSize - 1
            )

            R.id.action_settings -> {}
            R.id.action_about -> {
                val intentAbout = Intent(this@StoryActivity, AboutActivity::class.java)
                startActivity(intentAbout)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
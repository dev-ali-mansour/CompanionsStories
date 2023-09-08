package com.tibadev.alimansour.companionsstories

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        // Retrieve the share menu item
        val shareItem = menu.findItem(R.id.action_share)

        // Now get the ShareActionProvider from the item
        val mShareActionProvider =
            MenuItemCompat.getActionProvider(shareItem) as ShareActionProvider
        mShareActionProvider.setShareIntent(doShare())
        val rateItem = menu.findItem(R.id.action_rate)
        rateItem.setOnMenuItemClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            //Try Google play
            intent.data = Uri.parse("market://details?id=com.tibadev.alimansour.companionsstories")
            if (!myStartActivity(intent)) {
                //Market (Google play) app seems not installed, let's try to open a webbrowser
                intent.data =
                    Uri.parse("https://play.google.com/store/apps/details?com.tibadev.alimansour.companionsstories")
                if (!myStartActivity(intent)) {
                    //Well if this also fails, we have run out of options, inform the user.
                    Toast.makeText(
                        this@SettingsActivity,
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
        // populate the share intent with data
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> {}
            R.id.action_about -> {
                val intent = Intent(this@SettingsActivity, AboutActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
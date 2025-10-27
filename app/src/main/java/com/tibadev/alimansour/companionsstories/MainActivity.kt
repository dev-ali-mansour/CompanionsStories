package com.tibadev.alimansour.companionsstories

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.tibadev.alimansour.companionsstories.model.Story
import com.tibadev.alimansour.companionsstories.util.Network
import com.tibadev.alimansour.companionsstories.util.XMLPullParserHandler
import com.tibadev.alimansour.companionsstories.util.showInterstitialAd
import timber.log.Timber
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var stories: List<Story>
    private lateinit var strCompanion: String
    private lateinit var strTitle: String
    private lateinit var strContent: String
    private lateinit var network: Network
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        network = Network(this)
        listView = findViewById(R.id.list)

        setSupportActionBar(toolbar)
        try {
            val parser = XMLPullParserHandler()
            stories = parser.parse(assets.open("stories.xml"))
            listView.adapter = MyAdapter(this, android.R.layout.simple_list_item_1, stories)

            val finalStories: List<Story> = stories
            listView.setOnItemClickListener { _, _, position, _ -> // Retrieve story data
                strCompanion = finalStories[position].companion
                strTitle = finalStories[position].title
                strContent = finalStories[position].content
                showInterstitialAd(this)
                showStory()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (ex: Exception) {
            Timber.tag(this.javaClass.simpleName).e(ex.message.toString())
        }
    }

    private fun showStory() {
        val intent = Intent(this@MainActivity, StoryActivity::class.java)
        intent.putExtra("companion", strCompanion)
        intent.putExtra("title", strTitle)
        intent.putExtra("content", strContent)
        startActivity(intent)
    }

    inner class MyAdapter internal constructor(
        context: Context,
        resource: Int,
        objects: List<Story>
    ) : ArrayAdapter<Story?>(context, resource, objects) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val inflater: LayoutInflater =
                getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(R.layout.list_item, parent, false)
            val txtItemCompanion: TextView =
                view.findViewById<View>(R.id.txt_item_companion) as TextView
            val story: Story = stories[position]
            txtItemCompanion.text = story.companion
            return view
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        // Retrieve the share menu item
        val shareItem = menu.findItem(R.id.action_share)
        val rateItem = menu.findItem(R.id.action_rate)

        // Now get the ShareActionProvider from the item
        shareItem.setOnMenuItemClickListener {
            appShare()
            true
        }
        rateItem.setOnMenuItemClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            //Try Google play
            intent.data = Uri.parse("market://details?id=com.tibadev.alimansour.companionsstories")
            if (!myStartActivity(intent)) {
                //Market (Google play) app seems not installed, let's try to open a webBrowser
                intent.data =
                    Uri.parse("https://play.google.com/store/apps/details?com.tibadev.alimansour.companionsstories")
                if (!myStartActivity(intent)) {
                    //Well if this also fails, we have run out of options, inform the user.
                    Toast.makeText(
                        this@MainActivity,
                        "عفواً لا يمكن فتح تطبيق جوجل بلاي ، برجاء تثبيت التطبيق.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            false
        }
        return true
    }

    private fun appShare() {
        try {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.type = "text/plain"
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
            sendIntent.putExtra(
                Intent.EXTRA_TEXT, getString(R.string.share_app_text) + "\n"
                        + getString(R.string.app_url) + packageName
            )
            startActivity(Intent.createChooser(sendIntent, getString(R.string.app_share)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
            R.id.action_privacy_policy -> network.openLink(getString(R.string.privacy_policy_url))
            R.id.action_our_apps -> showOurApps()

            R.id.action_settings -> {}
            R.id.action_about -> {
                val intentAbout = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(intentAbout)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showOurApps() {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.our_apps_market_url))
                )
            )
        } catch (exception: android.content.ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.our_apps_url))))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
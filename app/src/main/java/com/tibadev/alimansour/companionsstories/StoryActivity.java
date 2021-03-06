package com.tibadev.alimansour.companionsstories;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class StoryActivity extends AppCompatActivity {
    private TextView txtProphet;
    private TextView txtContent;
    private View adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        try {
            // Check Device Connectivity & view Ads if it is connected:
            adView = findViewById(R.id.startAppBanner_story);
            NetworkCheck networkCheck = new NetworkCheck();
            if (networkCheck.check(this)) {
                adView.setVisibility(View.VISIBLE);
            } else {
                adView.setVisibility(View.GONE);
            }

            // Retrieve Prophet story data from the Main Activity
            Bundle bundle = getIntent().getExtras();
            txtProphet = (TextView) findViewById(R.id.txt_companion);
            txtContent = (TextView) findViewById(R.id.txt_content);
            TextView txtTitle = (TextView) findViewById(R.id.txt_title);
            assert txtProphet != null;
            txtProphet.setText(bundle.getString("companion"));
            assert txtTitle != null;
            txtTitle.setText(bundle.getString("title"));
            assert txtContent != null;
            txtContent.setText(bundle.getString("content"));
        } catch (Exception e) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_story, menu);
        // Retrieve the share menu item
        MenuItem shareItem = menu.findItem(R.id.action_share);

        // Now get the ShareActionProvider from the item
        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        mShareActionProvider.setShareIntent(doShare());

        MenuItem rateItem = menu.findItem(R.id.action_rate);
        rateItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                // Try Google play
                intent.setData(Uri.parse("market://details?id=com.tibadev.alimansour.companionsstories"));
                if (!MyStartActivity(intent)) {
                    // Market (Google play) app seems not installed, let's try to open a webbrowser
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?com.tibadev.alimansour.companionsstories"));
                    if (!MyStartActivity(intent)) {
                        // Well if this also fails, we have run out of options, inform the user.
                        Toast.makeText(StoryActivity.this, "عفواً لا يمكن فتح تطبيق جوجل بلاي ، برجاء تثبيت التطبيق.", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
        return true;
    }

    public Intent doShare() {
        // populate the share intent with data
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "قصص الصحابة - " + txtProphet.getText());
        intent.putExtra(Intent.EXTRA_TEXT, "قصص الصحابة - أندرويد\nطيبة ديف للبرمجيات\n\n" + txtProphet.getText() + "\n" + txtContent.getText());
        return intent;
    }

    private boolean MyStartActivity(Intent aIntent) {
        try {
            startActivity(aIntent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_our_apps:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                //Try Google play
                intent.setData(Uri.parse("market://dev?id=8256417273842663752"));
                if (!MyStartActivity(intent)) {
                    //Market (Google play) app seems not installed, let's try to open a webbrowser
                    intent.setData(Uri.parse("https://play.google.com/store/apps/dev?id=8256417273842663752"));
                    if (!MyStartActivity(intent)) {
                        //Well if this also fails, we have run out of options, inform the user.
                        Toast.makeText(StoryActivity.this, "عفواً لا يمكن فتح تطبيق جوجل بلاي ، برجاء تثبيت التطبيق.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.action_size_up:
                txtContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, txtContent.getTextSize() + 1);
                break;
            case R.id.action_size_down:
                txtContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, txtContent.getTextSize() - 1);
                break;
            case R.id.action_settings:

                break;
            case R.id.action_about:
                Intent intentAbout = new Intent(StoryActivity.this, AboutActivity.class);
                startActivity(intentAbout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

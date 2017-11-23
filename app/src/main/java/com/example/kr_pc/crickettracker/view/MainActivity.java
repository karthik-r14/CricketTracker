package com.example.kr_pc.crickettracker.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.kr_pc.crickettracker.R;

public class MainActivity extends AppCompatActivity {

    public static final String MY_PROFILE = "my_profile";
    public static final String LOGGED_IN = "LoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button liveScoreButton = findViewById(R.id.live_score_button);
        liveScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScrollingActivity.class);
                startActivity(intent);
            }
        });

        Button myProfileButton = findViewById(R.id.my_profile);
        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isUserLoggedIn()) {
                    Intent intent = new Intent(MainActivity.this, MyProfileActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cric_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.rate_us:
                RateUsDialogFragment rateUsDialogFragment = RateUsDialogFragment.newInstance();
                rateUsDialogFragment.show(getSupportFragmentManager(), RateUsDialogFragment.TAG);
                return true;
            case R.id.feedback:
                Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
                startActivity(intent);
                return true;
            case R.id.share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("plain/text");
                shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message));
                startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.share)));
                return true;
            case R.id.about_us:
                Intent aboutUsIntent = new Intent(MainActivity.this, AboutUsActivity.class);
                startActivity(aboutUsIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences(MY_PROFILE, Context.MODE_PRIVATE);
        boolean loggedIn = sharedPreferences.getBoolean(LOGGED_IN, false);
        return loggedIn;
    }
}
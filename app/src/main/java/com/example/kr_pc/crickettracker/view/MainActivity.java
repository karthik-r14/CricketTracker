package com.example.kr_pc.crickettracker.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kr_pc.crickettracker.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    public static final String MY_PROFILE = "my_profile";
    public static final String LOGGED_IN = "LoggedIn";

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, String.valueOf(R.string.banner_ad_unit_id));

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Button liveScoreButton = findViewById(R.id.live_score_button);
        liveScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connectivityAvailable()) {
                    Intent intent = new Intent(MainActivity.this, MatchesActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.move_left_anim, R.anim.move_right_anim);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.no_internet_message, LENGTH_LONG).show();
                }
            }
        });

        Button playerProfileButton = findViewById(R.id.player_profile_button);
        playerProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connectivityAvailable()) {
                    Intent intent = new Intent(MainActivity.this, PlayerSearchActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.move_left_anim, R.anim.move_right_anim);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.no_internet_message, LENGTH_LONG).show();
                }
            }
        });

        Button myProfileButton = findViewById(R.id.my_profile);
        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUserLoggedIn()) {
                    Intent intent = new Intent(MainActivity.this, MyProfileActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.move_left_anim, R.anim.move_right_anim);
                } else {
                    Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.move_left_anim, R.anim.move_right_anim);
                }
            }
        });

        Button fixturesButton = findViewById(R.id.fixtures);
        fixturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connectivityAvailable()) {
                    Intent intent = new Intent(MainActivity.this, FixturesActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.move_left_anim, R.anim.move_right_anim);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.no_internet_message, LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean connectivityAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
                if (isUserLoggedIn()) {
                    RateUsDialogFragment rateUsDialogFragment = RateUsDialogFragment.newInstance();
                    rateUsDialogFragment.show(getSupportFragmentManager(), RateUsDialogFragment.TAG);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.no_login_rating_text, LENGTH_LONG).show();
                }
                return true;
            case R.id.feedback:
                if (isUserLoggedIn()) {
                    Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.move_left_anim, R.anim.move_right_anim);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.no_login_feedback_text, LENGTH_LONG).show();
                }

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
                overridePendingTransition(R.anim.move_left_anim, R.anim.move_right_anim);
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
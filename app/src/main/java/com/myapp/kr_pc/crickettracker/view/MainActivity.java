package com.myapp.kr_pc.crickettracker.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.kr_pc.crickettracker.R;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    public static final String MY_PROFILE = "my_profile";
    public static final String LOGGED_IN = "LoggedIn";
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    private Snackbar snackbar;
    private boolean internetConnected = true;
    private RelativeLayout relativeLayout;

//    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = findViewById(R.id.relative_layout);

//        MobileAds.initialize(this, String.valueOf(R.string.banner_ad_unit_id));
//
//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

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
                shareIntent.setType("text/plain");
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

    @Override
    protected void onResume() {
        super.onResume();
        registerInternetCheckReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    private void registerInternetCheckReceiver() {
        IntentFilter internetFilter = new IntentFilter();
        internetFilter.addAction("android.net.wifi.STATE_CHANGE");
        internetFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver, internetFilter);
    }

    /**
     * Runtime Broadcast receiver inner class to capture internet connectivity events
     */
    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = getConnectivityStatusString(context);
            setSnackbarMessage(status, false);
        }
    };

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        String status = null;
        if (conn == TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }

    private void setSnackbarMessage(String status, boolean showBar) {
        String internetStatus = "";
        if (status.equalsIgnoreCase("Wifi enabled") || status.equalsIgnoreCase("Mobile data enabled")) {
            internetStatus = getString(R.string.internet_connected_msg);
        } else {
            internetStatus = getString(R.string.internet_disconnected_msg);
        }
        snackbar = Snackbar
                .make(relativeLayout, internetStatus, Snackbar.LENGTH_LONG)
                .setAction("X", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(Color.WHITE);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        if (internetStatus.equalsIgnoreCase("Lost Internet Connection")) {
            if (internetConnected) {
                snackbar.show();
                internetConnected = false;
            }
        } else {
            if (!internetConnected) {
                internetConnected = true;
                snackbar.show();
            }
        }
    }
}
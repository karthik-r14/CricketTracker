package com.example.kr_pc.crickettracker.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kr_pc.crickettracker.CircleTransform;
import com.example.kr_pc.crickettracker.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import static android.widget.Toast.LENGTH_LONG;

public class PlayerProfileActivity extends AppCompatActivity {
    private static final String JSON_URL = "http://cricapi.com/api/playerStats?apikey=f2ZI9j09ZbNo5BwdhlTs3lRt36z2&pid=";

    TextView playerName;
    TextView playerDob;
    ImageView playerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profile);

        playerName = findViewById(R.id.player_name);
        playerDob = findViewById(R.id.dob);
        playerImage = findViewById(R.id.player_image);

        Toast.makeText(getApplicationContext(), "Player id" + getIntent().getExtras().getString("playerId"), LENGTH_LONG).show();
        loadPlayerProfile(getIntent().getExtras().getString("playerId"));
    }

    private void loadPlayerProfile(final String playerId) {
        Toast.makeText(getApplicationContext(), "Hello" + playerId, LENGTH_LONG).show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL + playerId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            String name = obj.getString("name");
                            String dob = obj.getString("born");
                            String imageUrl = obj.getString("imageURL");
                            Log.e("Values", name + dob + imageUrl);
                            playerName.setText(name);
                            playerDob.setText(dob);

                            if(imageUrl != "null") {
                                Toast.makeText(getApplicationContext(), "image url" + imageUrl, LENGTH_LONG).show();
                                Picasso.with(PlayerProfileActivity.this)
                                        .load(imageUrl)
                                        .transform(new CircleTransform())
                                        .into(playerImage);
                            } else {
                                Picasso.with(PlayerProfileActivity.this)
                                        .load(R.drawable.player_profile)
                                        .transform(new CircleTransform())
                                        .into(playerImage);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}

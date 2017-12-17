package com.example.kr_pc.crickettracker.view;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kr_pc.crickettracker.R;
import com.example.kr_pc.crickettracker.model.Player;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_LONG;

public class PlayerSearchActivity extends AppCompatActivity {
    private static final String JSON_URL = "http://cricapi.com/api/playerFinder?apikey=f2ZI9j09ZbNo5BwdhlTs3lRt36z2&name=";

    EditText playerName;
    List<String> playerList;
    List<Long> playerIdList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_search);

        listView = findViewById(R.id.player_list);
        playerIdList = new ArrayList<>();
        Button playerSearchButton = findViewById(R.id.player_search_button);
        playerSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connectivityAvailable()) {
                    playerName = findViewById(R.id.player_name);
                    loadPlayerList(playerName.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), R.string.no_internet_message, LENGTH_LONG).show();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(PlayerSearchActivity.this, PlayerProfileActivity.class);
                intent.putExtra("playerId", playerIdList.get(i).toString());
                startActivity(intent);
            }
        });
    }

    private void loadPlayerList(String playerName) {

        playerList = new ArrayList<>();
        playerIdList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL + playerName,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named matches inside the object
                            //so here we are getting that json array
                            JSONArray playerArray = obj.getJSONArray("data");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < playerArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject playerObject = playerArray.getJSONObject(i);

                                //creating a match object and giving them the values from json object
                                Player player = new Player(playerObject.getLong("pid"), playerObject.getString("name"));
                                playerIdList.add(player.getPlayerId());
                                //adding the player to playerlist
                                playerList.add(player.getPlayerName());
                            }
                            //creating custom adapter object
                            //ListViewAdapter adapter = new ListViewAdapter(matchList, getApplicationContext());
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, playerList);
                            //adding the adapter to listview
                            listView.setAdapter(adapter);
                            listView.setVisibility(VISIBLE);
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

    public boolean connectivityAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

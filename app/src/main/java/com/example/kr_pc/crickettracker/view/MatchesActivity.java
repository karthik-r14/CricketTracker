package com.example.kr_pc.crickettracker.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kr_pc.crickettracker.R;
import com.example.kr_pc.crickettracker.model.Match;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MatchesActivity extends AppCompatActivity {
    private static final String JSON_URL = "http://cricapi.com/api/matches?apikey=f2ZI9j09ZbNo5BwdhlTs3lRt36z2";

    ListView listView;

    //the match list where we will store all the match objects after parsing json
    List<Match> matchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        listView = findViewById(R.id.listView);
        matchList = new ArrayList<>();

        loadMatchList();
    }

    private void loadScore(long id) {
        ScoreDialogFragment scoreDialogFragment = ScoreDialogFragment.newInstance();
        scoreDialogFragment.show(getSupportFragmentManager(), ScoreDialogFragment.TAG);

        Bundle arguments = new Bundle();
        arguments.putLong("id", id);
        scoreDialogFragment.setArguments(arguments);
    }

    private void loadMatchList() {
        //getting the progressbar
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        //making the progressbar visible
        progressBar.setVisibility(VISIBLE);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named matches inside the object
                            //so here we are getting that json array
                            JSONArray matchArray = obj.getJSONArray("matches");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < obj.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject matchObject = matchArray.getJSONObject(i);

                                //creating a match object and giving them the values from json object
                                Match match = new Match(matchObject.getLong("unique_id"), matchObject.getString("team-1"), matchObject.getString("team-2"));

                                //adding the match to matchlist
                                matchList.add(match);
                            }

                            //creating custom adapter object
                            ListViewAdapter adapter = new ListViewAdapter(matchList, getApplicationContext());

                            //adding the adapter to listview
                            listView.setAdapter(adapter);

                            //hiding the progressbar after completion
                            progressBar.setVisibility(INVISIBLE);


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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                loadScore(matchList.get(i).getId());
            }
        });
    }
}
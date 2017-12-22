package com.example.kr_pc.crickettracker.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.example.kr_pc.crickettracker.model.Fixture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class FixturesActivity extends AppCompatActivity {
    private static final String JSON_URL = "http://cricapi.com/api/matchCalendar?apikey=f2ZI9j09ZbNo5BwdhlTs3lRt36z2";

    ListView fixturesListView;
    List<Fixture> fixtureList;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixtures);

        fixturesListView = findViewById(R.id.fixtures_list);
        progressBar = findViewById(R.id.progressBar);
        fixtureList = new ArrayList<>();

        loadfixtures();
    }

    private void loadfixtures() {
        progressBar.setVisibility(VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            //we have the array named data inside the object
                            //so here we are getting that json array
                            JSONArray fixtureArray = obj.getJSONArray("data");

                            for (int i = 0; i < 10; i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject fixtureObject = fixtureArray.getJSONObject(i);
                                Fixture fixture = new Fixture(fixtureObject.getString("name"), fixtureObject.getString("date"));
                                fixtureList.add(fixture);
                            }

                            FixtureAdapter adapter = new FixtureAdapter(fixtureList, getApplicationContext());

                            //adding the adapter to listview
                            fixturesListView.setAdapter(adapter);
                            progressBar.setVisibility(GONE);
                            fixturesListView.setVisibility(VISIBLE);

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
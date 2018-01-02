package com.example.kr_pc.crickettracker.view;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_LONG;

public class MatchesActivity extends AppCompatActivity {
    private static final String JSON_URL = "http://cricapi.com/api/matches?apikey=f2ZI9j09ZbNo5BwdhlTs3lRt36z2";
    public static final String ALL = "ALL";

    ListView listView;
    Spinner matchTypeSpinner;

    //the match list where we will store all the match objects after parsing json
    List<Match> matchList;
    List<String> matchTypeList;
    List<Match> subMatchList;
    ListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        listView = findViewById(R.id.listView);
        matchTypeSpinner = findViewById(R.id.match_type_spinner);
        matchList = new ArrayList<>();
        matchTypeList = new ArrayList<>();
        subMatchList = new ArrayList<>();
        matchTypeList.add(ALL);

        loadMatchList();
    }

    private void loadMatchType() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, matchTypeList);
        matchTypeSpinner.setAdapter(adapter);
        matchTypeSpinner.setVisibility(VISIBLE);

        matchTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subMatchList.clear();
                if (matchTypeList.get(i).equals(ALL)) {
                    subMatchList.addAll(matchList);
                } else {
                    for (Match match : matchList) {
                        if (matchTypeList.get(i).equals(match.getMatchType())) {
                            subMatchList.add(match);
                        }
                    }
                }
                listViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public boolean connectivityAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
                            Log.e("Length = " + matchArray.length() + "All matches", matchArray.toString());
                            //now looping through all the elements of the json array
                            for (int i = 0; i < matchArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject matchObject = matchArray.getJSONObject(i);

                                //creating a match object and giving them the values from json object
                                Match match = new Match(matchObject.getLong("unique_id"), matchObject.getString("team-1"), matchObject.getString("team-2"), matchObject.getBoolean("matchStarted"), matchObject.getString("type"));

                                //adding the match to matchlist
                                if (match.isMatchStarted()) {
                                    matchList.add(match);

                                    if (!matchTypeList.contains(match.getMatchType())) {
                                        matchTypeList.add(match.getMatchType());
                                    }
                                }
                            }

                            //subMatchList.addAll(matchList);
                            //creating custom adapter object
                            listViewAdapter = new ListViewAdapter(subMatchList, getApplicationContext());

                            //adding the adapter to listview
                            listView.setAdapter(listViewAdapter);
                            loadMatchType();

                            //hiding the progressbar after completion
                            progressBar.setVisibility(GONE);
                            Toast.makeText(getApplicationContext(), R.string.tap_on_match, LENGTH_LONG).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        progressBar.setVisibility(GONE);
                        Toast.makeText(getApplicationContext(), getString(R.string.service_error_msg) + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (connectivityAvailable()) {
                    System.out.println("on click Match" + subMatchList.get(i).getId());
                    loadScore(subMatchList.get(i).getId());
                } else {
                    Toast.makeText(getApplicationContext(), R.string.no_internet_message, LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_anim, R.anim.move_right_anim);
    }
}
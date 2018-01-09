package com.myapp.kr_pc.crickettracker.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.myapp.kr_pc.crickettracker.R;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by KR-PC on 12-12-2017.
 */

public class ScoreDialogFragment extends DialogFragment {
    public static final String TAG = ScoreDialogFragment.class.getSimpleName();
    private static final String JSON_URL = "http://cricapi.com/api/cricketScore?apikey=f2ZI9j09ZbNo5BwdhlTs3lRt36z2&unique_id=";
    View view;

    public static ScoreDialogFragment newInstance() {
        return new ScoreDialogFragment();
    }

    ProgressBar progressBar;
    LinearLayout buttonLayout;
    Button refreshButton;
    Button dismissButton;
    TextView score;
    Long id;

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.score_dialog, null);

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(VISIBLE);

        buttonLayout = view.findViewById(R.id.button_layout);
        id = getArguments().getLong("id");
        loadLiveScore(id);
        score = view.findViewById(R.id.score);

        refreshButton = view.findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(connectivityAvailable()) {
                    progressBar.setVisibility(VISIBLE);
                    buttonLayout.setVisibility(GONE);
                    score.setVisibility(GONE);
                    loadLiveScore(id);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.no_internet_message, LENGTH_LONG).show();
                }
            }
        });

        dismissButton = view.findViewById(R.id.dismiss_button);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        builder.setView(view);
        Dialog dialog = builder.create();
        return dialog;
    }

    public boolean connectivityAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void loadLiveScore(Long id) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("Response is :", response);
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            Log.e("Obj is :", obj.toString());

                            if(obj.has("score")) {
                                String scoreText = obj.get("score").toString();
                                Log.e("Score is :", scoreText);
                                score.setText(scoreText);
                            }
                            //creating a match object and giving them the values from json object
                            score.setVisibility(VISIBLE);
                            progressBar.setVisibility(GONE);
                            buttonLayout.setVisibility(VISIBLE);

                            //creating custom adapter object
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}
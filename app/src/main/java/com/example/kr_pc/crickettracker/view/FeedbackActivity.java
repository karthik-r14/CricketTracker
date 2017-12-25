package com.example.kr_pc.crickettracker.view;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kr_pc.crickettracker.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.widget.Toast.LENGTH_LONG;

public class FeedbackActivity extends AppCompatActivity {

    public static final String FEEDBACK = "feedback";
    Button submitButton;
    EditText feedbackText;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(FEEDBACK);

        submitButton = findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitButtonClick();
            }
        });
    }

    private void onSubmitButtonClick() {
        feedbackText = findViewById(R.id.feedback_text);

        if (connectivityAvailable()) {

            if (feedbackText.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), R.string.empty_feedback_text_message, LENGTH_LONG).show();
            } else {

                myRef.push().setValue(feedbackText.getText().toString());
                Toast.makeText(getApplicationContext(), R.string.feedback_message, LENGTH_LONG).show();
                finish();
            }
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.no_internet_message), LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_anim, R.anim.move_right_anim);
    }

    public boolean connectivityAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
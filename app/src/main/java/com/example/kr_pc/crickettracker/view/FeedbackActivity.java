package com.example.kr_pc.crickettracker.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kr_pc.crickettracker.R;

public class FeedbackActivity extends AppCompatActivity {

    Button submitButton;
    EditText feedbackText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

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

        if (feedbackText.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.empty_feedback_text_message, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), R.string.feedback_message, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_anim, R.anim.move_right_anim);
    }
}
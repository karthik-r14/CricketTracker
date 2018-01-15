package com.myapp.kr_pc.crickettracker.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.myapp.kr_pc.crickettracker.R;

public class EditProfileActivity extends AppCompatActivity {

    public static final String USERNAME = "Username";
    public static final String PHONE_NUMBER = "Phone_number";
    public static final String EMAIL_ID = "Email_id";
    TextInputEditText name;
    TextInputEditText phoneNumber;
    TextInputEditText email;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        name = findViewById(R.id.name);
        phoneNumber = findViewById(R.id.phone_number);
        email = findViewById(R.id.email_id);

        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClick();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.MY_PROFILE, Context.MODE_PRIVATE);
        name.setText(sharedPreferences.getString(USERNAME, ""));
        phoneNumber.setText(sharedPreferences.getString(PHONE_NUMBER, ""));
        email.setText(sharedPreferences.getString(EMAIL_ID, ""));

        if (!sharedPreferences.getBoolean(MainActivity.LOGGED_IN, false)) {
            getSupportActionBar().setTitle(R.string.create_profile);
        }
    }

    private void onSaveButtonClick() {
        boolean validFieldsFlag = true;
        String userName = name.getText().toString();
        String userPhoneNumber = phoneNumber.getText().toString();
        String userEmailId = email.getText().toString();

        if (userName.trim().isEmpty()) {
            name.setError(getString(R.string.empty_name_message));
            validFieldsFlag = false;
        }

        if (userPhoneNumber.trim().isEmpty()) {
            phoneNumber.setError(getString(R.string.empty_phone_number_message));
            validFieldsFlag = false;
        } else if (userPhoneNumber.length() != 10) {
            phoneNumber.setError(getString(R.string.incomplete_phone_number_message));
            validFieldsFlag = false;
        }

        if (userEmailId.trim().isEmpty()) {
            email.setError(getString(R.string.empty_email_message));
            validFieldsFlag = false;
        }

        if (validFieldsFlag) {
            SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.MY_PROFILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(USERNAME, userName);
            editor.putString(PHONE_NUMBER, userPhoneNumber);
            editor.putString(EMAIL_ID, userEmailId);
            editor.commit();

            Intent intent = new Intent(EditProfileActivity.this, MyProfileActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_anim, R.anim.move_right_anim);
    }
}

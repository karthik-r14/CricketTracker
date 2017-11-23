package com.example.kr_pc.crickettracker.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kr_pc.crickettracker.CircleTransform;
import com.example.kr_pc.crickettracker.R;
import com.squareup.picasso.Picasso;

public class MyProfileActivity extends AppCompatActivity {

    TextView name;
    TextView phoneNumber;
    TextView emailId;
    Button logoutButton;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        name = findViewById(R.id.name);
        phoneNumber = findViewById(R.id.phone_number);
        emailId = findViewById(R.id.email_id);

        sharedPreferences = getSharedPreferences(MainActivity.MY_PROFILE, Context.MODE_PRIVATE);
        name.setText(sharedPreferences.getString(EditProfileActivity.USERNAME, ""));
        phoneNumber.setText(sharedPreferences.getString(EditProfileActivity.PHONE_NUMBER, ""));
        emailId.setText(sharedPreferences.getString(EditProfileActivity.EMAIL_ID, ""));

        ImageView imageView = findViewById(R.id.my_profile_image);
        Picasso.with(MyProfileActivity.this)
                .load(R.drawable.my_profile_image)
                .transform(new CircleTransform())
                .into(imageView);

        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(MainActivity.LOGGED_IN, true);
        editor.commit();

        logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyProfileActivity.this);
                builder.setCancelable(false);
                builder.setTitle(R.string.logout);
                builder.setMessage(R.string.logout_message);

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(MainActivity.LOGGED_IN, false);
                        editor.putString(EditProfileActivity.USERNAME, "");
                        editor.putString(EditProfileActivity.PHONE_NUMBER, "");
                        editor.putString(EditProfileActivity.EMAIL_ID, "");
                        editor.commit();
                        Toast.makeText(getApplicationContext(), R.string.on_logout_message, Toast.LENGTH_SHORT).show();
                    }
                })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.create().show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_profile:
                Intent intent = new Intent(MyProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
package com.example.kr_pc.crickettracker.view;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kr_pc.crickettracker.CircleTransform;
import com.example.kr_pc.crickettracker.R;
import com.squareup.picasso.Picasso;

public class AboutUsActivity extends AppCompatActivity {

    TextView githubHandle;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        githubHandle = findViewById(R.id.github_handle);
        githubHandle.setPaintFlags(githubHandle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        githubHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse(getResources().getString(R.string.github_handle));
                Intent intent = new
                        Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
            }
        });

        imageView = findViewById(R.id.my_image);
        Picasso.with(AboutUsActivity.this)
                .load(R.drawable.maker_photo)
                .transform(new CircleTransform())
                .into(imageView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_anim, R.anim.move_right_anim);
    }
}
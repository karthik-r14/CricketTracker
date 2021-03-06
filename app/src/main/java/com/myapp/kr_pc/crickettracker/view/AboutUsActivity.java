package com.myapp.kr_pc.crickettracker.view;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.kr_pc.crickettracker.CircleTransform;
import com.myapp.kr_pc.crickettracker.R;
import com.squareup.picasso.Picasso;

public class AboutUsActivity extends AppCompatActivity {

    TextView githubHandle;
    ImageView imageView;
    TextView cricApiLink;

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

        cricApiLink = findViewById(R.id.cricapi_link);
        cricApiLink.setPaintFlags(cricApiLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        cricApiLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse(getResources().getString(R.string.cric_api_link));
                Intent intent = new
                        Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
            }
        });


        imageView = findViewById(R.id.about_us_image);
        Picasso.with(AboutUsActivity.this)
                .load(R.drawable.about_us)
                .transform(new CircleTransform())
                .into(imageView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_anim, R.anim.move_right_anim);
    }
}
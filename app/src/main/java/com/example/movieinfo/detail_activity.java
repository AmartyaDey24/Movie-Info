package com.example.movieinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class detail_activity extends AppCompatActivity {

    ImageView image_detail;
    TextView name_detail,overview_detail,rating_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        image_detail = findViewById(R.id.image_detail);
        name_detail = findViewById(R.id.name_detail);
        overview_detail = findViewById(R.id.overview_detail);
        rating_detail = findViewById(R.id.rating_detail);

        Bundle bundle = getIntent().getExtras();

        String mTitle = bundle.getString("mTitle");
        String mThumbnail = bundle.getString("mThumbnail");
        String mSummery = bundle.getString("mSummery");
        Double mRating = bundle.getDouble("mRating");

        Glide.with(this).load(mThumbnail).into(image_detail);
        rating_detail.setText(mRating.toString()+"/10");
        name_detail.setText(mTitle);
        overview_detail.setText(mSummery);
    }
}
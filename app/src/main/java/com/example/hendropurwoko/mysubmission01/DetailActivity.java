package com.example.hendropurwoko.mysubmission01;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    TextView tvJudul, tvDesc, tvVote, tvDate, tvPopularity;
    ImageView imgFoto;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Context c = getApplicationContext();
        String judul, desc, date, popularity, vote, image;

        tvJudul = (TextView)findViewById(R.id.tv_judul_detail);
        tvDesc = (TextView)findViewById(R.id.tv_desc_detail);
        tvVote = (TextView)findViewById(R.id.tv_vote_detail);
        tvDate = (TextView)findViewById(R.id.tv_date_detail);
        tvPopularity = (TextView)findViewById(R.id.tv_popularity_detail);
        imgFoto = (ImageView)findViewById(R.id.img_foto_detail);

        bundle = new Bundle();
        bundle = getIntent().getExtras();

        judul = bundle.getString("judul").toString();
        desc = bundle.getString("desc").toString();
        date = bundle.getString("date").toString();
        vote = bundle.getString("vote").toString();
        popularity = bundle.getString("popularity").toString();
        image = bundle.getString("image").toString();

        tvJudul.setText(judul);
        tvDesc.setText(desc);
        tvDate.setText(date);
        tvVote.setText(vote);
        tvPopularity.setText(popularity);

        Glide.with(c)
                .load(image)
                .override(350, 350)
                .into(imgFoto);
    }
}

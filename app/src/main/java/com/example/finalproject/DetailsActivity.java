package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    TextView title,date,league,league_id,url;
    ImageView leagueIcon;
    ImageButton navButton;
    int[] ImageList = {R.drawable.premierleague,R.drawable.laliga,R.drawable.ligueone
        ,R.drawable.seriea,R.drawable.league};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        title = findViewById(R.id.titleTxt);
        date = findViewById(R.id.fixtureDatetxt);
        league = findViewById(R.id.leagueTxt);
        league_id = findViewById(R.id.idTextView);
        url = findViewById(R.id.urlTxt);
        leagueIcon = findViewById(R.id.leagueImageView);
        Intent detailsIntent = getIntent();
        Bundle detailsBundle = detailsIntent.getBundleExtra("moreDetails");
        ArrayList<String> detailArray = detailsBundle.getStringArrayList("details");
        final String leagueStr = detailArray.get(0), leagueidStr = detailArray.get(1), titleStr = detailArray.get(3),
             dateStr = detailArray.get(4), urlStr = detailArray.get(2);
        title.setText(titleStr);
        date.setText(dateStr);
        url.setText(urlStr);
        league_id.setText(leagueidStr);
        league.setText(leagueStr);
        leagueIcon.setImageResource(ImageList[4]);
        if (leagueStr.equals("ENGLAND: Premier League")){
            leagueIcon.setImageResource(ImageList[0]);
        }
        else if(leagueStr.equals("SPAIN: La Liga")){
            leagueIcon.setImageResource(ImageList[1]);
        }
        else if(leagueStr.equals("FRANCE: Ligue 1")){
            leagueIcon.setImageResource(ImageList[2]);
        }
        else if(leagueStr.equals("ITALY: Serie A")){
            leagueIcon.setImageResource(ImageList[3]);
        }
        else{
            leagueIcon.setImageResource(ImageList[4]);
        }
        navButton = findViewById(R.id.navBtn);
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this,MapsActivity.class);
                intent.putExtra("League",leagueStr);
                startActivity(intent);
            }
        });
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.8));


    }
}

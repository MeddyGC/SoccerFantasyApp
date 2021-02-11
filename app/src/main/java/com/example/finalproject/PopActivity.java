package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PopActivity extends AppCompatActivity {
    TextView playername, playernum, position, age, height;
    DataBaseHelper mDatabaseHelper;
    SQLiteDatabase db;
    ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
        Intent intent = getIntent();
        final String pName = intent.getStringExtra("playername");
        button = findViewById(R.id.imageButton);
        playername = findViewById(R.id.pNameTxt);
        playernum = findViewById(R.id.playerNotxt);
        position = findViewById(R.id.postv);
        age = findViewById(R.id.ageTextView);
        height = findViewById(R.id.hgtTxt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nameIntent = new Intent(PopActivity.this,ChatActivity.class);
                nameIntent.putExtra("thePlayerName",pName);
                startActivity(nameIntent);
            }
        });
        mDatabaseHelper = new DataBaseHelper(this);
        db = mDatabaseHelper.getReadableDatabase();
        String table = DataBaseHelper.TABLE_NAME;
        String col = DataBaseHelper.COLS_2;
        Cursor c =  db.rawQuery("SELECT * FROM "+ table +" WHERE "+ col + "='" + pName + "'",null);
        if(c.moveToFirst()) {
            playername.setText(pName);
            playernum.setText(c.getString(2));
            position.setText(c.getString(3));
            age.setText(c.getString(4));
            height.setText(c.getString(5));
        }
        else{
            Toast.makeText(this,"Not successful",Toast.LENGTH_SHORT).show();
        }
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.8));

    }
}

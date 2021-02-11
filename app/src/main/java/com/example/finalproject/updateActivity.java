package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class updateActivity extends Fragment {
    public static final String FRAGMENT_TAG = "ADD_FRAGMENT";

    EditText playerName, playerNumber,ageEditText, heightEditText, positionEditText;
    Button searchBtn, updateBtn;
    SQLiteDatabase db;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_update,container,false);
        playerName = rootView.findViewById(R.id.playerNameTxt);

        playerNumber = rootView.findViewById(R.id.playerNumber);
        ageEditText = rootView.findViewById(R.id.ageTxt);
        positionEditText = rootView.findViewById(R.id.posTxt);
        heightEditText = rootView.findViewById(R.id.heightTxt);
        searchBtn = rootView.findViewById(R.id.searchBtn);
        updateBtn = rootView.findViewById(R.id.updateBtn);
        final DataBaseHelper openHelper = new DataBaseHelper(getContext());

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pName = playerName.getText().toString();
                db = openHelper.getReadableDatabase();
                String table = DataBaseHelper.TABLE_NAME;
                String col = DataBaseHelper.COLS_2;
                Cursor c =  db.rawQuery("SELECT * FROM "+ table +" WHERE "+ col + "='" + pName + "'",null);
                if(c.moveToFirst()){
                    playerNumber.setText(c.getString(2));
                    positionEditText.setText(c.getString(3));
                    ageEditText.setText(c.getString(4));
                    heightEditText.setText(c.getString(5));

                }
                else{
                    playerName.setError("Invalid Player Name");
                }

            }
        });

        final ArrayList<String> arrayList =  new ArrayList<>();
        arrayList.add("GK");arrayList.add("DF");arrayList.add("MD");arrayList.add("AT");
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pName = playerName.getText().toString();
                db = openHelper.getReadableDatabase();
                String table = DataBaseHelper.TABLE_NAME;
                String col = DataBaseHelper.COLS_2;
                Cursor c =  db.rawQuery("SELECT * FROM "+ table +" WHERE "+ col + "='" + pName + "'",null);
                if(c.moveToFirst()){
                    Toast.makeText(getContext(),"Player exists",Toast.LENGTH_SHORT).show();

                    pName = playerName.getText().toString();
                    String pNum = playerNumber.getText().toString();
                    String position = positionEditText.getText().toString();
                    String age = ageEditText.getText().toString();
                    String height = heightEditText.getText().toString();
                    if (arrayList.contains(position) && pNum.matches("^[0-9]*$") &&pNum.length() <=3 && age.matches("^[0-9]*$") && Integer.valueOf(age) < 100 ) {
                        updateData(pName, pNum, position, age, height);
                        Toast.makeText(getContext(), "UPDATE SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        playerNumber.setError("Invalid Player Number");
                        ageEditText.setError("Invalid Age");
                        heightEditText.setError("Invalid Height");
                        positionEditText.setError("Invalid Position");
                    }

                }
                else {
                    playerName.setError("Invalid Player Name");


                }


            }
        });
        return rootView;
    }

    public Boolean updateData(String pName, String pNum, String position, String age, String height) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.COLS_2,pName);
        contentValues.put(DataBaseHelper.COLS_3,pNum);
        contentValues.put(DataBaseHelper.COLS_4,position);
        contentValues.put(DataBaseHelper.COLS_5,age);
        contentValues.put(DataBaseHelper.COLS_6,height);
        String id = playerName.getText().toString();
        return db.update(DataBaseHelper.TABLE_NAME, contentValues, DataBaseHelper.COLS_2 + "=?", new String[]{id})>0;

    }



}

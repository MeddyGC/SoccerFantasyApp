package com.example.finalproject;
/**
 Author : Mohamed Conde
 Due Date: 1/24/2020
 Description: A This activitity get the details entered by the user on a form and prompts them to select an image



 **/
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteActivity extends Fragment {
    public static final String FRAGMENT_TAG = "DELETE ACTIVITY";

    EditText playerName;
    Button delete;
    SQLiteDatabase db;

    public DeleteActivity(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_delete,container,false);
        playerName = rootView.findViewById(R.id.playerNameToDelete);

        delete = rootView.findViewById(R.id.deleteBtn);
        final DataBaseHelper openHelper = new DataBaseHelper(getContext());


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pName = playerName.getText().toString();
                db = openHelper.getReadableDatabase();
                String table = DataBaseHelper.TABLE_NAME;
                String col = DataBaseHelper.COLS_2;
                Cursor c =  db.rawQuery("SELECT * FROM "+ table +" WHERE "+ col + "='" + pName + "'",null);
                if(c.moveToFirst()){
                    Toast.makeText(getContext(),"Player exists",Toast.LENGTH_SHORT).show();
                    db = openHelper.getWritableDatabase();
                    pName = playerName.getText().toString();
                    deleteData(pName);
                    Toast.makeText(getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();

                }
                else {
                    playerName.setError("Invalid Player Name");


                }

            }
        });
        return rootView;
    }
    public boolean deleteData(String name){
        return db.delete(DataBaseHelper.TABLE_NAME, DataBaseHelper.COLS_2 + "=?", new String[]{name})>0;
    }
}

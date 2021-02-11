package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
/**
 Author : Mohamed Conde
 Due Date: 1/24/2020
 Description: A This activitity get the details entered by the user on a form and prompts them to select an image



 **/

public class addActivity extends Fragment  {

    public static final String FRAGMENT_TAG = "ADD_FRAGMENT";

    EditText playerName, playerNumber;
    TextView ageTv, heightTv;
    Spinner spinner;
    SeekBar seekBar;
    Button dateBtn, addBtn;
    SQLiteDatabase db;
    String posStr;

    public addActivity(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_add, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.appbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        playerName = rootView.findViewById(R.id.playerNameTxt);
        playerNumber = rootView.findViewById(R.id.playerNumber);
        ageTv = rootView.findViewById(R.id.ageTxt);
        heightTv = rootView.findViewById(R.id.heightTxt);
        spinner = rootView.findViewById(R.id.spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.Position,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(),text, Toast.LENGTH_SHORT).show();
                posStr = text;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        seekBar = rootView.findViewById(R.id.seekBar);
        final ProgressBar progressBar = rootView.findViewById(R.id.progressBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressBar.setProgress(progress/10);
                heightTv.setText(""+progress/10.00+"ft");


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        dateBtn = rootView.findViewById(R.id.dobBtn);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment datePicker = new DatePickerFragment(); // creates a datepicker
                datePicker.show(getFragmentManager(),"date picker"); //shows datepicker
            }
        });
        addBtn = rootView.findViewById(R.id.addButton);
        final DataBaseHelper openHelper = new DataBaseHelper(getContext());

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pName = playerName.getText().toString();
                db = openHelper.getReadableDatabase();
                String table = DataBaseHelper.TABLE_NAME;
                String col = DataBaseHelper.COLS_2;
                Cursor c =  db.rawQuery("SELECT * FROM "+ table +" WHERE "+ col + "='" + pName + "'",null);
                if(c.moveToFirst()){
                    Toast.makeText(getContext(),"Player exists",Toast.LENGTH_SHORT).show();
                    playerName.setError("Enter a different player");

                }
                else {
                    String position = posStr;
                    String pNum = playerNumber.getText().toString();
                    if(pNum.matches("^[0-9]*$")&& pNum.length() <= 3){

                        pName = playerName.getText().toString();

                        String age = ageTv.getText().toString();

                        String height = heightTv.getText().toString();
                        db = openHelper.getWritableDatabase();
                        insertData(pName, pNum, position, age, height);
                    }
                    else{
                        playerNumber.setError("Invalid Number");

                    }
                }




            }
        });
        return rootView;



    }

    private void insertData(String pName, String pNum, String position, String age, String height) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.COLS_2, pName);
        contentValues.put(DataBaseHelper.COLS_3, pNum);
        contentValues.put(DataBaseHelper.COLS_4, position);
        contentValues.put(DataBaseHelper.COLS_5, age);
        contentValues.put(DataBaseHelper.COLS_6, height);
        long id = db.insert(DataBaseHelper.TABLE_NAME, null, contentValues);
        if(id>0){
            Toast.makeText(getContext(),"Save successful " ,Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Save not successful", Toast.LENGTH_SHORT).show();
        }
    }


}

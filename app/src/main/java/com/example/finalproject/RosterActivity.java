package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RosterActivity extends Fragment {
    View view;
    String text;
    SQLiteDatabase db;
    private static final String TAG = "RosterActivity";
    public static final String FRAGMENT_TAG = "RosterActivity";
    private  RecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    ListView invListView;
    Button backBtn;
    DataBaseHelper mDatabaseHelper;
    ArrayList<String> listData;
    Spinner sort;
    String sortStr = "Sort by Height";
    //int[] images = {R.drawable.avatar,R.drawable.avatar,R.drawable.avatar,R.drawable.avatar};
    int images = R.drawable.avatar;
    public RosterActivity(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_recyclerroster, container, false);
        mDatabaseHelper = new DataBaseHelper(getContext());
        Toolbar toolbar = rootView.findViewById(R.id.appbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(getContext(),4);
        recyclerView.setHasFixedSize((true));
        recyclerView.setLayoutManager(layoutManager);

        sort = rootView.findViewById(R.id.sortRoster);
        final ArrayAdapter<CharSequence> sortadapter = ArrayAdapter.createFromResource(getContext(),R.array.Sort,android.R.layout.simple_spinner_item);
        sortadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sort.setAdapter(sortadapter);
        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(),text, Toast.LENGTH_SHORT).show();
                sortStr = text;
                populateListView(sortStr);
                adapter = new RecyclerAdapter(images,listData);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String name = listData.get(position).toString();

                Intent intent = new Intent(getContext(),PopActivity.class);
                intent.putExtra("playername",name);
                Toast.makeText(getContext(),name,Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, final int position) {
                new AlertDialog.Builder(getContext()).setIcon(android.R.drawable.ic_delete).
                        setTitle("Are you sure?")
                        .setMessage("Do you want to delete this item")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(getApplicationContext(),listData.get(itemId),Toast.LENGTH_SHORT).show();

                                //listData.remove(position);

                                Boolean deletedRow = mDatabaseHelper.deleteData(listData.get(position));
                                if(deletedRow){
                                    Toast.makeText(getContext(),"Delete successful " + position,Toast.LENGTH_SHORT).show();
                                    listData.remove(position);
                                }
                                else {
                                    Toast.makeText(getContext(),"Delete not successful " + position,Toast.LENGTH_SHORT).show();
                                }
                                adapter.notifyDataSetChanged();



                            }
                        })
                        .setNegativeButton("no",null).show();

            }
        }));

        //adapter.notifyDataSetChanged();
        return rootView;
    }


    private void populateListView(String sortStr) {
        Log.d(TAG, "populateListView: Displaying data in Listview.");
        if (sortStr.equals("lastEntry")) {
            //get the data and append to list
            Cursor data = mDatabaseHelper.getData();
            listData = new ArrayList<>();
            while (data.moveToNext()) {
                listData.add(data.getString(1));



            }
        }
        else if(sortStr.equals("Sort by Name")){
            Cursor data = mDatabaseHelper.getDataByName();
            listData = new ArrayList<>();
            while (data.moveToNext()) {
                listData.add(data.getString(1));


            }
//            Fragment fragment = null;
//            fragment = getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
//            final FragmentTransaction ft = getFragmentManager().beginTransaction();
//            ft.detach(fragment);
//            ft.attach(fragment);
//            ft.commit();

        }
        else if(sortStr.equals("Sort by Age")){
            Cursor data = mDatabaseHelper.getDataByAge();
            listData = new ArrayList<>();
            while (data.moveToNext()) {
                listData.add(data.getString(1));

            }


        }
        else{
            Cursor data = mDatabaseHelper.getDataByHeight();
            listData = new ArrayList<>();
            while (data.moveToNext()) {
                listData.add(data.getString(1));

            }
        }

        //Toast.makeText(getApplicationContext(), (CharSequence) listData,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu,menu);

        super.onCreateOptionsMenu(menu, inflater);
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.actionsetting){
            view = getActivity().getWindow().getDecorView();
            view.setBackgroundColor(R.drawable.googleg_standard_color_18); //material_on_background_disabled
        }
        if (id == R.id.actionsetting2){
            view = getActivity().getWindow().getDecorView();
            view.setBackgroundColor(R.style.CustomToolbarStyle);
        }


        return super.onOptionsItemSelected(item);
    }

}

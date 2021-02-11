package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FootballActivity extends Fragment {
    public static final String FRAGMENT_TAG = "FOOTBALL_FRAGMENT";
    ArrayList<String> nameList = new ArrayList<>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    public FootballActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_football, container, false);
        final ArrayList<String> footballArrayList = new ArrayList<>();
        mRecyclerView = rootView.findViewById(R.id.footballRecyclerview);


        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.scorebat.com/video-api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonFootballApi jsonFootballApi = retrofit.create(JsonFootballApi.class);
        Call<List<Football>> call = jsonFootballApi.getFootball();
        call.enqueue(new Callback<List<Football>>() {
            @Override
            public void onResponse(Call<List<Football>> call, Response<List<Football>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                    return;
                }
                final List<Football> footballList = response.body();


                for (Football football : footballList) {
                    String content = "";
                    content +=  football.getTitle() + "\n\n";
                    content = content.replace("-","vs");
//                    content += "Date:" + football.getDate() + "\n\n";
//                    content += "Competition:" + football.getCompetition() + "\n";
//                    //content += "Name:" + football.getCompetition().getClass(). + "\n";
//                    content += "***************************\n\n";
                    footballArrayList.add(content);

                    //Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    //textViewResult.append(content);

                }


                String s = String.valueOf(footballList.size());
                Toast.makeText(getContext(), String.valueOf(footballList.size()), Toast.LENGTH_SHORT).show();
                mRecyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getContext());
                adapter = new mRecyclerAdapter(footballArrayList);
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getContext(),DetailsActivity.class);
                        Football football = footballList.get(position);
                        String Title = football.getTitle();
                        String Date = football.getDate();
                        Map map = (Map) football.getCompetition();
                        String country = (String) map.get("name");
                        Double leagueID = (Double) map.get("id");
                        String url = (String) map.get("url");
                        nameList.add(country);
                        nameList.add(String.valueOf(leagueID));
                        nameList.add(url);
                        nameList.add(Title);
                        nameList.add(Date);
                        Bundle moreDetails = new Bundle();
                        moreDetails.putStringArrayList("details",nameList);
                        intent.putExtra("moreDetails",moreDetails);
                        startActivity(intent);
                        nameList.clear();


                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
            }

            @Override
            public void onFailure(Call<List<Football>> call, Throwable t) {
                Toast.makeText(getContext(), "Not successful", Toast.LENGTH_SHORT).show();;

            }
        });

        return rootView;
    }
}

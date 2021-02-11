package com.example.finalproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonFootballApi {
    @GET("https://www.scorebat.com/video-api/v1/")
    Call<List<Football>> getFootball();
}

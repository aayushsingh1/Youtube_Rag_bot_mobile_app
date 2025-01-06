package com.example.myyoutuberagbot;


import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/extracted_channels")
    Call<data_Channel_info> getChannelData();
}

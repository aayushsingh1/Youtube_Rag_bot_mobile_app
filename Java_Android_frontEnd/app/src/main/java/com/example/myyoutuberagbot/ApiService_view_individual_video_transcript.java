package com.example.myyoutuberagbot;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService_view_individual_video_transcript {

    @POST("view_transcript/{c_name}")
    Call<data_Individial_transcript_view> updateData(@Path("c_name") String channel_name, @Body Map<String, String> requestBody);
}

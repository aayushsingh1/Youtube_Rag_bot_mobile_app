package com.example.myyoutuberagbot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService_view_list_of_transcripts {

    @GET("/view_transcript/{channel_name}")
    Call<data_Transcript_list_in_channel> getSubfiles(@Path("channel_name") String c_name);
}

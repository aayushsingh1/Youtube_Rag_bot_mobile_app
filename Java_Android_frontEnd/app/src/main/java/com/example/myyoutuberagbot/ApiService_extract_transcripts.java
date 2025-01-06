package com.example.myyoutuberagbot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService_extract_transcripts {

    @GET("save_transcript/{channel_name}")
    Call<data_save_transcript> get_result_save_transcript (@Path("channel_name") String channel_name);


}

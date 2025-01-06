package com.example.myyoutuberagbot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService_generate_embeddings {

    @GET("/chunk_transcript_and_embedding/{channel_name}")
    Call <data_generate_chunks_and_embeddings> get_embedding_results (@Path("channel_name") String c_name);
}

package com.example.myyoutuberagbot;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiService_rag_question_reply {
    @POST("/ask_question/{channel_name}")
    Call<data_AskQuestionReply> get_Answer(@Path("channel_name") String channel_name , @Body Map<String, String> requestBody );
}
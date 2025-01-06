package com.example.myyoutuberagbot;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class view_individual_video_transcript extends AppCompatActivity {

    private TextView container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_individual_transcript);

        container = findViewById(R.id.transcript_paragraph);
        String channel_name = getIntent().getStringExtra("channel_name");
        String video_name = getIntent().getStringExtra("video_name");
        Map<String, String> video_name_map = new HashMap<>();
        video_name_map.put("video_name", video_name);

        list_individual_transcript(channel_name,video_name_map);

    }


    private void list_individual_transcript(String channel_name , Map<String,String> video_name_map)
    {
        ApiService_view_individual_video_transcript apiService =  ApiClient.getClient().create(ApiService_view_individual_video_transcript.class);
        Call<data_Individial_transcript_view> call = apiService.updateData(channel_name,video_name_map);

        call.enqueue(new Callback<data_Individial_transcript_view>() {
            @Override
            public void onResponse(Call<data_Individial_transcript_view> call, Response<data_Individial_transcript_view> response) {
                if (response.isSuccessful() && response.body() != null) {

                    data_Individial_transcript_view data = response.body();

                    container.setText(data.get_contents());


                }
                }

            @Override
            public void onFailure(Call<data_Individial_transcript_view> call, Throwable throwable) {

                container.setText("Some error occured");

            }

        });

    }

}

package com.example.myyoutuberagbot;
import java.lang.String;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class view_all_transcripts_in_a_channel extends AppCompatActivity{
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_list_of_transcripts_in_channel);

        container = findViewById(R.id.transcript_buttons);
        String channel_name = getIntent().getStringExtra("channel_name");


        // Parse the JSON data
        Create_transcript_Buttons(channel_name);
    }

    private void Create_transcript_Buttons(String channel_name){

        ApiService_view_list_of_transcripts apiService = ApiClient.getClient().create(ApiService_view_list_of_transcripts.class);
        Call<data_Transcript_list_in_channel> call = apiService.getSubfiles(channel_name);

        call.enqueue(new Callback<data_Transcript_list_in_channel>() {
            @Override
            public void onResponse(Call<data_Transcript_list_in_channel> call, Response<data_Transcript_list_in_channel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data_Transcript_list_in_channel channelData = response.body();


                    for (String list : channelData.getSubfiles())
                    {Button button = new Button(getApplicationContext());
                        button.setText(list);
                        button.setOnClickListener(v -> {
                            // Add your button click logic here
                            Toast.makeText(view_all_transcripts_in_a_channel.this, "Clicked: " + list, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(view_all_transcripts_in_a_channel.this, view_individual_video_transcript.class);
                            intent.putExtra("channel_name", channel_name);
                            intent.putExtra("video_name", list);
                            startActivity(intent);

                        });

                        container.addView(button);

                    }
                }
            }

            @Override
            public void onFailure(Call<data_Transcript_list_in_channel> call, Throwable t) {

                Button b = new Button(getApplicationContext());
                b.setText("Some Error occured");
                // Handle the failure
                container.addView(b);

            }
        });





    }
}

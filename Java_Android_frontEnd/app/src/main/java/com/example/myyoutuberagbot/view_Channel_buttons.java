package com.example.myyoutuberagbot;
import java.lang.String;

import java.util.Map;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class view_Channel_buttons extends AppCompatActivity {

    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_channels_in_buttons);

        container = findViewById(R.id.channel_buttons);


        // Parse the JSON data
        CreateButtons();
    }

    private void CreateButtons() {


            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<data_Channel_info> call = apiService.getChannelData();

            call.enqueue(new Callback<data_Channel_info>() {
                @Override
                public void onResponse(Call<data_Channel_info> call, Response<data_Channel_info> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        data_Channel_info dataChannelInfo = response.body();
                        StringBuilder channelInfoBuilder = new StringBuilder();


                        for (Map.Entry<String, data_Channel_info.ChannelInfo> entry : dataChannelInfo.getChannels().entrySet()) {
                            String channelName = entry.getKey();
                            Button button = new Button(getApplicationContext());
                            button.setText(channelName);
                            button.setOnClickListener(v -> {
                                // Add your button click logic here
                                Toast.makeText(view_Channel_buttons.this, "Clicked: " + channelName, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(view_Channel_buttons.this, view_all_transcripts_in_a_channel.class);
                                intent.putExtra("channel_name", channelName);
                                startActivity(intent);



                            });

                            // Add the button to the container
                            container.addView(button);

                        }
                    }
                }

                        @Override
                        public void onFailure (Call <data_Channel_info> call, Throwable t){

                    Button b = new Button(getApplicationContext());
                    b.setText("Some error occured");
                            // Handle the failure
                            container.addView(b);
                        }


                    });



        }
}




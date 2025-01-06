package com.example.myyoutuberagbot;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class View_extracted_channels extends AppCompatActivity {
    private ExecutorService executorService;
    private TextView channelDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_extracted_channels);
        // Initialize the executor service
        executorService = Executors.newSingleThreadExecutor();
        channelDataTextView = findViewById(R.id.view_extracted_channels);


    }
    @Override
    protected void onStart() {
        super.onStart();
        fetch_extracted_channels();
    }



    private void fetch_extracted_channels() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<data_Channel_info> call = apiService.getChannelData();

        call.enqueue(new Callback<data_Channel_info>() {
            @Override
            public void onResponse(Call<data_Channel_info> call, Response<data_Channel_info> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data_Channel_info dataChannelInfo = response.body();
                    StringBuilder channelInfoBuilder = new StringBuilder();
                    channelDataTextView.setText(response.body().toString());

                    for (Map.Entry<String, data_Channel_info.ChannelInfo> entry : dataChannelInfo.getChannels().entrySet()) {
                        String channelName = entry.getKey();
                        data_Channel_info.ChannelInfo channelInfo = entry.getValue();
                        channelInfoBuilder.append("Name: ").append(channelName)
                                .append("\nTranscripts Saved: ").append(channelInfo.getTranscriptsSaved())
                                .append("\nLast Updated On: ").append(channelInfo.getLastUpdatedOn())
                                .append("\n\n"); // Add some spacing between channels
                    }

                    // Set the text to the TextView
                    channelDataTextView.setText(channelInfoBuilder.toString());
                } else {
                    channelDataTextView.setText("Response not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<data_Channel_info> call, Throwable t) {
                // Handle the failure
                channelDataTextView.setText("Some error occured");
            }
        });
    }
}
package com.example.myyoutuberagbot;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class view_Extract_transcript_and_embeddings extends AppCompatActivity {

    private TextInputEditText channel_name_input;
    private Button submit_button ;
    private TextView output_text;
    private boolean submit_button_clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extract_new_channel_transcripts);

        channel_name_input = findViewById(R.id.enter_channel_name_input_for_extraction);
        submit_button = findViewById(R.id.submit_button);
        output_text = findViewById(R.id.extraction_result);

        submit_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // freezing submit button

                String channel_name_entered = channel_name_input.getText().toString();
                if (!channel_name_entered.isEmpty()) {
                    submit_button.setEnabled(false);
                    submit_button.setAlpha(0.5f);

                    submit_button_clicked = true;
                    send_request(channel_name_entered);

                }


            }

        });
    }


        private void send_request(String channel_name)
        {

            ////////////// Downloading transcripts for videos now
            ApiService_extract_transcripts apiservice = ApiClient.getClient().create(ApiService_extract_transcripts.class);
            Call<data_save_transcript> call = apiservice.get_result_save_transcript(channel_name);

            call.enqueue(new Callback<data_save_transcript>() {
                @Override
                public void onResponse(Call<data_save_transcript> call, Response<data_save_transcript> response) {
                    data_save_transcript response_for_transcript_extraction = response.body();
                    int success_or_not = response_for_transcript_extraction.get_isSuccessful();

                    if (success_or_not == 0)
                {
                    output_text.setTextColor(Color.RED);
                    output_text.setText("Error :"+response_for_transcript_extraction.getReason_for_unsucessful_action());




                }

                    else
                    {
                        String result = "New transcripts saved :"+response_for_transcript_extraction.getNew_videos_transcripts_saved();
                        result+="\n";
                        result+="Transcripts already extracted :"+response_for_transcript_extraction.getVideo_transcript_already_present();
                        result+="\n";
                        result+="Final number of transcripts present :"+response_for_transcript_extraction.getTotal_transcripts_now_present();
                        output_text.setTextColor(Color.GREEN);

                        output_text.setText(result);
                        //// to enter here ...
                    }

                }




                @Override
                public void onFailure(Call<data_save_transcript> call, Throwable throwable) {

                    output_text.setText("Failure to connect to my pc");

                }
            });


            ///// Chunking transcripts into smaller text size and then embedding

            ApiService_generate_embeddings apiService_generate_embedd = ApiClient.getClient().create(ApiService_generate_embeddings.class);
            Call<data_generate_chunks_and_embeddings> call_no_two = apiService_generate_embedd.get_embedding_results(channel_name);

            call_no_two.enqueue(new Callback<data_generate_chunks_and_embeddings>() {
                @Override
                public void onResponse(Call<data_generate_chunks_and_embeddings> call, Response<data_generate_chunks_and_embeddings> response) {
                    data_generate_chunks_and_embeddings embedd_result = response.body();
                    int success_status = embedd_result.getIs_successful();

                    if (success_status == 1)
                    {
                        String result = "No. of chunks created:";
                        result += embedd_result.getChunks_created_from_transcripts();
                        result+="\n";
                        result+="New embeddings generated from chunks:";
                        result+=embedd_result.getNew_Embeddings_generated_from_chunks();
                        result+="\n";
                        result+="Embeddings already saved from old chunks:";
                        result+=embedd_result.getEmbeddings_already_saved_from_old_chunks();
                        result+="\n";
                        int total_embedd_present = embedd_result.getNew_Embeddings_generated_from_chunks() + embedd_result.getEmbeddings_already_saved_from_old_chunks();
                        result+="Total Embeddings now present:";
                        result+=total_embedd_present;
                        String curr_text = output_text.getText().toString();
                        int curr_text_color = output_text.getCurrentTextColor();




                        curr_text = curr_text +"\n" + result;

                        output_text.setTextColor(curr_text_color);
                        output_text.setText(curr_text);
                    }

                    else

                    {
                        String curr_text = output_text.getText().toString();
                        curr_text += embedd_result.getReason_for_unsuccessful_response();
                        output_text.setText(curr_text);

                    }
                }

                @Override
                public void onFailure(Call<data_generate_chunks_and_embeddings> call, Throwable throwable) {



                    output_text.setText("Failure to connect to my pc");

                }
            });
















        }
    }




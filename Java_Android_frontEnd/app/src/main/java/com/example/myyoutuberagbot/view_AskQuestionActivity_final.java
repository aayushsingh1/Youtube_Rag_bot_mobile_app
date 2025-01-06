package com.example.myyoutuberagbot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class view_AskQuestionActivity_final extends AppCompatActivity {
    private EditText inputField;
    private TextView outputField;
    private Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_form);

        inputField = findViewById(R.id.inputText);
        outputField = findViewById(R.id.outputText);
        submitButton =  findViewById(R.id.submitButton);
        String channel_name = getIntent().getStringExtra("channel_name");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = inputField.getText().toString();
                if (!question.isEmpty()) {
                    outputField.setText("Please wait .....");
                    sendPostRequest(channel_name,question);
                }

                else {
                Toast.makeText(view_AskQuestionActivity_final.this, "Please enter some data", Toast.LENGTH_SHORT).show();
            }
        }
    });
}



    private void sendPostRequest (String channel_name , String question )
    {
        Map<String,String> quest_kay_value_format = new HashMap<>();
        quest_kay_value_format.put("question",question);
        ApiService_rag_question_reply apiService = ApiClient.getClient().create(ApiService_rag_question_reply.class);
        Call<data_AskQuestionReply> call = apiService.get_Answer(channel_name,quest_kay_value_format);

        call.enqueue(new Callback<data_AskQuestionReply>() {
            @Override
            public void onResponse(Call<data_AskQuestionReply> call, Response<data_AskQuestionReply> response) {
                data_AskQuestionReply final_answer = response.body();
                outputField.setText(final_answer.get_final_answer());

            }

            @Override
            public void onFailure(Call<data_AskQuestionReply> call, Throwable throwable) {
                outputField.setText("Some error occured");
            }
        });




    }



}

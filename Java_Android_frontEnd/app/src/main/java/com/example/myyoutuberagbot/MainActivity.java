
package com.example.myyoutuberagbot;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ExecutorService executorService;
    private TextView text_1;
    private TextView text_2;
    private ImageView img_conn_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_1 = findViewById(R.id.main_message_1);
        text_2 = findViewById(R.id.main_message_2);
        img_conn_status = findViewById(R.id.image_connection_status);


        // Initialize the executor service
        executorService = Executors.newSingleThreadExecutor();

        // Set up button click listeners
//        Button btnRead = findViewById(R.id.btn_read);
//        Button btnAsk = findViewById(R.id.btn_ask);
//
//        btnRead.setOnClickListener(this::goToNewActivity);
//        btnAsk.setOnClickListener(this::goToNewActivity);

    }

    @Override
    protected void onStart() {
        super.onStart();

        check_connection();

    }


    private void check_connection()
    {
        ApiService_check_connection apiservice = ApiClient.getClient().create(ApiService_check_connection.class);
        Call<data_check_connection> call =  apiservice.getConnected_status();

        call.enqueue(new Callback<data_check_connection>() {
            @Override
            public void onResponse(Call<data_check_connection> call, Response<data_check_connection> response) {
                data_check_connection reply = response.body();

                if (reply.getConnected()==1)
                {
                    img_conn_status.setImageResource(R.drawable.success_2);
                    text_1.setTextColor(Color.GREEN);
                    text_1.setText("Connection to pc established !");

                }



            }

            @Override
            public void onFailure(Call<data_check_connection> call, Throwable throwable) {
                img_conn_status.setImageResource(R.drawable.failure_2);

                text_1.setTextColor(Color.RED);
                text_1.setText("Connection to pc is failed !! . This app will not function now. Possible because pc is not online or turned off ");

            }
        });


    }





    public void goToNewActivity(View view) {
        int id = view.getId();
        Class<?> targetActivity;

        if (id == R.id.btn_read) {
            targetActivity = view_Channel_buttons.class;
        } else if (id == R.id.btn_ask) {
            targetActivity = view_AskQuestionActivity_first.class;
        }

        else if (id == R.id.btn_view)
        { targetActivity = View_extracted_channels.class; }
        else if  (id == R.id.btn_extract){
            targetActivity = view_Extract_transcript_and_embeddings.class;
        }

        else
        {return ;}

        Intent intent = new Intent(this, targetActivity);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown(); // Shutdown the executor service when the activity is destroyed
    }
}

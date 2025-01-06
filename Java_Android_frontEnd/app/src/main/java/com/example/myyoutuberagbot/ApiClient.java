package com.example.myyoutuberagbot;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;


public class ApiClient {
    static InetAddress localHost;

//    static {
//        try {
//            localHost = InetAddress.getLocalHost();
//            System.out.println(localHost);
//        } catch (UnknownHostException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private static final String BASE_URL = "http://[2405:201:680c:a121:e877:1a52:e1c7:47aa]:5000";
    //private static final String BASE_URL = "http://" + localHost.getHostAddress() + ":5000";
    private static Retrofit retrofit = null;

    public ApiClient() throws UnknownHostException {
    }

    public static Retrofit getClient() {
        System.out.println(BASE_URL);
        if (retrofit == null) {
            // Create a custom OkHttpClient with timeout settings
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(5000, TimeUnit.SECONDS) // Set connection timeout
                    .readTimeout(5000, TimeUnit.SECONDS)    // Set read timeout
                    .writeTimeout(5000, TimeUnit.SECONDS)   // Set write timeout
                    .build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

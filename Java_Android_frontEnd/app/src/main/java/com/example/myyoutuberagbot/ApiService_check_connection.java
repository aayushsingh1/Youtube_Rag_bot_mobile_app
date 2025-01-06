package com.example.myyoutuberagbot;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService_check_connection {

  @GET("/status")
  Call<data_check_connection> getConnected_status();

}

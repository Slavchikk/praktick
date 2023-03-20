package com.example.praktick;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("user/login")
    Call<UserMask> createUser(@Body SentUser modelSendUser);
}

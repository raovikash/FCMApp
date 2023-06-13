package com.raovikash.fcmapp.clients;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface AppNotificationClient {
    @POST("v1/register")
    Call<Object> registerDevice(@Body Map<String, Object> registrationRequest,
                                @HeaderMap Map<String, String> headers);

    @POST("v1/deRegister")
    Call<Object> deRegisterDevice(@Body Map<String, Object> registrationRequest,
                                @HeaderMap Map<String, String> headers);
}

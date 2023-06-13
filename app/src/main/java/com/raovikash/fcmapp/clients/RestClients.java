package com.raovikash.fcmapp.clients;

import com.google.android.datatransport.runtime.dagger.Component;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class RestClients {
    String appNotificationBaseUrl = "{mention-base-url}";

    public AppNotificationClient getAppNotificationClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(appNotificationBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(AppNotificationClient.class);
    }
}

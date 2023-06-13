package com.raovikash.fcmapp.helpers;

import android.util.Log;

import com.raovikash.fcmapp.clients.RestClients;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class AppNotificationHelper {
    private static final String TAG = "AppNotificationHelper";
    public static final String FCM_APP_ID = "testapp-ddd34";
    public static final String USER_ID = "9014837930";
    public static final String APP_NAME = "CUSTOMER_APP";
    public static final String DEVICE_ID = "testDeviceId6";
    public static final String SOURCE = "POSTMAN";
    public static final List<String> TOPICS = List.of("CUSTOMISED_DETAILS_TOPIC_2");

    public void registerDevice(String fcmToken) {
        RestClients restClients = new RestClients();
        Map<String, Object> registrationRequest = buildRegistrationRequest(fcmToken);
        Log.i(TAG, String.format("Request sent for registration %s", registrationRequest));
        Call<Object> response = restClients.getAppNotificationClient().registerDevice(registrationRequest, getHeaders());
        try {
            Object responseBody = response.execute().body();
            Log.i(TAG, String.format("Response received on registration %s", responseBody));
        } catch (Exception e) {
            Log.e(TAG, "Exception occurred while registration", e);
        }
    }

    public void deRegisterDevice() {
        RestClients restClients = new RestClients();
        Map<String, Object> deRegistrationRequest = buildDeRegistrationRequest();
        Call<Object> response = restClients.getAppNotificationClient().deRegisterDevice(deRegistrationRequest, getHeaders());
        try {
            Object responseBody = response.execute().body();
            Log.i(TAG, String.format("Response received on deRegistration %s", responseBody));
        } catch (Exception e) {
            Log.e(TAG, "Exception occurred while registration", e);
        }
    }
    private Map<String, Object> buildDeRegistrationRequest() {
        Map<String, Object> deRegistrationRequest = new HashMap<>();
        deRegistrationRequest.put("appName", APP_NAME);
        deRegistrationRequest.put("deRegistrationType", "TOKEN");
        deRegistrationRequest.put("deviceId", DEVICE_ID);
        deRegistrationRequest.put("fcmAppId", FCM_APP_ID);
        deRegistrationRequest.put("source", SOURCE);
        deRegistrationRequest.put("topics", TOPICS);
        deRegistrationRequest.put("userId", USER_ID);
        return deRegistrationRequest;
    }

    private Map<String, Object> buildRegistrationRequest(String fcmToken) {
        Map<String, Object> registrationRequest = new HashMap<>();
        registrationRequest.put("fcmAppId", FCM_APP_ID);
        registrationRequest.put("userId", USER_ID);
        registrationRequest.put("expiryDate", LocalDateTime.now().plus(7, ChronoUnit.DAYS).toString());
        registrationRequest.put("fcmToken", fcmToken);
        registrationRequest.put("appName", APP_NAME);
        registrationRequest.put("deviceId", DEVICE_ID);
        registrationRequest.put("source", SOURCE);
        registrationRequest.put("topics", TOPICS);
        return registrationRequest;
    }

    private Map<String, String> getHeaders() {
        Map<String, String> authHeaders = new HashMap<>();
        return authHeaders;
    }
}

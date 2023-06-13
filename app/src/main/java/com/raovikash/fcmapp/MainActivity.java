package com.raovikash.fcmapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;
import com.raovikash.fcmapp.helpers.AppNotificationHelper;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allowNetworkAccessOnMain();
        final Button registerBtn = findViewById(R.id.register);
        registerBtn.setOnClickListener(v -> registerDevice());

        final Button deRegisterBtn = findViewById(R.id.deRegister);
        deRegisterBtn.setOnClickListener(v -> deRegisterDevice());
    }

    private void allowNetworkAccessOnMain() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void deRegisterDevice() {
        new AppNotificationHelper().deRegisterDevice();
        showToast("Device De-registered!");
    }

    public void showToast(String content) {
        Toast.makeText(MainActivity.this, content, Toast.LENGTH_SHORT).show();
    }

    public void registerDevice() {
        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                    return;
                }
                // Get new FCM registration token
                String token = task.getResult();
                new AppNotificationHelper().registerDevice(token);
                Log.i(TAG, "Device registered with token: " + token);
            });
    }
}
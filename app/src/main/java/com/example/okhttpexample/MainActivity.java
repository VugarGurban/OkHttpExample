package com.example.okhttpexample;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class MainActivity extends AppCompatActivity {

    OkHttpClient client;
    TextView textData;
    Button btnGet, btnPost;

    String geturl = "https://demo.v2.aptekonline.az/items?key=zm1an242";
    String posturl = "https://demo.v2.aptekonline.az/items";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new OkHttpClient();
        btnGet = findViewById(R.id.btnGet);
        btnPost = findViewById(R.id.btnPost);
        textData = findViewById(R.id.textData);

        btnGet.setOnClickListener(v -> {
            get();
        });

        btnPost.setOnClickListener(v -> {
            post();
        });

    }

    public void get(){
        Request request = new Request.Builder().url(geturl).build();
        client.newCall(request).enqueue(new Callback(){

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            textData.setText(response.body().string());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });
    }

    public void post(){
        String json = "{\n" +
                "  \"name\": \"Sulfat\",\n" +
                "  \"amount\": 45.95,\n" +
                "  \"category\": 1,\n" +
                "  \"note\": \"note update\",\n" +
                "  \"key\": \"zm1an242\",\n" +
                "  \"id\": 1\n" +
                "}";
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));

        Request request = new Request.Builder().url(posturl).post(requestBody).build();

        client.newCall(request).enqueue(new Callback(){

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            textData.setText(response.body().string());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });
    }

}
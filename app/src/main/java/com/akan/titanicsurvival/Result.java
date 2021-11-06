package com.akan.titanicsurvival;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;

public class Result extends AppCompatActivity {
    TextView Result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Result = findViewById(R.id.Result);

        Intent intent = getIntent();
        String str = intent.getStringExtra("Prediction");

        Result.setText(str);
    }
}
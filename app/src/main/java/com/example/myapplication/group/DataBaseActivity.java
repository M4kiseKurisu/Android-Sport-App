package com.example.myapplication.group;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class DataBaseActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_database;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        //tv_database = findViewById(R.id.tv);
        findViewById(R.id.btn_database_create).setOnClickListener(this);
        findViewById(R.id.btn_database_delete).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
    }
}

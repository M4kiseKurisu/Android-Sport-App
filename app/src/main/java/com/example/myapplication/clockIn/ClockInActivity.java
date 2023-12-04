package com.example.myapplication.clockIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class ClockInActivity extends AppCompatActivity {
    private ImageButton backButton;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_in);
        backButton = findViewById(R.id.clock_in_back);
        backButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        }));
//        recyclerView = findViewById(R.id.clock_in_list);
//        adapter = new ClockAdapter();
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
        Button toClockIn = findViewById(R.id.to_clock_in);
        toClockIn.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ClockInActivity.this, ClockInCreateActivity.class);
                startActivity(intent);
            }
        }));

    }
}
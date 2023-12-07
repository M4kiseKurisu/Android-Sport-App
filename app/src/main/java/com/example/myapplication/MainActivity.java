package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.group.GroupActivity;
import com.example.myapplication.tutorial.TutorialActivity;
import com.example.myapplication.clockIn.ClockInActivity;

public class MainActivity extends AppCompatActivity {
    private ImageButton button1;

    private ImageButton clockInButton;
    private ImageButton button2;
    private ImageButton button4;
    private DataBaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginInfor", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("UserID", -1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clockInButton = findViewById(R.id.clock_in_button);
        clockInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,ClockInActivity.class);
                startActivity(intent);
            }
        });
        TextView text = findViewById(R.id.userId);
        text.setText(String.valueOf(userId));

        dbHelper = new DataBaseHelper(this, "DataBase.db", null, 1);
        dbHelper.getWritableDatabase();

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, GroupActivity.class);
                startActivity(intent);
            }
        });

        button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, TutorialActivity.class);
                startActivity(intent);
            }
        });
    }

//    private class MyClickListener implements View.OnClickListener {
//
//
//        @Override
//        public void onClick(View v) {
//            if (v.equals(button1)) {
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, TutorialActivity.class);
//                startActivity(intent);
//            }
//        }
//    }
}
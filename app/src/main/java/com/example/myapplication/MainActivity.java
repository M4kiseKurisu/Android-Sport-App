package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.clockIn.ClockInActivity;
import com.example.myapplication.database.DataBaseHelper;
import com.example.myapplication.tutorial.TutorialActivity;

public class MainActivity extends AppCompatActivity {
    private ImageButton button1;

    private ImageButton clockInButton;
    private DataBaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button1);
        MyClickListener myClickListener = new MyClickListener();
        button1.setOnClickListener(myClickListener);
        clockInButton = findViewById(R.id.clock_in_button);
        clockInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ClockInActivity.class);
                startActivity(intent);
            }
        });
        button1.setOnClickListener(new MyClickListener());
        dbHelper = new DataBaseHelper(this, "DataBase.db", null, 1);
        dbHelper.getWritableDatabase();
        /*Button createDatabase = (Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//创建或打开现有的数据库
                dbHelper.getWritableDatabase();
            }
        });*/
    }

    private class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.equals(button1)) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, TutorialActivity.class);
                startActivity(intent);
            }
        }
    }
}
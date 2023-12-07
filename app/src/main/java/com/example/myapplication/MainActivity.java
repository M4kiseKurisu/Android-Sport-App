package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.tutorial.TutorialActivity;

public class MainActivity extends AppCompatActivity {
    private ImageButton button1;
    private ImageButton button4;
    private DataBaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginInfor", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("UserID", -1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button1);
        TextView text = findViewById(R.id.userId);
        text.setText(String.valueOf(userId));
        //button1.setOnClickListener(new MyClickListener());
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
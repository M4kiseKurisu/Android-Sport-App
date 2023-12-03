package com.example.myapplication.tutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.R;

public class TutorialWrite extends AppCompatActivity {
    private EditText title;
    private EditText content;
    private Spinner category;
    private Button commit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_write);

        title = findViewById(R.id.write_activity_title);
        content = findViewById(R.id.write_activity_content);
        category = findViewById(R.id.write_activity_category);
        commit = findViewById(R.id.write_activity_commit);

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "攻略标题不能为空~", Toast.LENGTH_SHORT).show();
                }

                else if (content.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "攻略内容不能为空~", Toast.LENGTH_SHORT).show();
                }

                else {
                    //成功发送，放入数据库


                    //发送信息，返回首页
                    Toast.makeText(getApplicationContext(), "成功发送攻略！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(TutorialWrite.this, TutorialActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
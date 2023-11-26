package com.example.myapplication.tutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.myapplication.R;

public class TutorialShow extends AppCompatActivity {

    private Button back;
    private Button commit;
    private LinearLayout comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_show);

        back = findViewById(R.id.back);
        commit = findViewById(R.id.commit);
        comment = findViewById(R.id.comment);

        LinearLayout.LayoutParams para;
        para = (LinearLayout.LayoutParams) comment.getLayoutParams();
        para.height = 0;
        comment.setLayoutParams(para);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TutorialShow.this, TutorialActivity.class);
                startActivity(intent);
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams para;
                para = (LinearLayout.LayoutParams) comment.getLayoutParams();
                para.height = 520;
                comment.setLayoutParams(para);
            }
        });
    }
}
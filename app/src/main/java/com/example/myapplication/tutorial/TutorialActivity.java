package com.example.myapplication.tutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;

public class TutorialActivity extends AppCompatActivity {

    private Button create;
    private ImageButton tutorial_1;
    private LinearLayout tutorial_text_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        boolean set = true;

        tutorial_text_1 = findViewById(R.id.tutorial_text_1);

        if (!set) {
            LinearLayout.LayoutParams para;
            para = (LinearLayout.LayoutParams) tutorial_text_1.getLayoutParams();
            para.height=0;
            tutorial_text_1.setLayoutParams(para);
        }


        create = findViewById(R.id.create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TutorialActivity.this, TutorialWrite.class);
                startActivity(intent);
            }
        });

        tutorial_1 = findViewById(R.id.tutorial_1);

        tutorial_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TutorialActivity.this, TutorialShow.class);
                startActivity(intent);
            }
        });
    }


}
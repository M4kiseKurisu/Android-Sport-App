package com.example.myapplication.tutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;

public class TutorialShow extends AppCompatActivity {

    private Button like;
    private Button back;
    private Button commit;
    private LinearLayout comment;
    private boolean commitIsOpen;
    private TextView writer;
    private TextView category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_show);

        like = findViewById(R.id.like);
        back = findViewById(R.id.back);
        commit = findViewById(R.id.commit);
        comment = findViewById(R.id.comment);
        commitIsOpen = false;
        writer = findViewById(R.id.tutorial_writer_name);
        category = findViewById(R.id.tutorial_category);

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
                if (!commitIsOpen) {
                    LinearLayout.LayoutParams para;
                    para = (LinearLayout.LayoutParams) comment.getLayoutParams();
                    para.height = 520;
                    comment.setLayoutParams(para);
                }
                else {
                    LinearLayout.LayoutParams para;
                    para = (LinearLayout.LayoutParams) comment.getLayoutParams();
                    para.height = 0;
                    comment.setLayoutParams(para);
                }
                commitIsOpen = !commitIsOpen;
            }
        });
    }
}
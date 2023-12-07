package com.example.myapplication.tutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.DataBaseHelper;
import com.example.myapplication.R;

public class TutorialWrite extends AppCompatActivity {
    private EditText title;
    private EditText content;
    private Spinner category;
    private Button commit;

    private String selectedCategory;

    private DataBaseHelper dbHelper = new DataBaseHelper(this, "DataBase.db", null, 1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_write);

        title = findViewById(R.id.write_activity_title);
        content = findViewById(R.id.write_activity_content);
        category = findViewById(R.id.write_activity_category);
        commit = findViewById(R.id.write_activity_commit);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                    //获取userId
                    SharedPreferences sharedPreferences = getSharedPreferences("LoginInfor", MODE_PRIVATE);
                    int userId = sharedPreferences.getInt("UserID", -1);


                    ContentValues values = new ContentValues();
                    values.put("favor", 0);
                    values.put("planContent", content.getText().toString());
                    values.put("planTitle", title.getText().toString());
                    values.put("planType", selectedCategory);
                    values.put("userId", userId);  //todo

                    SQLiteDatabase database = dbHelper.getWritableDatabase();
                    long id = database.insert("TravelPlan", null, values);

                    Log.d("id:", String.valueOf(id));
                    Log.d("id:", "text");
                    database.close();
                    finish();


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
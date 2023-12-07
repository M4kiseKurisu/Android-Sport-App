package com.example.myapplication.clockIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.DataBaseHelper;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.io.File;
import java.util.ArrayList;

public class ClockInActivity extends AppCompatActivity {

    private ImageButton backButton;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private Button toClockIn;
    private DataBaseHelper dbHelper = new DataBaseHelper(this, "DataBase.db", null, 1);
    private LinearLayout clockInList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_in);
        backButton = findViewById(R.id.clock_in_back);
        clockInList = findViewById(R.id.clock_in_list);
        backButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }));
//        recyclerView = findViewById(R.id.clock_in_list);
//        adapter = new ClockAdapter();
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
        toClockIn = findViewById(R.id.to_clock_in);
        toClockIn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ClockInActivity.this, ClockInCreateActivity.class);
                startActivity(intent);
                clockInList.removeAllViews();
            }
        }));

    }

    protected void onResume() {
        super.onResume();
        rendering();
    }

    public void rendering() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ClockIn", null);
        ArrayList<LinearLayout> linearLayouts = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int userId = cursor.getInt(cursor.getColumnIndex("userId"));
            String sportItem = cursor.getString(cursor.getColumnIndex("sportItem"));
            int love = cursor.getInt(cursor.getColumnIndex("love"));
            String sportAdd = cursor.getString(cursor.getColumnIndex("sportAdd"));
            String startTime = cursor.getString(cursor.getColumnIndex("startTime"));
            String endTime = cursor.getString(cursor.getColumnIndex("endTime"));
            String imagePath = cursor.getString(cursor.getColumnIndex("imgPath"));
            // 在这里使用提取的数据进行操作
            Log.d("rendering", sportItem);
            Log.d("rendering", sportAdd);

            // 创建根布局 LinearLayout
            LinearLayout rootLayout = new LinearLayout(this);
            LinearLayout.LayoutParams rootLayoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    660
            );
            rootLayoutParams.setMargins(20, 20, 20, 0);
            rootLayout.setLayoutParams(rootLayoutParams);
            rootLayout.setOrientation(LinearLayout.VERTICAL);
            //rootLayout.setGravity(Gravity.CENTER_VERTICAL);
            rootLayout.setBackgroundResource(R.drawable.clock_item_border);
            rootLayout.setPadding(
                    10,
                    5,
                    10,
                    0
            );

            // 创建包裹用户名的 LinearLayout
            LinearLayout usernameLayout = new LinearLayout(this);
            LinearLayout.LayoutParams usernameLayoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    100
            );
            usernameLayout.setLayoutParams(usernameLayoutParams);
            usernameLayout.setOrientation(LinearLayout.HORIZONTAL);

            // 创建用户名 TextView
            TextView usernameTextView = new TextView(this);
            LinearLayout.LayoutParams usernameTextViewParams = new LinearLayout.LayoutParams(
                    850,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            usernameTextView.setLayoutParams(usernameTextViewParams);
            String user = userId + "-" + sportItem + "-" + sportAdd;
            usernameTextView.setText(user);
            usernameTextView.setTextSize(18);
            usernameTextView.setTypeface(Typeface.DEFAULT_BOLD);
            usernameTextView.setTextColor(getResources().getColor(R.color.group_black));
            usernameTextView.setPadding(
                    15,
                    15,
                    0,
                    0
            );

            //创建点赞数
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    60,
                    120
            );
            layoutParams.setMargins(
                    0,
                    0,
                    0,
                    0
            );

            ImageButton imageButton = new ImageButton(this);
            imageButton.setLayoutParams(layoutParams);
            imageButton.setScaleType(ImageButton.ScaleType.FIT_XY);
            imageButton.setImageResource(R.drawable.dianzan_aigei_com);
            imageButton.setBackgroundResource(R.drawable.white_background);


            //创建点赞数
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams1.setMargins(
                    10,
                    10,
                    0,
                    0
            );

            TextView textView = new TextView((Context) this);
            textView.setLayoutParams(layoutParams1);
            textView.setTextSize(20);
            textView.setText(String.valueOf(love));
            textView.setPadding(10, 0, 0, 0);
            imageButton.setOnClickListener((new View.OnClickListener() {
                private boolean isLiked = false;

                @Override
                public void onClick(View v) {
                    if (isLiked) {
                        return;
                    }
                    imageButton.setImageResource(R.drawable.dianzanhou_aigei_com);
                    int num = Integer.parseInt(textView.getText().toString());
                    textView.setText(String.valueOf(num + 1));
                    ContentValues values = new ContentValues();
                    values.put("love", num + 1);
                    String selection = "id = ?";
                    String[] selectionArgs = {String.valueOf(id)};
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.update("ClockIn", values, selection, selectionArgs);
                    isLiked = true;
                }
            }));

            usernameLayout.addView(usernameTextView);
            usernameLayout.addView(imageButton);
            usernameLayout.addView(textView);
            rootLayout.addView(usernameLayout);

            // 创建 ImageView
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    480
            );
            imageView.setLayoutParams(imageViewParams);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            File imgFile = new File(imagePath);
            if (imgFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(bitmap);
            }
            //imageView.setImageResource(R.drawable.image1);
            imageView.setPadding(
                    10,
                    0,
                    10,
                    0
            );

            rootLayout.addView(imageView);

            // 创建开始时间-结束时间 TextView
            TextView timeTextView = new TextView(this);
            LinearLayout.LayoutParams timeTextViewParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            timeTextView.setLayoutParams(timeTextViewParams);
            String time = startTime + " - " + endTime;
            timeTextView.setText(time);
            timeTextView.setPadding(
                    12,
                    5,
                    0,
                    0
            );

            rootLayout.addView(timeTextView);
            linearLayouts.add(rootLayout);
        }
        for (int i = linearLayouts.size() - 1; i >= 0; i--) {
            clockInList.addView(linearLayouts.get(i));
        }
        cursor.close();
        db.close();
    }

}
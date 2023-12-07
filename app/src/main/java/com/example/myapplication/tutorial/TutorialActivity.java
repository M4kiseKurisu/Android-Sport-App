package com.example.myapplication.tutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.DataBaseHelper;
import com.example.myapplication.R;

import java.io.File;

public class TutorialActivity extends AppCompatActivity {

    private Button create;
    private int page = 1;
    //private int rows;

    private LinearLayout tutorial_text_1;
    private ImageButton tutorial_1;
    private TextView tutorial_1_title;
    private TextView tutorial_1_category;

    private LinearLayout tutorial_text_2;
    private ImageButton tutorial_2;
    private TextView tutorial_2_title;
    private TextView tutorial_2_category;

    private LinearLayout tutorial_text_3;
    private ImageButton tutorial_3;
    private TextView tutorial_3_title;
    private TextView tutorial_3_category;

    private LinearLayout tutorial_text_4;
    private ImageButton tutorial_4;
    private TextView tutorial_4_title;
    private TextView tutorial_4_category;

    private LinearLayout tutorial_text_5;
    private ImageButton tutorial_5;
    private TextView tutorial_5_title;
    private TextView tutorial_5_category;

    private LinearLayout tutorial_text_6;
    private ImageButton tutorial_6;
    private TextView tutorial_6_title;
    private TextView tutorial_6_category;

    private LinearLayout tutorial_text_7;
    private ImageButton tutorial_7;
    private TextView tutorial_7_title;
    private TextView tutorial_7_category;

    private LinearLayout tutorial_text_8;
    private ImageButton tutorial_8;
    private TextView tutorial_8_title;
    private TextView tutorial_8_category;

    private LinearLayout tutorial_text_9;
    private ImageButton tutorial_9;
    private TextView tutorial_9_title;
    private TextView tutorial_9_category;

    private LinearLayout tutorial_text_10;
    private ImageButton tutorial_10;
    private TextView tutorial_10_title;
    private TextView tutorial_10_category;

    private Button firstPage;
    private Button beforePage;
    private Button afterPage;
    private Button lastPage;
    private TextView pageShow;

    private DataBaseHelper dbHelper = new DataBaseHelper(this, "DataBase.db", null, 1);

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        boolean set = true;

        tutorial_text_1 = findViewById(R.id.tutorial_text_1);
        tutorial_1 = findViewById(R.id.tutorial_1);
        tutorial_1_title = findViewById(R.id.tutorial_1_title);
        tutorial_1_category = findViewById(R.id.tutorial_1_category);

        tutorial_text_2 = findViewById(R.id.tutorial_text_2);
        tutorial_2 = findViewById(R.id.tutorial_2);
        tutorial_2_title = findViewById(R.id.tutorial_2_title);
        tutorial_2_category = findViewById(R.id.tutorial_2_category);

        tutorial_text_3 = findViewById(R.id.tutorial_text_3);
        tutorial_3 = findViewById(R.id.tutorial_3);
        tutorial_3_title = findViewById(R.id.tutorial_3_title);
        tutorial_3_category = findViewById(R.id.tutorial_3_category);

        tutorial_text_4 = findViewById(R.id.tutorial_text_4);
        tutorial_4 = findViewById(R.id.tutorial_4);
        tutorial_4_title = findViewById(R.id.tutorial_4_title);
        tutorial_4_category = findViewById(R.id.tutorial_4_category);

        tutorial_text_5 = findViewById(R.id.tutorial_text_5);
        tutorial_5 = findViewById(R.id.tutorial_5);
        tutorial_5_title = findViewById(R.id.tutorial_5_title);
        tutorial_5_category = findViewById(R.id.tutorial_5_category);

        tutorial_text_6 = findViewById(R.id.tutorial_text_6);
        tutorial_6 = findViewById(R.id.tutorial_6);
        tutorial_6_title = findViewById(R.id.tutorial_6_title);
        tutorial_6_category = findViewById(R.id.tutorial_6_category);

        tutorial_text_7 = findViewById(R.id.tutorial_text_7);
        tutorial_7 = findViewById(R.id.tutorial_7);
        tutorial_7_title = findViewById(R.id.tutorial_7_title);
        tutorial_7_category = findViewById(R.id.tutorial_7_category);

        tutorial_text_8 = findViewById(R.id.tutorial_text_8);
        tutorial_8 = findViewById(R.id.tutorial_8);
        tutorial_8_title = findViewById(R.id.tutorial_8_title);
        tutorial_8_category = findViewById(R.id.tutorial_8_category);

        tutorial_text_9 = findViewById(R.id.tutorial_text_9);
        tutorial_9 = findViewById(R.id.tutorial_9);
        tutorial_9_title = findViewById(R.id.tutorial_9_title);
        tutorial_9_category = findViewById(R.id.tutorial_9_category);

        tutorial_text_10 = findViewById(R.id.tutorial_text_10);
        tutorial_10 = findViewById(R.id.tutorial_10);
        tutorial_10_title = findViewById(R.id.tutorial_10_title);
        tutorial_10_category = findViewById(R.id.tutorial_10_category);

        firstPage = findViewById(R.id.tutorial_main_firstPage);
        beforePage = findViewById(R.id.tutorial_main_beforePage);
        afterPage = findViewById(R.id.tutorial_main_afterPage);
        lastPage = findViewById(R.id.tutorial_main_lastPage);

        pageShow = findViewById(R.id.activity_show_page);

//        if (!set) {
//            LinearLayout.LayoutParams para;
//            para = (LinearLayout.LayoutParams) tutorial_text_1.getLayoutParams();
//            para.height=0;
//            tutorial_text_1.setLayoutParams(para);
//        }


        create = findViewById(R.id.create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TutorialActivity.this, TutorialWrite.class);
                startActivity(intent);
            }
        });

        page = 1;
        render(1);

        String pageText = "当前页数：" + page;
        pageShow.setText(pageText);

        firstPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                render(page);
                String pageText = "当前页数：" + page;
                pageShow.setText(pageText);
            }
        });

        beforePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page > 1) {
                    page--;
                }
                render(page);
                String pageText = "当前页数：" + page;
                pageShow.setText(pageText);
            }
        });

        afterPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page <= getRowCount() / 10 + 1) {
                    page++;
                }
                render(page);
                String pageText = "当前页数：" + page;
                pageShow.setText(pageText);
            }
        });

        lastPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = getRowCount() / 10 + 1;
                render(page);
                String pageText = "当前页数：" + page;
                pageShow.setText(pageText);
            }
        });
    }

    public int getRowCount() {
        int count = 0;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            // 查询数据库表格，根据需要修改表格名称
            cursor = db.rawQuery("SELECT COUNT(*) FROM TravelPlan", null);

            if (cursor != null && cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        //rows = count;
        return count;
    }

    @SuppressLint("Range")
    private void render(int page) {
        tutorial_text_1.setVisibility(View.GONE);
        tutorial_text_2.setVisibility(View.GONE);
        tutorial_text_3.setVisibility(View.GONE);
        tutorial_text_4.setVisibility(View.GONE);
        tutorial_text_5.setVisibility(View.GONE);
        tutorial_text_6.setVisibility(View.GONE);
        tutorial_text_7.setVisibility(View.GONE);
        tutorial_text_8.setVisibility(View.GONE);
        tutorial_text_9.setVisibility(View.GONE);
        tutorial_text_10.setVisibility(View.GONE);


        //查询TravelPlan表进行渲染
        int rows = getRowCount();

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.query("TravelPlan", null, null, null, null, null, null, null);

        if (!cursor.moveToNext()) {
            cursor.close();
            database.close();
            return;
        }

        int showIndex = 1;

        boolean flag;

        if (page < rows / 10 + 1) {
            cursor.moveToPosition(rows - page * 10 + 1);
            flag = false;
        } else {
            cursor.moveToPosition(0);
            flag = true;  //最后一页
        }
        //Log.d("cursor:", String.valueOf(cursor));

        //cursor.moveToPosition((page - 1) * 10);
        int count = 0;

        do {
            //要获得id，进入TutorialShow渲染
            int Uid = 0;
            if (showIndex == 10) {
                tutorial_text_1.setVisibility(View.VISIBLE);
                tutorial_1_title.setText(cursor.getString(cursor.getColumnIndex("planTitle")));

                String category = "by ";
                Uid = cursor.getInt(cursor.getColumnIndex("userId"));
                Cursor cursor2 = database.query("User", null, null, null, null, null, null, null);
                while (cursor2.moveToNext()) {
                    int rowUid = cursor2.getInt(cursor2.getColumnIndex("id"));
                    if (Uid == rowUid) {
                        category += cursor2.getString(cursor2.getColumnIndex("name"));
                    }
                }
                cursor2.close();
                category += ("   " + cursor.getString(cursor.getColumnIndex("planType")));

                String imgPath = cursor.getString(cursor.getColumnIndex("img"));
                refreshPhoto(imgPath, tutorial_1);

                tutorial_1_category.setText(category);
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                tutorial_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(TutorialActivity.this, TutorialShow.class);
                        intent.putExtra("contentId", id);
                        startActivity(intent);
                    }
                });
            }

            if (showIndex == 9) {
                tutorial_text_2.setVisibility(View.VISIBLE);
                tutorial_2_title.setText(cursor.getString(cursor.getColumnIndex("planTitle")));

                String category = "by ";
                Uid = cursor.getInt(cursor.getColumnIndex("userId"));
                Cursor cursor2 = database.query("User", null, null, null, null, null, null, null);
                while (cursor2.moveToNext()) {
                    int rowUid = cursor2.getInt(cursor2.getColumnIndex("id"));
                    if (Uid == rowUid) {
                        category += cursor2.getString(cursor2.getColumnIndex("name"));
                    }
                }
                cursor2.close();
                category += ("   " + cursor.getString(cursor.getColumnIndex("planType")));

                String imgPath = cursor.getString(cursor.getColumnIndex("img"));
                refreshPhoto(imgPath, tutorial_2);

                tutorial_2_category.setText(category);
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                tutorial_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(TutorialActivity.this, TutorialShow.class);
                        intent.putExtra("contentId", id);
                        startActivity(intent);
                    }
                });
            }

            if (showIndex == 8) {
                tutorial_text_3.setVisibility(View.VISIBLE);
                tutorial_3_title.setText(cursor.getString(cursor.getColumnIndex("planTitle")));

                String category = "by ";
                Uid = cursor.getInt(cursor.getColumnIndex("userId"));
                Cursor cursor2 = database.query("User", null, null, null, null, null, null, null);
                while (cursor2.moveToNext()) {
                    int rowUid = cursor2.getInt(cursor2.getColumnIndex("id"));
                    if (Uid == rowUid) {
                        category += cursor2.getString(cursor2.getColumnIndex("name"));
                    }
                }
                cursor2.close();
                category += ("   " + cursor.getString(cursor.getColumnIndex("planType")));

                String imgPath = cursor.getString(cursor.getColumnIndex("img"));
                refreshPhoto(imgPath, tutorial_3);

                tutorial_3_category.setText(category);
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                tutorial_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(TutorialActivity.this, TutorialShow.class);
                        intent.putExtra("contentId", id);
                        startActivity(intent);
                    }
                });
            }

            if (showIndex == 7) {
                tutorial_text_4.setVisibility(View.VISIBLE);
                tutorial_4_title.setText(cursor.getString(cursor.getColumnIndex("planTitle")));

                String category = "by ";
                Uid = cursor.getInt(cursor.getColumnIndex("userId"));
                Cursor cursor2 = database.query("User", null, null, null, null, null, null, null);
                while (cursor2.moveToNext()) {
                    int rowUid = cursor2.getInt(cursor2.getColumnIndex("id"));
                    if (Uid == rowUid) {
                        category += cursor2.getString(cursor2.getColumnIndex("name"));
                    }
                }
                cursor2.close();
                category += ("   " + cursor.getString(cursor.getColumnIndex("planType")));

                String imgPath = cursor.getString(cursor.getColumnIndex("img"));
                refreshPhoto(imgPath, tutorial_4);

                tutorial_4_category.setText(category);
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                tutorial_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(TutorialActivity.this, TutorialShow.class);
                        intent.putExtra("contentId", id);
                        startActivity(intent);
                    }
                });
            }

            if (showIndex == 6) {
                tutorial_text_5.setVisibility(View.VISIBLE);
                tutorial_5_title.setText(cursor.getString(cursor.getColumnIndex("planTitle")));

                String category = "by ";
                Uid = cursor.getInt(cursor.getColumnIndex("userId"));
                Cursor cursor2 = database.query("User", null, null, null, null, null, null, null);
                while (cursor2.moveToNext()) {
                    int rowUid = cursor2.getInt(cursor2.getColumnIndex("id"));
                    if (Uid == rowUid) {
                        category += cursor2.getString(cursor2.getColumnIndex("name"));
                    }
                }
                cursor2.close();
                category += ("   " + cursor.getString(cursor.getColumnIndex("planType")));

                String imgPath = cursor.getString(cursor.getColumnIndex("img"));
                refreshPhoto(imgPath, tutorial_5);

                tutorial_5_category.setText(category);
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                tutorial_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(TutorialActivity.this, TutorialShow.class);
                        intent.putExtra("contentId", id);
                        startActivity(intent);
                    }
                });
            }

            if (showIndex == 5) {
                tutorial_text_6.setVisibility(View.VISIBLE);
                tutorial_6_title.setText(cursor.getString(cursor.getColumnIndex("planTitle")));

                String category = "by ";
                Uid = cursor.getInt(cursor.getColumnIndex("userId"));
                Cursor cursor2 = database.query("User", null, null, null, null, null, null, null);
                while (cursor2.moveToNext()) {
                    int rowUid = cursor2.getInt(cursor2.getColumnIndex("id"));
                    if (Uid == rowUid) {
                        category += cursor2.getString(cursor2.getColumnIndex("name"));
                    }
                }
                cursor2.close();
                category += ("   " + cursor.getString(cursor.getColumnIndex("planType")));

                String imgPath = cursor.getString(cursor.getColumnIndex("img"));
                refreshPhoto(imgPath, tutorial_6);

                tutorial_6_category.setText(category);
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                tutorial_6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(TutorialActivity.this, TutorialShow.class);
                        intent.putExtra("contentId", id);
                        startActivity(intent);
                    }
                });
            }

            if (showIndex == 4) {
                tutorial_text_7.setVisibility(View.VISIBLE);
                tutorial_7_title.setText(cursor.getString(cursor.getColumnIndex("planTitle")));

                String category = "by ";
                Uid = cursor.getInt(cursor.getColumnIndex("userId"));
                Cursor cursor2 = database.query("User", null, null, null, null, null, null, null);
                while (cursor2.moveToNext()) {
                    int rowUid = cursor2.getInt(cursor2.getColumnIndex("id"));
                    if (Uid == rowUid) {
                        category += cursor2.getString(cursor2.getColumnIndex("name"));
                    }
                }
                cursor2.close();
                category += ("   " + cursor.getString(cursor.getColumnIndex("planType")));

                String imgPath = cursor.getString(cursor.getColumnIndex("img"));
                refreshPhoto(imgPath, tutorial_7);

                tutorial_7_category.setText(category);
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                tutorial_7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(TutorialActivity.this, TutorialShow.class);
                        intent.putExtra("contentId", id);
                        startActivity(intent);
                    }
                });
            }

            if (showIndex == 3) {
                tutorial_text_8.setVisibility(View.VISIBLE);
                tutorial_8_title.setText(cursor.getString(cursor.getColumnIndex("planTitle")));

                String category = "by ";
                Uid = cursor.getInt(cursor.getColumnIndex("userId"));
                Cursor cursor2 = database.query("User", null, null, null, null, null, null, null);
                while (cursor2.moveToNext()) {
                    int rowUid = cursor2.getInt(cursor2.getColumnIndex("id"));
                    if (Uid == rowUid) {
                        category += cursor2.getString(cursor2.getColumnIndex("name"));
                    }
                }
                cursor2.close();
                category += ("   " + cursor.getString(cursor.getColumnIndex("planType")));

                String imgPath = cursor.getString(cursor.getColumnIndex("img"));
                refreshPhoto(imgPath, tutorial_8);

                tutorial_8_category.setText(category);
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                tutorial_8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(TutorialActivity.this, TutorialShow.class);
                        intent.putExtra("contentId", id);
                        startActivity(intent);
                    }
                });
            }

            if (showIndex == 2) {
                tutorial_text_9.setVisibility(View.VISIBLE);
                tutorial_9_title.setText(cursor.getString(cursor.getColumnIndex("planTitle")));

                String category = "by ";
                Uid = cursor.getInt(cursor.getColumnIndex("userId"));
                Cursor cursor2 = database.query("User", null, null, null, null, null, null, null);
                while (cursor2.moveToNext()) {
                    int rowUid = cursor2.getInt(cursor2.getColumnIndex("id"));
                    if (Uid == rowUid) {
                        category += cursor2.getString(cursor2.getColumnIndex("name"));
                    }
                }
                cursor2.close();
                category += ("   " + cursor.getString(cursor.getColumnIndex("planType")));

                String imgPath = cursor.getString(cursor.getColumnIndex("img"));
                refreshPhoto(imgPath, tutorial_9);

                tutorial_9_category.setText(category);
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                tutorial_9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(TutorialActivity.this, TutorialShow.class);
                        intent.putExtra("contentId", id);
                        startActivity(intent);
                    }
                });
            }

            if (showIndex == 1) {
                tutorial_text_10.setVisibility(View.VISIBLE);
                tutorial_10_title.setText(cursor.getString(cursor.getColumnIndex("planTitle")));

                String category = "by ";
                Uid = cursor.getInt(cursor.getColumnIndex("userId"));
                Cursor cursor2 = database.query("User", null, null, null, null, null, null, null);
                while (cursor2.moveToNext()) {
                    int rowUid = cursor2.getInt(cursor2.getColumnIndex("id"));
                    if (Uid == rowUid) {
                        category += cursor2.getString(cursor2.getColumnIndex("name"));
                    }
                }
                cursor2.close();
                category += ("   " + cursor.getString(cursor.getColumnIndex("planType")));

                String imgPath = cursor.getString(cursor.getColumnIndex("img"));
                refreshPhoto(imgPath, tutorial_10);

                tutorial_10_category.setText(category);
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                tutorial_10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(TutorialActivity.this, TutorialShow.class);
                        intent.putExtra("contentId", id);
                        startActivity(intent);
                    }
                });
            }

            showIndex++;
            count++;
        } while(cursor.moveToNext() && ((!flag && count <= 10) || (flag && count <= rows % 10)));

        cursor.close();
        database.close();
    }

    private void refreshPhoto(String imgPath, ImageButton tutorial_image) {
        if (imgPath != null) {
            File imgFile = new File(imgPath);
            if (imgFile.exists()) {
                tutorial_image.setVisibility(View.VISIBLE);
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                tutorial_image.setImageBitmap(bitmap);
            } else {
                tutorial_image.setImageResource(R.drawable.image4);
            }
        }
    }
}
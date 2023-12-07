package com.example.myapplication.tutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DataBaseHelper;
import com.example.myapplication.R;

import java.io.File;

public class TutorialShow extends AppCompatActivity {

    private Button like;
    private Button back;
    private Button commit;
    private LinearLayout comment;
    private boolean commitIsOpen;
    private TextView writer;
    private TextView category;

    private LinearLayout comment1;
    private TextView comment1_id;
    private TextView comment1_Likes;
    private Button comment1_favor;
    private TextView comment1_content;
    private int favor_1;
    private int cid_1;

    private LinearLayout comment2;
    private TextView comment2_id;
    private TextView comment2_Likes;
    private Button comment2_favor;
    private TextView comment2_content;
    private int favor_2;
    private int cid_2;

    private LinearLayout comment3;
    private TextView comment3_id;
    private TextView comment3_Likes;
    private Button comment3_favor;
    private TextView comment3_content;
    private int favor_3;
    private int cid_3;

    private LinearLayout comment4;
    private TextView comment4_id;
    private TextView comment4_Likes;
    private Button comment4_favor;
    private TextView comment4_content;
    private int favor_4;
    private int cid_4;

    private LinearLayout comment5;
    private TextView comment5_id;
    private TextView comment5_Likes;
    private Button comment5_favor;
    private TextView comment5_content;
    private int favor_5;
    private int cid_5;


    private LinearLayout comment6;
    private TextView comment6_id;
    private TextView comment6_Likes;
    private Button comment6_favor;
    private TextView comment6_content;
    private int favor_6;
    private int cid_6;

    private Button firstPage;
    private Button beforePage;
    private Button afterPage;
    private Button lastPage;

    private TextView tutorial_category;
    private TextView tutorial_writer_name;
    private TextView tutorial_favors;
    private TextView tutorial_content;
    private Button send_comment;
    private EditText comment_content;
    private TextView commentPage;

    private DataBaseHelper dbHelper = new DataBaseHelper(this, "DataBase.db", null, 1);
    private int tutorialId;
    private int favorAmount;

    private TextView tutorial_title;
    private ImageView tutorial_image;

    private int page = 1;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_show);

        like = findViewById(R.id.like);
        back = findViewById(R.id.back);
        commit = findViewById(R.id.commit);
        comment = findViewById(R.id.comment);
        commitIsOpen = false;

        comment1 = findViewById(R.id.comment_1);
        comment1_id = findViewById(R.id.comment_1_id);
//        comment1_Likes = findViewById(R.id.comment_1_Likes);
//        comment1_favor = findViewById(R.id.comment_1_favor);
        comment1_content = findViewById(R.id.comment_1_content);

        comment2 = findViewById(R.id.comment_2);
        comment2_id = findViewById(R.id.comment_2_id);
//        comment2_Likes = findViewById(R.id.comment_2_Likes);
//        comment2_favor = findViewById(R.id.comment_2_favor);
        comment2_content = findViewById(R.id.comment_2_content);

        comment3 = findViewById(R.id.comment_3);
        comment3_id = findViewById(R.id.comment_3_id);
//        comment3_Likes = findViewById(R.id.comment_3_Likes);
//        comment3_favor = findViewById(R.id.comment_3_favor);
        comment3_content = findViewById(R.id.comment_3_content);

        comment4 = findViewById(R.id.comment_4);
        comment4_id = findViewById(R.id.comment_4_id);
//        comment4_Likes = findViewById(R.id.comment_4_Likes);
//        comment4_favor = findViewById(R.id.comment_4_favor);
        comment4_content = findViewById(R.id.comment_4_content);

        comment5 = findViewById(R.id.comment_5);
        comment5_id = findViewById(R.id.comment_5_id);
//        comment5_Likes = findViewById(R.id.comment_5_Likes);
//        comment5_favor = findViewById(R.id.comment_5_favor);
        comment5_content = findViewById(R.id.comment_5_content);

        comment6 = findViewById(R.id.comment_6);
        comment6_id = findViewById(R.id.comment_6_id);
//        comment6_Likes = findViewById(R.id.comment_6_Likes);
//        comment6_favor = findViewById(R.id.comment_6_favor);
        comment6_content = findViewById(R.id.comment_6_content);

        firstPage = findViewById(R.id.content_firstpage);
        beforePage = findViewById(R.id.content_beforepage);
        afterPage = findViewById(R.id.content_afterpage);
        lastPage = findViewById(R.id.content_lastpage);

        tutorial_category = findViewById(R.id.tutorial_category);
        tutorial_writer_name = findViewById(R.id.tutorial_writer_name);
        tutorial_favors = findViewById(R.id.tutorial_favors);
        tutorial_content = findViewById(R.id.tutorial_content_all);

        send_comment = findViewById(R.id.send_comment);
        comment_content = findViewById(R.id.comment_content);

        commentPage = findViewById(R.id.activity_comment_page);
        tutorial_title = findViewById(R.id.tutorial_title);
        tutorial_image = findViewById(R.id.tutorial_image);



        LinearLayout.LayoutParams para;
        para = (LinearLayout.LayoutParams) comment.getLayoutParams();
        para.height = 0;
        comment.setLayoutParams(para);

        //接受这条tutorial的id
        Intent intent = getIntent();
        if (intent != null) {
            int id = intent.getIntExtra("contentId", 1);
            tutorialId = id;
            //Log.d("id:", String.valueOf(id));

            SQLiteDatabase database = dbHelper.getReadableDatabase();

            Cursor cursor = database.query("TravelPlan", null, null, null, null, null, null, null);

            while (cursor.moveToNext()) {
                // 获取 "your_column" 列的值
                @SuppressLint("Range") int rowId = cursor.getInt(cursor.getColumnIndex("id"));
                // 将值添加到数组中
                if (rowId == id) {
                    //找到正确行，进行渲染
                    //找到用户名
                    int uid = cursor.getInt(cursor.getColumnIndex("userId"));
                    Cursor cursor2 = database.query("User", null, null, null, null, null, null, null);
                    while (cursor2.moveToNext()) {
                        int rowUid = cursor2.getInt(cursor2.getColumnIndex("id"));
                        if (uid == rowUid) {
                            tutorial_writer_name.setText(cursor2.getString(cursor2.getColumnIndex("name")));
                        }
                    }
                    cursor2.close();

                    tutorial_category.setText(cursor.getString(cursor.getColumnIndex("planType")));
                    favorAmount = cursor.getInt(cursor.getColumnIndex("favor"));
                    tutorial_title.setText(cursor.getString(cursor.getColumnIndex("planTitle")));
                    tutorial_favors.setText(String.valueOf(favorAmount));

                    String imgPath = cursor.getString(cursor.getColumnIndex("img"));
                    refreshPhoto(imgPath);

                    String content = cursor.getString(cursor.getColumnIndex("planContent"));
                    tutorial_content.setText(wipe(content));
                }
            }
            cursor.close();
            database.close();
        }

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("favor", ++favorAmount);

                String selection = "id = ?";
                String[] selectionArgs = {String.valueOf(tutorialId)};

                database.update("TravelPlan", values, selection, selectionArgs);
                tutorial_favors.setText(String.valueOf(favorAmount));

                Toast.makeText(getApplicationContext(), "点赞成功！", Toast.LENGTH_SHORT).show();

                database.close();
            }
        });

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
                } else {
                    LinearLayout.LayoutParams para;
                    para = (LinearLayout.LayoutParams) comment.getLayoutParams();
                    para.height = 0;
                    comment.setLayoutParams(para);
                }
                commitIsOpen = !commitIsOpen;
            }
        });

        send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comment_content.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "评论内容不能为空~", Toast.LENGTH_SHORT).show();
                }
                else {
                    //成功发送，放入数据库
                    SharedPreferences sharedPreferences = getSharedPreferences("LoginInfor", MODE_PRIVATE);
                    int userId = sharedPreferences.getInt("UserID", -1);

                    ContentValues values = new ContentValues();
                    values.put("commentContent", comment_content.getText().toString());
                    values.put("favor", 0);
                    values.put("planId", tutorialId);
                    values.put("userId", userId);  //todo

                    SQLiteDatabase database = dbHelper.getWritableDatabase();
                    long id = database.insert("Comment", null, values);

                    Log.d("id:", String.valueOf(id));
                    Log.d("id:", "text");
                    database.close();

                    Toast.makeText(getApplicationContext(), "评论成功发送！", Toast.LENGTH_SHORT).show();
                    render(page);
                    comment_content.setText("");
                }
            }
        });

        page = 1;
        render(1);

        String pageText = "当前页数：" + page;
        commentPage.setText(pageText);

        firstPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                render(page);
                String pageText = "当前页数：" + page;
                commentPage.setText(pageText);
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
                commentPage.setText(pageText);
            }
        });

        afterPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page <= getRowCount() / 6 + 1) {
                    page++;
                }
                render(page);
                String pageText = "当前页数：" + page;
                commentPage.setText(pageText);
            }
        });

        lastPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = getRowCount() / 6 + 1;
                render(page);
                String pageText = "当前页数：" + page;
                commentPage.setText(pageText);
            }
        });

    }

    public int getRowCount() {
//        int count = 0;
//
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = null;
//
////        try {
////            // 查询数据库表格，根据需要修改表格名称
////            cursor = db.rawQuery("SELECT COUNT(*) FROM Comment", null);
////
////            if (cursor != null && cursor.moveToFirst()) {
////                count = cursor.getInt(0);
////            }
////        } finally {
////            if (cursor != null) {
////                cursor.close();
////            }
////            db.close();
////        }
//
//        return count;
        String tableName = "Comment";
        String columnName = "planId";
        int targetId = tutorialId; // 这里的 123 是你给出的 id

// 执行查询
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + columnName + " = ?";
        String[] selectionArgs = {String.valueOf(targetId)};
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, selectionArgs);

// 获取结果
        int rowCount = 0;
        if (cursor.moveToFirst()) {
            rowCount = cursor.getInt(0);
        }

// 关闭游标和数据库连接
        cursor.close();
        db.close();

// 输出行数
        //System.out.println("行数：" + rowCount);
        return rowCount;
    }

    @SuppressLint({"Range", "SetTextI18n"})
    private void render(int page) {
        comment1.setVisibility(View.GONE);
        comment2.setVisibility(View.GONE);
        comment3.setVisibility(View.GONE);
        comment4.setVisibility(View.GONE);
        comment5.setVisibility(View.GONE);
        comment6.setVisibility(View.GONE);

        int rows = getRowCount();

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.query("Comment", null, null, null, null, null, null);

        if (!cursor.moveToNext()) {
            cursor.close();
            database.close();
            return;
        }

        int showIndex = 1;

        boolean flag;

        if (page < rows / 6 + 1) {
            cursor.moveToPosition(rows - page * 6 + 1);
            flag = false;
        } else {
            cursor.moveToPosition(0);
            flag = true;
        }

        int count = 0;

        do {
            int id = 0;
            if (cursor.getInt(cursor.getColumnIndex("planId")) == tutorialId) {
                if (showIndex == 6) {
                    //cid_1 = cursor.getInt(cursor.getColumnIndex("id"));
                    comment1.setVisibility(View.VISIBLE);
                    //comment1_id.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex("userId"))));
                    id = cursor.getInt(cursor.getColumnIndex("userId"));
                    //favor_1 = cursor.getInt(cursor.getColumnIndex("favor"));
                    comment1_content.setText(cursor.getString(cursor.getColumnIndex("commentContent")));
                    Cursor cursor2 = database.query("User", null, null, null, null, null, null, null);
                    while (cursor2.moveToNext()) {
                        int rowUid = cursor2.getInt(cursor2.getColumnIndex("id"));
                        if (id == rowUid) {
                            comment1_id.setText(cursor2.getString(cursor2.getColumnIndex("name")));
                        }
                    }
                    cursor2.close();
                }

                else if (showIndex == 5) {
                    comment2.setVisibility(View.VISIBLE);
                    id = cursor.getInt(cursor.getColumnIndex("userId"));
                    comment2_content.setText(cursor.getString(cursor.getColumnIndex("commentContent")));

                    Cursor cursor2 = database.query("User", null, null, null, null, null, null, null);
                    while (cursor2.moveToNext()) {
                        int rowUid = cursor2.getInt(cursor2.getColumnIndex("id"));
                        if (id == rowUid) {
                            comment2_id.setText(cursor2.getString(cursor2.getColumnIndex("name")));
                        }
                    }

                    cursor2.close();
                }

                else if (showIndex == 4) {
                    comment3.setVisibility(View.VISIBLE);
                    id = cursor.getInt(cursor.getColumnIndex("userId"));
                    comment3_content.setText(cursor.getString(cursor.getColumnIndex("commentContent")));

                    Cursor cursor2 = database.query("User", null, null, null, null, null, null, null);
                    while (cursor2.moveToNext()) {
                        int rowUid = cursor2.getInt(cursor2.getColumnIndex("id"));
                        if (id == rowUid) {
                            comment3_id.setText(cursor2.getString(cursor2.getColumnIndex("name")));
                        }
                    }

                    cursor2.close();
                }

                else if (showIndex == 3) {
                    comment4.setVisibility(View.VISIBLE);
                    id = cursor.getInt(cursor.getColumnIndex("userId"));
                    comment4_content.setText(cursor.getString(cursor.getColumnIndex("commentContent")));

                    Cursor cursor2 = database.query("User", null, null, null, null, null, null, null);
                    while (cursor2.moveToNext()) {
                        int rowUid = cursor2.getInt(cursor2.getColumnIndex("id"));
                        if (id == rowUid) {
                            comment4_id.setText(cursor2.getString(cursor2.getColumnIndex("name")));
                        }
                    }

                    cursor2.close();
                }

                else if (showIndex == 2) {
                    comment5.setVisibility(View.VISIBLE);
                    id = cursor.getInt(cursor.getColumnIndex("userId"));
                    comment5_content.setText(cursor.getString(cursor.getColumnIndex("commentContent")));

                    Cursor cursor2 = database.query("User", null, null, null, null, null, null, null);
                    while (cursor2.moveToNext()) {
                        int rowUid = cursor2.getInt(cursor2.getColumnIndex("id"));
                        if (id == rowUid) {
                            comment5_id.setText(cursor2.getString(cursor2.getColumnIndex("name")));
                        }
                    }

                    cursor2.close();
                }

                else if (showIndex == 1) {
                    comment6.setVisibility(View.VISIBLE);
                    id = cursor.getInt(cursor.getColumnIndex("userId"));
                    comment6_content.setText(cursor.getString(cursor.getColumnIndex("commentContent")));

                    Cursor cursor2 = database.query("User", null, null, null, null, null, null, null);
                    while (cursor2.moveToNext()) {
                        int rowUid = cursor2.getInt(cursor2.getColumnIndex("id"));
                        if (id == rowUid) {
                            comment6_id.setText(cursor2.getString(cursor2.getColumnIndex("name")));
                        }
                    }

                    cursor2.close();
                }

                showIndex++;
                count++;
            }
        } while(cursor.moveToNext() && ((!flag && count <= 6) || (flag && count <= rows % 6)));

        cursor.close();
        database.close();
    }

    private void refreshPhoto(String imgPath) {
        if (imgPath != null) {
            File imgFile = new File(imgPath);
            if (imgFile.exists()) {
                tutorial_image.setVisibility(View.VISIBLE);
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                tutorial_image.setImageBitmap(bitmap);
            } else {
                // 处理文件不存在的情况
            }
        }
    }

    private String wipe(String input) {
        input = input.replaceAll("sb", "**");
        return input;

        //暂时只想到了用String中的replaceAll来处理屏蔽词，如果有一定屏蔽词可以在这个方法中设定屏蔽成*
    }
}
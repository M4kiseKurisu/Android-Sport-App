package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.group.GroupActivity;
import com.example.myapplication.tutorial.TutorialActivity;

import java.util.ArrayList;
import java.util.Random;

import com.example.myapplication.clockIn.ClockInActivity;

public class MainActivity extends AppCompatActivity {
    private ImageButton button1;

    private ImageButton clockInButton;
    private ImageButton button2;
    private ImageButton button4;
    private DataBaseHelper dbHelper;
    private int userId;
    private String weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginInfor", MODE_PRIVATE);
        userId = sharedPreferences.getInt("UserID", -1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clockInButton = findViewById(R.id.clock_in_button);
        clockInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,ClockInActivity.class);
                startActivity(intent);
            }
        });

        dbHelper = new DataBaseHelper(this, "DataBase.db", null, 1);
        dbHelper.getWritableDatabase();
        updateScrollView();
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, GroupActivity.class);
                startActivity(intent);
            }
        });

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



    private void updateScrollView() {
        LinearLayout verticalLayout = findViewById(R.id.verticalLayout);
        ArrayList<Integer> userParticipateId = searchUserGroupId();
        ArrayList<String> userParticipateType = searchUserGroupType(userParticipateId);
        ArrayList<GroupInfo> groupInfos = searchUserRecommendedGroup(userParticipateId,userParticipateType);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        for (GroupInfo item : groupInfos) {
            LinearLayout borderLayout = new LinearLayout(this);
            borderLayout.setBackgroundResource(R.drawable.textview_border);
            borderLayout.setLayoutParams((new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)));
            borderLayout.setOrientation(LinearLayout.VERTICAL);
            TextView textViewTitle = new TextView(this);
            Cursor cursor = db.rawQuery("SELECT name FROM User WHERE id = ?", new String[] { String.valueOf(item.id) });
            String name = null;
            if (cursor.moveToFirst()) {
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            }
            cursor.close();
            textViewTitle.setText(String.format("活动: "));
            textViewTitle.setTypeface(null, Typeface.BOLD);
            TextView textViewTitleName = new TextView(this);
            textViewTitleName.setText(item.activityTitle);
            LinearLayout section0 = new LinearLayout(this);
            section0.addView(textViewTitle);
            section0.addView(textViewTitleName);
            borderLayout.addView(section0);
            TextView textViewHost = new TextView(this);
            textViewHost.setText(String.format("发起人: "));
            TextView textViewHostName = new TextView(this);
            textViewHostName.setText(name);
            textViewHost.setTypeface(null, Typeface.BOLD);
            LinearLayout section1 = new LinearLayout(this);
            section1.addView(textViewHost);
            section1.addView(textViewHostName);
            borderLayout.addView(section1);
            TextView textViewContent = new TextView(this);
            textViewContent.setText(String.format("运动项目: "));
            textViewContent.setTypeface(null, Typeface.BOLD);
            TextView textViewContentName = new TextView(this);
            textViewContentName.setText(item.activityType);
            LinearLayout section2 = new LinearLayout(this);
            section2.addView(textViewContent);
            section2.addView(textViewContentName);
            borderLayout.addView(section2);
            TextView textViewPeople = new TextView(this);
            textViewPeople.setText(String.format("参加人数: "));
            textViewPeople.setTypeface(null, Typeface.BOLD);
            TextView textViewPeopleNum = new TextView(this);
            textViewPeopleNum.setText(item.peopleNum + " / " + item.maxNum);
            LinearLayout section3 = new LinearLayout(this);
            section3.addView(textViewPeople);
            section3.addView(textViewPeopleNum);
            borderLayout.addView(section3);
            TextView textViewTime = new TextView(this);
            textViewTime.setText(String.format("时间: "));
            textViewTime.setTypeface(null, Typeface.BOLD);
            TextView textViewTimeNum = new TextView(this);
            textViewTimeNum.setText(item.startTime + " -- " + item.endTime);
            LinearLayout section4 = new LinearLayout(this);
            section4.addView(textViewTime);
            section4.addView(textViewTimeNum);
            borderLayout.addView(section4);
            verticalLayout.addView(borderLayout);
        }


        /*for (int i = 0; i < groupInfos.size(); i++) {
            ImageView imageView = new ImageView(this);

            int heightInPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics());
            int marginTopInPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());

            // 设置ImageView的LayoutParams
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    heightInPx
            );
            layoutParams.setMargins(0, marginTopInPx, 0, 0);
            imageView.setLayoutParams(layoutParams);

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


            imageView.setImageResource(R.drawable.image1);


            verticalLayout.addView(imageView);
        }*/
    }

    public ArrayList<Integer> searchUserGroupId() {
        ArrayList<Integer> userParticipateId = new ArrayList<>();
        int uPnum = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = { "activityId" };
        String selection = "userId = ?";
        String[] selectionArgs = { String.valueOf(userId) };
        Cursor cursor = db.query("UserActivity",projection, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                userParticipateId.add(cursor.getInt(cursor.getColumnIndexOrThrow("activityId")));
                // 使用获取的activityId
            }
            cursor.close();
        }
        /*for (int i = 0; i < uPnum; i++) {
            System.out.println(userParticipateId.get(i) + " ");
        }*/
        return userParticipateId;
    }

    public ArrayList<String> searchUserGroupType(ArrayList<Integer> groupId) {
        ArrayList<String> ans = new ArrayList<>();
        int ansLen = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = { "activityType" };
        String selection = "id = ?";
        for (int i = 0; i < groupId.size(); i++) {
            String[] selectionArgs = {String.valueOf(groupId.get(i))};
            Cursor cursor = db.query(
                    "Groups",        // 表名
                    projection,      // 要返回的列
                    selection,       // 列的选择标准
                    selectionArgs,   // 选择标准的参数
                    null,            // groupBy
                    null,            // having
                    null             // orderBy
            );
            if (cursor != null && cursor.moveToFirst()) {
                ans.add(cursor.getString(cursor.getColumnIndexOrThrow("activityType")));
                cursor.close();
            }
        }
        System.out.println(ansLen);
       /* for (int i = 0; i < ans.size(); i++) {
            System.out.println(ans.get(i));
        }*/
        return ans;
    }

    public ArrayList<GroupInfo> searchUserRecommendedGroup(ArrayList<Integer> groupId, ArrayList<String> groupType) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                "id", "hostId", "peopleNum", "startTime", "endTime", "maxNum", "activityTitle"
        };
        ArrayList<GroupInfo> groupInfoList = new ArrayList<>();
        String selection = "activityType = ?";
        for (int i = 0; i < groupType.size(); i++) {
            String[] selectionArgs = {groupType.get(i)};

            Cursor cursor = db.query(
                    "Groups",        // 表名
                    projection,      // 要返回的列
                    selection,       // 列的选择标准
                    selectionArgs,   // 选择标准的参数
                    null,            // groupBy
                    null,            // having
                    null             // orderBy
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    GroupInfo groupInfo = new GroupInfo(
                            cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("hostId")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("peopleNum")),
                            cursor.getString(cursor.getColumnIndexOrThrow("startTime")),
                            cursor.getString(cursor.getColumnIndexOrThrow("endTime")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("maxNum")),
                            cursor.getString(cursor.getColumnIndexOrThrow("activityTitle")),
                            groupType.get(i)
                    );
                    boolean flag = true;
                    for (int j = 0; j < groupId.size(); j++) {
                        if (groupInfo.id == groupId.get(j)) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        if (groupInfo.peopleNum < groupInfo.maxNum) {
                            groupInfoList.add(groupInfo);
                            /*if (weather.equals("晴天")) {
                                groupInfoList.add(groupInfo);
                            } else {
                                if (groupInfo.activityType.equals("游泳") || groupInfo.activityType.equals("台球") || groupInfo.activityType.equals("乒乓球")
                                        || groupInfo.activityType.equals("健身") || groupInfo.activityType.equals("羽毛球")) {
                                    groupInfoList.add(groupInfo);
                                }
                            }*/
                        }
                    }
                } while (cursor.moveToNext());
                cursor.close();
            }
        }
        if (groupInfoList.size() < 5) {
            Cursor cursor = db.rawQuery("SELECT * FROM Groups", null);
            if (cursor.moveToFirst()) {
                do {
                    GroupInfo groupInfo = new GroupInfo(
                            cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("hostId")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("peopleNum")),
                            cursor.getString(cursor.getColumnIndexOrThrow("startTime")),
                            cursor.getString(cursor.getColumnIndexOrThrow("endTime")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("maxNum")),
                            cursor.getString(cursor.getColumnIndexOrThrow("activityTitle")),
                            cursor.getString(cursor.getColumnIndexOrThrow("activityType"))
                            // 可以添加其他字段
                    );
                    boolean flag = true;
                    for (int i = 0; i < groupId.size(); i++) {
                        if (groupInfo.id == groupId.get(i)) {
                            flag = false;
                            break;
                        }
                    }
                    for (int i = 0; i < groupInfoList.size(); i++) {
                        if (groupInfo.id == groupInfoList.get(i).id) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        groupInfoList.add(groupInfo);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return groupInfoList;
    }

    private void getWeather() {
        Random random = new Random();
        int randomInt = random.nextInt();
        int randomInt100 = random.nextInt(100);
        if (randomInt100 <= 50) {
            weather = "晴天";
        } else if (randomInt <= 75){
            weather = "下雨";
        } else if (randomInt <= 85) {
            weather = "刮风";
        } else if (randomInt <= 95) {
            weather = "下雪";
        } else {
            weather = "雾霾";
        }
        TextView textView = findViewById(R.id.weather);
        textView.setText("今日天气:" + weather);
    }

    public static class GroupInfo {
        public int id;
        public int hostId;
        public int peopleNum;
        public String startTime;
        public String endTime;
        public int maxNum;
        public String activityTitle;
        public String activityType;

        public GroupInfo(int id, int hostId, int peopleNum, String startTime,
                         String endTime, int maxNum, String activityTitle, String activityType) {
            this.id = id;
            this.hostId = hostId;
            this.peopleNum = peopleNum;
            this.startTime = startTime;
            this.endTime = endTime;
            this.maxNum = maxNum;
            this.activityTitle = activityTitle;
            this.activityType = activityType;
        }
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
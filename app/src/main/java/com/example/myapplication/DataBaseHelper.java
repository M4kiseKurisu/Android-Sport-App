package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_USER = "create table User(" +
            //primary key 将id列设为主键    autoincrement表示id列是自增长的
            "id integer primary key autoincrement," +
            "name text," +
            "password text)";
    public static final String CREATE_CLOCKIN = "create table ClockIn(" +
            //primary key 将id列设为主键    autoincrement表示id列是自增长的
            "id integer primary key autoincrement," +
            "userId integer," +
            "sportItem text," +
            "love integer," +
            "sportAdd text," +
            "startTime text," +
            "endTime text," +
            "imgPath text," + ""+
            "FOREIGN KEY (userId) REFERENCES User(id) ON DELETE CASCADE)";
    public static final String CREATE_GROUP = "create table Groups(" +
            //primary key 将id列设为主键    autoincrement表示id列是自增长的
            "id integer primary key autoincrement," +
            "activityTitle text," +
            "activityType text," +
            "hostId integer," +
            "peopleNum integer," +
            "startTime text," +
            "endTime text," +
            "activityIntro text," +
            "activityAdd text," +
            "maxNum integer," +
            "FOREIGN KEY (hostId) REFERENCES User(id) ON DELETE CASCADE)";
    public static final String CREATE_USERACTIVITYTABLE = "create table UserActivity(" +
            "userId integer," +
            "activityId integer," +
            "FOREIGN KEY (userId) REFERENCES User(id) ON DELETE CASCADE," +
            "FOREIGN KEY (activityId) REFERENCES Groups(id) ON DELETE CASCADE," +
            "PRIMARY KEY (userId, activityId))";

    public static final String CREATE_TRAVELPLAN = "create table TravelPlan(" +
            //primary key 将id列设为主键    autoincrement表示id列是自增长的
            "id integer primary key autoincrement," +
            "userId integer," +
            "planTitle text," +
            "planContent text," +
            "planType text," +
            "favor integer," +
            "FOREIGN KEY (userId) REFERENCES User(id) ON DELETE CASCADE)";

    public static final String CREATE_COMMENT = "create table Comment(" +
            //primary key 将id列设为主键    autoincrement表示id列是自增长的
            "id integer primary key autoincrement," +
            "userId integer," +
            "planId integer," +
            "commentContent text," +
            "favor integer," +
            "FOREIGN KEY (userId) REFERENCES User(id) ON DELETE CASCADE," +
            "FOREIGN KEY (planId) REFERENCES TravelPlan(id) ON DELETE CASCADE)";

    private Context mContext;

    //构造方法：第一个参数Context，第二个参数数据库名，第三个参数cursor允许我们在查询数据的时候返回一个自定义的光标位置，一般传入的都是null，第四个参数表示目前库的版本号（用于对库进行升级）
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //调用SQLiteDatabase中的execSQL（）执行建表语句。
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_CLOCKIN);
        db.execSQL(CREATE_COMMENT);
        db.execSQL(CREATE_GROUP);
        db.execSQL(CREATE_TRAVELPLAN);
        db.execSQL(CREATE_USERACTIVITYTABLE);
        //创建成功
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

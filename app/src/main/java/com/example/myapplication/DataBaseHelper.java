package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_USER = "create table User(" +
            //primary key 将id列设为主键    autoincrement表示id列是自增长的
            "id integer primary key autoincrement," +
            "name text," +
            "password text," +
            "phoneNumber text)";
    public static final String CREATE_LOGIN = "create table LoginTB(" +
            "name text primary key," +
            "password text)";
    public static final String CREATE_ID = "create table IdTB(" +
            "name text primary key," +
            "id integer)";

    public static final String CREATE_FORGETPWD = "create table PasswordTB(" +
            //primary key 将id列设为主键    autoincrement表示id列是自增长的
            "phoneNumber text primary key," +
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
        db.execSQL(CREATE_LOGIN);
        db.execSQL(CREATE_FORGETPWD);
        db.execSQL(CREATE_CLOCKIN);
        db.execSQL(CREATE_COMMENT);
        db.execSQL(CREATE_GROUP);
        db.execSQL(CREATE_TRAVELPLAN);
        db.execSQL(CREATE_USERACTIVITYTABLE);
        db.execSQL(CREATE_ID);
        //创建成功
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);

        long result = db.insert("User", null, values);
        db.close();

        return result != -1; // 如果result为-1，则插入失败
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("User", new String[]{"id"}, "name=? AND password=?", new String[]{username, password}, null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public int getUserIdByUsername(String username) {
        int userId = -2; // 默认值，表示未找到
        Cursor cursor = null;

        try {
            String[] columns = new String[]{"id"}; // 要返回的列
            String selection = "name = ?"; // 查询条件
            String[] selectionArgs = new String[]{username}; // 查询条件的参数

            // 执行查询
            cursor = this.getWritableDatabase().query("IdTB", columns, selection, selectionArgs, null, null, null);

            // 读取查询结果
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow("id");
                userId = cursor.getInt(index);
            }
        } catch (Exception e) {
            // 处理异常
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return userId;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


package com.example.myapplication.tutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.Manifest;

import com.example.myapplication.DataBaseHelper;
import com.example.myapplication.R;

import java.io.File;

public class TutorialWrite extends AppCompatActivity {
    private EditText title;
    private EditText content;
    private Spinner category;
    private Button commit;
    private Button addImageButton;

    private String selectedCategory;
    private String imagePath = null;
    private ImageView img;

    private DataBaseHelper dbHelper = new DataBaseHelper(this, "DataBase.db", null, 1);

    private static final int REQUEST_SELECT_PHOTO = 1;

    private static final int REQUEST_PERMISSION = 1;
    private static final String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestPermission();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_write);

        title = findViewById(R.id.write_activity_title);
        content = findViewById(R.id.write_activity_content);
        category = findViewById(R.id.write_activity_category);
        commit = findViewById(R.id.write_activity_commit);
        addImageButton = findViewById(R.id.add_image_button);
        img = findViewById(R.id.input_image);

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();

            }
        });

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

                    values.put("img", imagePath);

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

    private void refreshPhoto() {
        if (this.imagePath != null) {
            File imgFile = new File(this.imagePath);
            if (imgFile.exists()) {
                img.setVisibility(View.VISIBLE);
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                img.setImageBitmap(bitmap);
            } else {
                // 处理文件不存在的情况
            }
        }
    }

    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_SELECT_PHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            this.imagePath = getImagePathFromUri(imageUri);
            Log.d("id:", this.imagePath);
            refreshPhoto();
        }
    }

    private String getImagePathFromUri(Uri uri) {
        String imagePath = null; //将imagePath设置成为属性
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是Document类型的URI，则通过Document ID处理
            String documentId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是Content类型的URI，则直接获取路径
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是File类型的URI，则直接获取路径
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限已授予，执行相应操作
                // 例如，你可以在这里调用刷新照片的方法
                refreshPhoto();
            } else {
                // 权限被拒绝，处理相应的逻辑
                // 例如，你可以显示一个提示消息告知用户需要权限才能加载图片
                Toast.makeText(this, "需要授予读取外部存储器的权限才能加载图片", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
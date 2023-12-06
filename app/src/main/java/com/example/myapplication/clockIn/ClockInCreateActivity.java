package com.example.myapplication.clockIn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.myapplication.DataBaseHelper;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.io.File;
import java.util.Calendar;

public class ClockInCreateActivity extends AppCompatActivity {
    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;
    private RadioGroup radioGroup3;
    private final Calendar calendar = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private TextView textViewStart;
    private TextView textViewEnd;
    private EditText sportPlace;
    private Button addImg;
    private String imagePath = "null";
    private static final int REQUEST_CODE_PICK_IMAGE = 1;
    private DataBaseHelper dbHelper = new DataBaseHelper(this, "DataBase.db", null, 1);
    private String[] sports = new String[]{"TD线", "跑步", "篮球", "排球", "羽毛球",
            "乒乓球", "网球", "足球", "台球", "游泳", "飞盘", "健身"};
    private int sportIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_in_create);

        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        radioGroup3 = (RadioGroup) findViewById(R.id.radioGroup3);

        radioGroup1.setOnCheckedChangeListener(groupOneListener);
        radioGroup2.setOnCheckedChangeListener(groupTwoListener);
        radioGroup3.setOnCheckedChangeListener(groupThreeListener);


        textViewStart = findViewById(R.id.textViewStart);
        textViewEnd = findViewById(R.id.textViewEnd);
        sportPlace = findViewById(R.id.sport_place);
        Button btnStart = findViewById(R.id.clockStart);
        Button btnEnd = findViewById(R.id.clockEnd);
        ImageButton back = findViewById(R.id.clock_in_create_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDateAndTimePicker(textViewStart);
            }
        });
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDateAndTimePicker(textViewEnd);
            }
        });

        Button addSport = findViewById(R.id.clock_add_img);//加图片按钮
        addSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        addImg = findViewById(R.id.clock_add_img);
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chooseImageFromGallery();
                addImg.setVisibility(View.GONE);
                TextView textView = findViewById(R.id.add_img_back_ground);
                textView.setVisibility(View.GONE);
                ImageView imageView = findViewById(R.id.sport_img);
                imageView.setImageResource(R.drawable.image1);
                imageView.setVisibility(View.VISIBLE);
//                File imgFile = new File(imagePath);
//                if (imgFile.exists()) {
//                    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                    imageView.setImageBitmap(bitmap);
//                }
            }
        });

        Button createBtn = findViewById(R.id.create_clock_in);//向数据库中插入打卡数据
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("userId", 1);
                values.put("sportItem", sports[sportIndex]);
                values.put("love", 0);
                values.put("sportAdd", sportPlace.getText().toString());
                values.put("startTime", textViewStart.getText().toString());
                values.put("endTime", textViewEnd.getText().toString());
                values.put("imgPath",imagePath);
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                long id = database.insert("ClockIn", null, values);
                Log.d("id:", String.valueOf(id));
                Log.d("id:", "test");
                database.close();
                finish();
            }
        });




        ////////////获取数据的方式
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM ClockIn", null);
//        while (cursor.moveToNext()) {
//            int id = cursor.getInt(cursor.getColumnIndex("id"));
//            int userId = cursor.getInt(cursor.getColumnIndex("userId"));
//            String sportItem = cursor.getString(cursor.getColumnIndex("sportItem"));
//            int love = cursor.getInt(cursor.getColumnIndex("love"));
//            String sportAdd = cursor.getString(cursor.getColumnIndex("sportAdd"));
//            String startTime = cursor.getString(cursor.getColumnIndex("startTime"));
//            String endTime = cursor.getString(cursor.getColumnIndex("endTime"));
//            Log.d("test",sportItem);
//            // 在这里使用提取的数据进行操作
//            cursor.close();
//            db.close();
//        }
        ////////////
    }

    private RadioGroup.OnCheckedChangeListener groupOneListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (radioGroup1.getCheckedRadioButtonId() != -1) {
                // 如果radioGroup1里有选中的RadioButton，清空radioGroup2的选中状态
                radioGroup2.setOnCheckedChangeListener(null);
                radioGroup2.clearCheck();
                radioGroup2.setOnCheckedChangeListener(groupTwoListener);
                radioGroup3.setOnCheckedChangeListener(null);
                radioGroup3.clearCheck();
                radioGroup3.setOnCheckedChangeListener(groupTwoListener);
                if (checkedId == R.id.radioButton1) {
                    sportIndex = 0;
                } else if (checkedId == R.id.radioButton2) {
                    sportIndex = 1;
                } else if (checkedId == R.id.radioButton3) {
                    sportIndex = 2;
                } else if (checkedId == R.id.radioButton4) {
                    sportIndex = 3;
                }
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener groupTwoListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (radioGroup2.getCheckedRadioButtonId() != -1) {
                // 如果radioGroup2里有选中的RadioButton，清空radioGroup1的选中状态
                radioGroup1.setOnCheckedChangeListener(null);
                radioGroup1.clearCheck();
                radioGroup1.setOnCheckedChangeListener(groupOneListener);
                radioGroup3.setOnCheckedChangeListener(null);
                radioGroup3.clearCheck();
                radioGroup3.setOnCheckedChangeListener(groupTwoListener);
                if (checkedId == R.id.radioButton5) {
                    sportIndex = 4;
                } else if (checkedId == R.id.radioButton6) {
                    sportIndex = 5;
                } else if (checkedId == R.id.radioButton7) {
                    sportIndex = 6;
                } else if (checkedId == R.id.radioButton8) {
                    sportIndex = 7;
                }
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener groupThreeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (radioGroup3.getCheckedRadioButtonId() != -1) {
                // 如果radioGroup2里有选中的RadioButton，清空radioGroup1的选中状态
                radioGroup1.setOnCheckedChangeListener(null);
                radioGroup1.clearCheck();
                radioGroup1.setOnCheckedChangeListener(groupOneListener);
                radioGroup2.setOnCheckedChangeListener(null);
                radioGroup2.clearCheck();
                radioGroup2.setOnCheckedChangeListener(groupTwoListener);
                if (checkedId == R.id.radioButton9) {
                    sportIndex = 8;
                } else if (checkedId == R.id.radioButton10) {
                    sportIndex = 9;
                } else if (checkedId == R.id.radioButton11) {
                    sportIndex = 10;
                } else if (checkedId == R.id.radioButton12) {
                    sportIndex = 11;
                }
            }
        }
    };

    private void handleDateAndTimePicker(final TextView textView) {
        //日期选择器
        datePickerDialog = new DatePickerDialog(ClockInCreateActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                //时间选择器
                timePickerDialog = new TimePickerDialog(ClockInCreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        textView.setText(year + "-" + (month + 1) + "-" + day + " " + hourOfDay + ":" + minute);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    /////图片上传测试
    // 打开相册
    private void chooseImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    // 处理相册选择结果的回调方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            // 获取选择的图片的URI
            Uri imageUri = data.getData();
            // 通过URI获取图片的路径
            String imagePath = getImagePathFromUri(imageUri);
            // 在这里可以对图片路径进行处理或上传操作
        }
    }

    // 根据URI获取图片的路径
    private String getImagePathFromUri(Uri uri) {
        //String imagePath = null; 将imagePath设置成为属性
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

    // 根据URI和查询条件获取图片的路径   应该用不上吧
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


}

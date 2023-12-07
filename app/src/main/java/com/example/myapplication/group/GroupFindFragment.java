package com.example.myapplication.group;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.DataBaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GroupFindFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public GroupFindFragment() {
        // Required empty public constructor
    }

    public static GroupFindFragment newInstance(String param1, String param2) {
        GroupFindFragment fragment = new GroupFindFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_find, container, false);
        draw(view);
        // 新建活动
        ImageButton create = view.findViewById(R.id.group_activity_create_button);
        createActivity(create);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        View view = getView();
        assert view != null;
        draw(view);
    }

    // 根据数据库渲染页面
    private void draw(View view) {
        LinearLayout linearLayout = view.findViewById(R.id.group_find_container);
        linearLayout.removeAllViews();

        SQLiteDatabase db;
        try (DataBaseHelper dbHelper = new DataBaseHelper(getActivity(), "DataBase.db", null, 1)) {
            db = dbHelper.getReadableDatabase();
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginInfor", MODE_PRIVATE);
            int userId = sharedPreferences.getInt("UserID", -1);
            String query = "SELECT G.*, U.name " +
                    "FROM Groups G " +
                    "JOIN User U ON G.hostId = U.id " +
                    "WHERE G.id NOT IN ( " +
                    "    SELECT UA.activityId " +
                    "    FROM UserActivity UA " +
                    "    WHERE UA.userId = " + userId +
                    ") " +
                    "ORDER BY datetime(G.startTime) DESC";

            Date currentTime = new Date(System.currentTimeMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                // 提取活动字段信息
                int activityId = cursor.getInt(0);
                String activityTitle = cursor.getString(1);
                String activityType = cursor.getString(2);
                int hostId = cursor.getInt(3);
                int peopleNum = cursor.getInt(4);
                String startTime = cursor.getString(5);
                String endTime = cursor.getString(6);
                String activityIntro = cursor.getString(7);
                String activityAdd = cursor.getString(8);
                int maxNum = cursor.getInt(9);
                String creatorName = cursor.getString(10);
                try {
                    if (dateFormat.parse(startTime).compareTo(currentTime) > 0) {
                        drawActivity(view, activityId, activityTitle, activityType, peopleNum, maxNum,
                                startTime, endTime, activityIntro, activityAdd, creatorName);
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
            cursor.close();
            db.close();
        }
    }

    // 渲染特定组团信息
    private void drawActivity(View view, int activityId, String title, String type, int capacity, int maximum,
                              String startTime, String endTime, String intro, String address, String creatorName) {
        LinearLayout linearLayout = view.findViewById(R.id.group_find_container);
        CardView cardView = new CardView(requireContext());
        cardView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        cardView.setCardBackgroundColor(Color.WHITE);
        cardView.setRadius(20);
        cardView.setCardElevation(8);
        cardView.setUseCompatPadding(true);

        LinearLayout innerLinearLayout = new LinearLayout(requireContext());
        innerLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dpToPx(120)
        ));
        innerLinearLayout.setOrientation(LinearLayout.VERTICAL);
        innerLinearLayout.setPadding(
                dpToPx(15), dpToPx(2), dpToPx(15), 0
        );

        TextView titleTextView = new TextView(requireContext());
        titleTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(38)
        ));
        titleTextView.setBackgroundColor(Color.WHITE);
        titleTextView.setGravity(Gravity.CENTER | Gravity.START);
        titleTextView.setText(title);
        titleTextView.setTextColor(Color.parseColor("#01345C"));
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 27);
        titleTextView.setTypeface(Typeface.DEFAULT_BOLD);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );

        LinearLayout dateTimeLayout = new LinearLayout(requireContext());
        dateTimeLayout.setLayoutParams(layoutParams);
        dateTimeLayout.setOrientation(LinearLayout.HORIZONTAL);
        dateTimeLayout.setPadding(
                0, dpToPx(5), 0, 0
        );

        TextView timeTextView = new TextView(requireContext());
        timeTextView.setLayoutParams(new LinearLayout.LayoutParams(
                0, dpToPx(30), 1
        ));
        timeTextView.setBackgroundColor(Color.WHITE);
        timeTextView.setText("时间：" + startTime.split(" ")[0]);
        timeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);

        TextView creatorTextView = new TextView(requireContext());
        creatorTextView.setLayoutParams(new LinearLayout.LayoutParams(
                0, dpToPx(30), 1
        ));
        creatorTextView.setBackgroundColor(Color.WHITE);
        creatorTextView.setGravity(Gravity.END);
        creatorTextView.setText("发起人：" + creatorName);
        creatorTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);

        dateTimeLayout.addView(timeTextView);
        dateTimeLayout.addView(creatorTextView);

        LinearLayout buttonsLayout = new LinearLayout(requireContext());
        buttonsLayout.setLayoutParams(layoutParams);
        buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonsLayout.setPadding(
                dpToPx(10), dpToPx(5), dpToPx(10), 0
        );

        Button detailsButton = new Button(requireContext());
        LinearLayout.LayoutParams detailsButtonParams = new LinearLayout.LayoutParams(
                0, dpToPx(30), 1
        );
        detailsButtonParams.setMarginEnd(dpToPx(140));
        detailsButton.setLayoutParams(detailsButtonParams);
        detailsButton.setBackgroundColor(Color.parseColor("#058BC8"));
        detailsButton.setGravity(Gravity.CENTER);
        detailsButton.setSingleLine(true);
        detailsButton.setEllipsize(TextUtils.TruncateAt.END);
        detailsButton.setText("查看详情");
        detailsButton.setTextColor(Color.WHITE);
        detailsButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        detailsButton.setTypeface(Typeface.DEFAULT_BOLD);
        detailsButton.setPadding(0, 0, 0, 0);
        detailsButton.setOnClickListener(v -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater1 = GroupFindFragment.this.getLayoutInflater();
            View dialogView = inflater1.inflate(R.layout.dialog_group_activity_detail, null);
            TextView titleView = dialogView.findViewById(R.id.group_activity_title);
            titleView.setText(title);
            TextView category = dialogView.findViewById(R.id.group_activity_category);
            category.setText(type);
            TextView creator = dialogView.findViewById(R.id.group_activity_creator);
            creator.setText(creatorName);
            TextView person = dialogView.findViewById(R.id.group_activity_person);
            person.setText(capacity + "/" + maximum);
            TextView time = dialogView.findViewById(R.id.group_activity_time);
            time.setText(startTime.split(" ")[0] + " " + startTime.split(" ")[1] + "-" + endTime.split(" ")[1]);
            TextView location = dialogView.findViewById(R.id.group_activity_location);
            location.setText(address);
            TextView description = dialogView.findViewById(R.id.group_activity_content);
            description.setText(intro);
            Button closeButton = dialogView.findViewById(R.id.group_activity_close_button);
            dialogBuilder.setView(dialogView);
            AlertDialog alertDialog = dialogBuilder.create();

            closeButton.setOnClickListener(v1 -> alertDialog.dismiss());
            alertDialog.show();
        });

        Button joinButton = new Button(requireContext());
        LinearLayout.LayoutParams joinButtonParams = new LinearLayout.LayoutParams(
                0, dpToPx(30), 1
        );
        joinButton.setLayoutParams(joinButtonParams);
        joinButton.setBackgroundColor(Color.parseColor("#E61A9F1F"));
        joinButton.setGravity(Gravity.CENTER);
        joinButton.setSingleLine(true);
        joinButton.setText("加入活动");
        joinButton.setTextColor(Color.WHITE);
        joinButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        joinButton.setTypeface(Typeface.DEFAULT_BOLD);
        joinButton.setPadding(0, 0, 0, 0);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater1 = GroupFindFragment.this.getLayoutInflater();
                View dialogView = inflater1.inflate(R.layout.dialog_confirm, null);
                TextView titleView = dialogView.findViewById(R.id.confirm_title);
                titleView.setText("确认是否加入活动：" + title);

                dialogBuilder.setView(dialogView);
                AlertDialog alertDialog = dialogBuilder.create();

                Button button = dialogView.findViewById(R.id.confirm_yes_button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addActivity(activityId);
                        alertDialog.dismiss();
                    }
                });
                button = dialogView.findViewById(R.id.confirm_no_button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        buttonsLayout.addView(detailsButton);
        buttonsLayout.addView(joinButton);
        innerLinearLayout.addView(titleTextView);
        innerLinearLayout.addView(dateTimeLayout);
        innerLinearLayout.addView(buttonsLayout);
        cardView.addView(innerLinearLayout);
        linearLayout.addView(cardView);
    }

    // 用户加入活动
    private void addActivity(int activityId) {
        Log.d("activity", String.valueOf(activityId));
        SQLiteDatabase db;
        try (DataBaseHelper dbHelper = new DataBaseHelper(getActivity(), "DataBase.db", null, 1)) {
            db = dbHelper.getWritableDatabase();
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginInfor", MODE_PRIVATE);
            int userId = sharedPreferences.getInt("UserID", -1);

            int currentNum = getCurrentPeopleNum(db, activityId);
            int maxNum = getMaxNum(db, activityId);

            // 检查是否达到人数上限
            if (currentNum >= maxNum) {
                Toast.makeText(getActivity(), "活动人数已达上限", Toast.LENGTH_SHORT).show();
                return;
            }

            db.execSQL("UPDATE Groups SET peopleNum = peopleNum + 1 WHERE id = ?", new String[]{String.valueOf(activityId)});
            ContentValues values = new ContentValues();
            values.put("userId", userId);
            values.put("activityId", activityId);
            db.insert("UserActivity", null, values);
            db.close();
            Toast.makeText(getActivity(), "成功加入活动", Toast.LENGTH_SHORT).show();
            draw(getView());
        }
    }

    // 获取当前活动的人数
    private int getCurrentPeopleNum(SQLiteDatabase db, int activityId) {
        Cursor cursor = db.rawQuery("SELECT peopleNum FROM Groups WHERE id = ?", new String[]{String.valueOf(activityId)});
        if (cursor.moveToFirst()) {
            int currentPeopleNum = cursor.getInt(0);
            cursor.close();
            return currentPeopleNum;
        }
        cursor.close();
        return 0;
    }

    // 获取活动的最大人数
    private int getMaxNum(SQLiteDatabase db, int activityId) {
        Cursor cursor = db.rawQuery("SELECT maxNum FROM Groups WHERE id = ?", new String[]{String.valueOf(activityId)});
        if (cursor.moveToFirst()) {
            int maxNum = cursor.getInt(0);
            cursor.close();
            return maxNum;
        }
        cursor.close();
        return 0;
    }

    // 创建活动
    private void createActivity(ImageButton button) {
        button.setOnClickListener(v -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater1 = GroupFindFragment.this.getLayoutInflater();
            View dialogView = inflater1.inflate(R.layout.dialog_group_activity_create, null);
            dialogBuilder.setView(dialogView);
            AlertDialog alertDialog = dialogBuilder.create();
            // 最大人数
            EditText maxNumEditText = dialogView.findViewById(R.id.group_activity_create_max_num);
            ImageButton minusButton = dialogView.findViewById(R.id.group_activity_create_minus_button);
            ImageButton plusButton = dialogView.findViewById(R.id.group_activity_create_plus_button);

            minusButton.setOnClickListener(v13 -> {
                try {
                    int maxNum = Integer.parseInt(maxNumEditText.getText().toString());
                    if (maxNum > 0) {
                        maxNum--;
                        maxNumEditText.setText(String.valueOf(maxNum));
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "请输入有效的数字", Toast.LENGTH_SHORT).show();
                }
            });

            plusButton.setOnClickListener(v12 -> {
                try {
                    int maxNum = Integer.parseInt(maxNumEditText.getText().toString());
                    maxNum++;
                    maxNumEditText.setText(String.valueOf(maxNum));
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "请输入有效的数字", Toast.LENGTH_SHORT).show();
                }

            });

            // 实现时间选择
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            TextView startTimeText = dialogView.findViewById(R.id.group_activity_create_startTime);
            TextView endTimeText = dialogView.findViewById(R.id.group_activity_create_endTime);
            startTimeText.setOnClickListener(v_ -> {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        (tempView, yearOfYear, monthOfYear, dayOfMonth) -> {
                            c.set(yearOfYear, monthOfYear, dayOfMonth);
                            int hour = c.get(Calendar.HOUR_OF_DAY);
                            int minute = c.get(Calendar.MINUTE);
                            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                                    (timerView, hourOfDay, minuteOfDay) -> {
                                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        c.set(Calendar.MINUTE, minuteOfDay);

                                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                                        startTimeText.setText(format.format(c.getTime()));
                                    }, hour, minute, true);

                            timePickerDialog.show();
                        }, year, month, day);

                datePickerDialog.show();
            });
            endTimeText.setOnClickListener(v_ -> {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        (tempView, yearOfYear, monthOfYear, dayOfMonth) -> {
                            c.set(yearOfYear, monthOfYear, dayOfMonth);
                            int hour = c.get(Calendar.HOUR_OF_DAY);
                            int minute = c.get(Calendar.MINUTE);

                            // 创建一个新的时间选择器对话框对象
                            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                                    (timerView, hourOfDay, minuteOfDay) -> {
                                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        c.set(Calendar.MINUTE, minuteOfDay);

                                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                                        endTimeText.setText(format.format(c.getTime()));
                                        String endTimeString = format.format(c.getTime());

                                        // 验证结束时间是否晚于开始时间
                                        try {
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                                            Date startTime = dateFormat.parse(startTimeText.getText().toString());
                                            Date endTime = dateFormat.parse(endTimeString);
                                            if (!isSameDay(startTime, endTime)) {
                                                Toast.makeText(getContext(), "开始时间和结束时间必须是同一天", Toast.LENGTH_SHORT).show();
                                                endTimeText.setText("");
                                            } else if (endTime.before(startTime)) {
                                                Toast.makeText(getContext(), "结束时间不能早于开始时间", Toast.LENGTH_SHORT).show();
                                                endTimeText.setText("");
                                            } else {
                                                endTimeText.setText(endTimeString);
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }, hour, minute, true);

                            timePickerDialog.show();
                        }, year, month, day);

                datePickerDialog.show();
            });

            // 下拉类别菜单
            Spinner spinner = dialogView.findViewById(R.id.group_activity_create_category);
            ArrayList<String> dataList = new ArrayList<>(Arrays.asList("跑步", "羽毛球", "篮球", "足球",
                    "排球", "乒乓球", "台球", "游泳", "网球", "飞盘", "健身", "TD线", "其他"));
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, dataList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


            // 关闭按钮
            Button closeButton = dialogView.findViewById(R.id.group_activity_create_close_button);
            closeButton.setOnClickListener(v1 -> alertDialog.dismiss());

            // 确认按钮
            Button confirmButton = dialogView.findViewById(R.id.group_activity_create_confirm_button);
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText nameEditText = dialogView.findViewById(R.id.group_activity_create_name);
                    EditText locationEditText = dialogView.findViewById(R.id.group_activity_create_location);
                    EditText descEditText = dialogView.findViewById(R.id.group_activity_create_desc);
                    TextView startTimeTextView = dialogView.findViewById(R.id.group_activity_create_startTime);
                    TextView endTimeTextView = dialogView.findViewById(R.id.group_activity_create_endTime);
                    Spinner categorySpinner = dialogView.findViewById(R.id.group_activity_create_category);

                    String name = nameEditText.getText().toString();
                    String location = locationEditText.getText().toString();
                    String desc = descEditText.getText().toString();
                    String startTime = startTimeTextView.getText().toString();
                    String endTime = endTimeTextView.getText().toString();
                    String category = categorySpinner.getSelectedItem().toString();
                    int maxNum;
                    try {
                        maxNum = Integer.parseInt(maxNumEditText.getText().toString());
                        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(location) || TextUtils.isEmpty(desc) ||
                                TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime) || TextUtils.isEmpty(category)) {
                            Toast.makeText(getContext(), "请输入完整信息", Toast.LENGTH_SHORT).show();
                        } else {
                            addNewGroupActivity(name, category, startTime, endTime, desc, location, maxNum);
                            alertDialog.dismiss();
                            Toast.makeText(getContext(), "已成功创建组团活动", Toast.LENGTH_SHORT).show();
                            draw(getView());
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(getActivity(), "请输入有效的数字", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            alertDialog.show();
        });
    }

    private void addNewGroupActivity(String title, String type, String startTime, String endTime,
                                     String intro, String address, int maxNum) {
        SQLiteDatabase db;
        try (DataBaseHelper dbHelper = new DataBaseHelper(getActivity(), "DataBase.db", null, 1)) {
            db = dbHelper.getWritableDatabase();
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginInfor", MODE_PRIVATE);
            int userId = sharedPreferences.getInt("UserID", -1);
            // 插入组团活动表
            ContentValues values = new ContentValues();
            values.put("activityTitle", title);
            values.put("activityType", type);
            values.put("hostId", userId);
            values.put("peopleNum", 1);
            values.put("startTime", startTime);
            values.put("endTime", endTime);
            values.put("activityIntro", intro);
            values.put("activityAdd", address);
            values.put("maxNum", maxNum);
            long insertId = db.insert("Groups", null, values);
            // 插入联系表
            values = new ContentValues();
            values.put("userId", userId);
            values.put("activityId", (int) insertId);
            db.insert("UserActivity", null, values);
            db.close();
        }
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    // 检查两个日期是否为同一天
    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }
}
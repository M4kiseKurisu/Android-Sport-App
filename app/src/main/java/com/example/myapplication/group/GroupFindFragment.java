package com.example.myapplication.group;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_find, container, false);

        LinearLayout linearLayout = view.findViewById(R.id.group_find_container);
        for (int i = 0; i < 5; ++i) {
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
            titleTextView.setText("大运村聚会");
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
            timeTextView.setText("时间：2023-12-12");
            timeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);

            TextView creatorTextView = new TextView(requireContext());
            creatorTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    0, dpToPx(30), 1
            ));
            creatorTextView.setBackgroundColor(Color.WHITE);
            creatorTextView.setGravity(Gravity.END);
            creatorTextView.setText("发起人：吴浩宇");
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
            detailsButtonParams.setMarginEnd(dpToPx(140)); // 设置按钮之间的间距
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
                TextView title = dialogView.findViewById(R.id.group_activity_title);
                title.setText("大运村桌游聚会");
                TextView creator = dialogView.findViewById(R.id.group_activity_creator);
                creator.setText("吴浩宇");
                TextView person = dialogView.findViewById(R.id.group_activity_person);
                person.setText("15/200");
                TextView time = dialogView.findViewById(R.id.group_activity_time);
                time.setText("2023-11-11 19:00-22:00");
                TextView location = dialogView.findViewById(R.id.group_activity_location);
                location.setText("大运村广场");
                TextView description = dialogView.findViewById(R.id.group_activity_content);
                description.setText("这是一场\"浩\"劫\n");
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

            buttonsLayout.addView(detailsButton);
            buttonsLayout.addView(joinButton);

            innerLinearLayout.addView(titleTextView);
            innerLinearLayout.addView(dateTimeLayout);
            innerLinearLayout.addView(buttonsLayout);

            cardView.addView(innerLinearLayout);
            linearLayout.addView(cardView);
        }

        // 新建活动
        ImageButton create = view.findViewById(R.id.group_activity_create_button);
        create.setOnClickListener(v -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater1 = GroupFindFragment.this.getLayoutInflater();
            View dialogView = inflater1.inflate(R.layout.dialog_group_activity_create, null);
            dialogBuilder.setView(dialogView);
            AlertDialog alertDialog = dialogBuilder.create();

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
                            // 创建一个新的时间选择器对话框对象
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
                                            if (endTime.before(startTime)) {
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

                    Log.d("Form Content", "Name: " + name);
                    Log.d("Form Content", "Location: " + location);
                    Log.d("Form Content", "Description: " + desc);
                    Log.d("Form Content", "Start Time: " + startTime);
                    Log.d("Form Content", "End Time: " + endTime);
                    Log.d("Form Content", "Category: " + category);

                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(location) || TextUtils.isEmpty(desc) ||
                            TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime) || TextUtils.isEmpty(category)) {
                        Toast.makeText(getContext(), "请输入完整信息", Toast.LENGTH_SHORT).show();
                    } else {
                        alertDialog.dismiss();
                    }
                }
            });
            alertDialog.show();
        });
        return view;
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}


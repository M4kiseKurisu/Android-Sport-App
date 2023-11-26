package com.example.myapplication.group;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupFindFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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

        Button detail = view.findViewById(R.id.group_find_more);
        detail.setOnClickListener(v -> {
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

                            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                                    (timerView, hourOfDay, minuteOfDay) -> {
                                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        c.set(Calendar.MINUTE, minuteOfDay);

                                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                                        String endTimeString = format.format(c.getTime());

                                        // 验证结束时间是否晚于开始时间
                                        try {
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                                            Date startTime = dateFormat.parse(startTimeText.getText().toString());
                                            Date endTime = dateFormat.parse(endTimeString);

                                            if (endTime.before(startTime)) {
                                                Toast.makeText(getContext(), "结束时间不能早于开始时间", Toast.LENGTH_SHORT).show();
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

            alertDialog.show();
        });
        return view;
    }
}
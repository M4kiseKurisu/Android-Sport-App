package com.example.myapplication.clockIn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.myapplication.R;

import java.util.Calendar;

public class ClockInCreateActivity extends AppCompatActivity {
    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;
    private Button startDateButton, endDateButton;
    private final Calendar calendar = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_in_create);

        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);

        radioGroup1.setOnCheckedChangeListener(groupOneListener);
        radioGroup2.setOnCheckedChangeListener(groupTwoListener);
        TextView textViewStart,textViewEnd;
        textViewStart = findViewById(R.id.textViewStart);
        textViewEnd = findViewById(R.id.textViewEnd);
        Button btnStart = findViewById(R.id.clockStart);
        Button btnEnd = findViewById(R.id.clockEnd);

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

        Button addSport = findViewById(R.id.clock_add_img);
        addSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private RadioGroup.OnCheckedChangeListener groupOneListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (radioGroup1.getCheckedRadioButtonId() != -1) {
                // 如果radioGroup1里有选中的RadioButton，清空radioGroup2的选中状态
                radioGroup2.setOnCheckedChangeListener(null);
                radioGroup2.clearCheck();
                radioGroup2.setOnCheckedChangeListener(groupTwoListener);
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
}

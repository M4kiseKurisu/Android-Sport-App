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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupMyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupMyFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public GroupMyFragment() {
        // Required empty public constructor
    }

    public static GroupMyFragment newInstance(String param1, String param2) {
        GroupMyFragment fragment = new GroupMyFragment();
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
        View view = inflater.inflate(R.layout.fragment_group_my, container, false);

        LinearLayout linearLayout = view.findViewById(R.id.group_my_container);
        for (int i = 0; i < 3; ++i) {
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
                LayoutInflater inflater1 = GroupMyFragment.this.getLayoutInflater();
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

            Button exitButton = new Button(requireContext());
            LinearLayout.LayoutParams joinButtonParams = new LinearLayout.LayoutParams(
                    0, dpToPx(30), 1
            );
            exitButton.setLayoutParams(joinButtonParams);
            exitButton.setBackgroundColor(Color.parseColor("#CA1C0F"));
            exitButton.setGravity(Gravity.CENTER);
            exitButton.setSingleLine(true);
            exitButton.setText("退出活动");
            exitButton.setTextColor(Color.WHITE);
            exitButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            exitButton.setTypeface(Typeface.DEFAULT_BOLD);
            exitButton.setPadding(0, 0, 0, 0);

            buttonsLayout.addView(detailsButton);
            buttonsLayout.addView(exitButton);

            innerLinearLayout.addView(titleTextView);
            innerLinearLayout.addView(dateTimeLayout);
            innerLinearLayout.addView(buttonsLayout);

            cardView.addView(innerLinearLayout);
            linearLayout.addView(cardView);
        }
        return view;
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
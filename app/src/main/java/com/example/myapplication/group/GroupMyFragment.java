package com.example.myapplication.group;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DataBaseHelper;
import com.example.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
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
        draw(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        View view = getView();
        assert view != null;
        draw(view);
    }

    // 根据数据库数据渲染页面
    private void draw(View view) {
        LinearLayout linearLayout = view.findViewById(R.id.group_my_container);
        linearLayout.removeAllViews();
        SQLiteDatabase db;
        try (DataBaseHelper dbHelper = new DataBaseHelper(getActivity(), "DataBase.db", null, 1)) {
            db = dbHelper.getReadableDatabase();
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginInfor", MODE_PRIVATE);
            int userId = sharedPreferences.getInt("UserID", -1);
            String query = "SELECT G.*, U.name " +
                    "FROM Groups G " +
                    "JOIN User U ON G.hostId = U.id " +
                    "JOIN UserActivity UA ON G.id = UA.activityId " +
                    "WHERE UA.userId = " + userId + " " +
                    "ORDER BY G.startTime DESC";

            Cursor cursor = db.rawQuery(query, null);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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

                Date currentTime = new Date(System.currentTimeMillis());
                try {
                    drawActivity(view, activityId, activityTitle, activityType, peopleNum, maxNum,
                            startTime, endTime, activityIntro, activityAdd, creatorName,
                            compare(dateFormat.parse(startTime), dateFormat.parse(endTime), currentTime));
                } catch (ParseException e) {
                    Log.d("something", "wrong");
                    throw new RuntimeException(e);
                }
            }
            cursor.close();
            db.close();
        }
    }

    // 渲染特定的组团信息
    private void drawActivity(View view, int activityId, String title, String activityType, int capacity, int maximum,
                              String startTime, String endTime, String intro, String address, String creatorName,
                              int state) {
        Log.d("test", title);
        LinearLayout linearLayout = view.findViewById(R.id.group_my_container);
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
            LayoutInflater inflater1 = GroupMyFragment.this.getLayoutInflater();
            View dialogView = inflater1.inflate(R.layout.dialog_group_activity_detail, null);
            TextView titleView = dialogView.findViewById(R.id.group_activity_title);
            titleView.setText(title);
            TextView category = dialogView.findViewById(R.id.group_activity_category);
            category.setText(activityType);
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

        Button otherButton = new Button(requireContext());
        LinearLayout.LayoutParams joinButtonParams = new LinearLayout.LayoutParams(
                0, dpToPx(30), 1
        );
        otherButton.setLayoutParams(joinButtonParams);
        if (state == 0) {
            otherButton.setBackgroundColor(Color.parseColor("#FF9800"));
            otherButton.setText("正在进行");
        } else if (state == 1) {
            otherButton.setBackgroundColor(Color.parseColor("#B8B7B7"));
            otherButton.setText("已经结束");
        } else if (state == 2) {
            otherButton.setBackgroundColor(Color.parseColor("#C60532"));
            otherButton.setText("退出活动");
            otherButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater1 = GroupMyFragment.this.getLayoutInflater();
                    View dialogView = inflater1.inflate(R.layout.dialog_confirm, null);
                    TextView titleView = dialogView.findViewById(R.id.confirm_title);
                    titleView.setText("确认是否退出活动：" + title);

                    dialogBuilder.setView(dialogView);
                    AlertDialog alertDialog = dialogBuilder.create();

                    Button button = dialogView.findViewById(R.id.confirm_yes_button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            exitActivity(activityId);
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
        }
        otherButton.setGravity(Gravity.CENTER);
        otherButton.setSingleLine(true);
        otherButton.setTextColor(Color.WHITE);
        otherButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        otherButton.setTypeface(Typeface.DEFAULT_BOLD);
        otherButton.setPadding(0, 0, 0, 0);
        buttonsLayout.addView(detailsButton);
        buttonsLayout.addView(otherButton);
        innerLinearLayout.addView(titleTextView);
        innerLinearLayout.addView(dateTimeLayout);
        innerLinearLayout.addView(buttonsLayout);
        cardView.addView(innerLinearLayout);
        linearLayout.addView(cardView);
    }

    private int compare(Date start, Date end, Date cur) {
        int comparison1 = cur.compareTo(start);
        int comparison2 = cur.compareTo(end);

        if (comparison1 < 0) {  // 未开始
            return 2;
        } else if (comparison2 > 0) {   // 已结束
            return 1;
        } else { // 正在进行
            return 0;
        }
    }

    private void exitActivity(int activityId) {
        SQLiteDatabase db;
        try (DataBaseHelper dbHelper = new DataBaseHelper(getActivity(), "DataBase.db", null, 1)) {
            db = dbHelper.getWritableDatabase();
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginInfor", MODE_PRIVATE);
            int userId = sharedPreferences.getInt("UserID", -1);

            String query = "SELECT hostId FROM Groups WHERE id = " + activityId;
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                int hostId = cursor.getInt(0);

                if (hostId == userId) {
                    // 如果活动是用户创建的，则删除活动
                    String deleteActivityQuery = "DELETE FROM Groups WHERE id = " + activityId;
                    db.execSQL(deleteActivityQuery);
                    Toast.makeText(getActivity(), "活动已删除", Toast.LENGTH_SHORT).show();
                } else {
                    String deleteUserActivityQuery = "DELETE FROM UserActivity WHERE userId = " + userId + " AND activityId = " + activityId;
                    db.execSQL("UPDATE Groups SET peopleNum = peopleNum - 1 WHERE id = ?", new String[]{String.valueOf(activityId)});
                    db.execSQL(deleteUserActivityQuery);
                    Toast.makeText(getActivity(), "成功退出活动", Toast.LENGTH_SHORT).show();
                }
            }
            cursor.close();
            db.close();
            draw(getView());
        }
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
package com.example.myapplication.group;

import android.app.AlertDialog;
import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

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
            View dialogView = inflater1.inflate(R.layout.group_find_detail, null);

            TextView title = dialogView.findViewById(R.id.title);
            title.setText("大运村 impart 活动");

            TextView description = dialogView.findViewById(R.id.description);
            description.setText("这是一场\"浩\"劫\n");

            Button closeButton = dialogView.findViewById(R.id.close_button);

            dialogBuilder.setView(dialogView);

            AlertDialog alertDialog = dialogBuilder.create();
            closeButton.setOnClickListener(v1 -> alertDialog.dismiss());

            alertDialog.show();
        });

        return view;
    }
}
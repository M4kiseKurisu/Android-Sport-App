package com.example.myapplication.clockIn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class ClockAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from
                (parent.getContext()).inflate(R.layout.clock_in_item, parent, false);
        return new ClockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView textView = (TextView) ((ClockViewHolder) holder).clockItem.getChildAt(0);
        textView.setText("成功发送新打卡");
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public static class ClockViewHolder extends RecyclerView.ViewHolder {

        LinearLayout clockItem;
        public ClockViewHolder(@NonNull View itemView) {
            super(itemView);
            clockItem = itemView.findViewById(R.id.clock_item);
        }
    }
}

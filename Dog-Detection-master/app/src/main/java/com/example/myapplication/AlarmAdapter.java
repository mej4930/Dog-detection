package com.example.myapplication;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlramViewHolder> {

    private ArrayList<Alarm> mList;

    public class AlramViewHolder extends RecyclerView.ViewHolder{
        protected TextView time;
        protected TextView status;

        public AlramViewHolder(View view) {
            super(view);
            this.time = view.findViewById(R.id.timeItem);
            this.status = view.findViewById(R.id.statusItem);
        }
    }

    public AlarmAdapter(ArrayList<Alarm> list){
        this.mList = list;
    }

    @Override
    public AlramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        AlramViewHolder viewHolder = new AlramViewHolder(view);

        return viewHolder;
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull AlramViewHolder holder, int position) {
        holder.time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        holder.status.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        holder.time.setGravity(Gravity.CENTER);
        holder.status.setGravity(Gravity.CENTER);

        holder.time.setText(mList.get(position).getTime());
        holder.status.setText(mList.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }
}

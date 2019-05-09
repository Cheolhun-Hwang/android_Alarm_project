package com.hooneys.alarmproject.ListPack;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hooneys.alarmproject.DataObject.MyAlarm;
import com.hooneys.alarmproject.R;

import java.util.ArrayList;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmHolder> {
    private ArrayList<MyAlarm> list;
    private OnClickItemViewListener listener;

    public AlarmAdapter(ArrayList<MyAlarm> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AlarmHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_alarm, viewGroup, false);
        return new AlarmHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmHolder holder, int i) {
        MyAlarm start = list.get(i*2);  // 0 2 4
        MyAlarm end = list.get(i*2+1);  // 1 3 5

        holder.start.setText(start.getAlarm_time());
        holder.end.setText(end.getAlarm_time());
    }

    @Override
    public int getItemCount() {
        return list.size()/2;
    }

    class AlarmHolder extends RecyclerView.ViewHolder{
        TextView start, end;

        public AlarmHolder(@NonNull final View itemView) {
            super(itemView);
            start = itemView.findViewById(R.id.item_alarm_start);
            end = itemView.findViewById(R.id.item_alarm_end);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int start_position = getAdapterPosition()*2;
                    int end_position = start_position+1;
                    if (listener != null &&
                            start_position != RecyclerView.NO_POSITION &&
                            end_position != RecyclerView.NO_POSITION){
                        listener.onClicked(itemView,
                                list.get(start_position),
                                list.get(end_position));
                    }
                }
            });
        }
    }

    public interface OnClickItemViewListener{
        void onClicked(View view, MyAlarm start, MyAlarm end);
    }

    public void setOnClickItemViewListener(OnClickItemViewListener listener){
        this.listener = listener;
    }

}

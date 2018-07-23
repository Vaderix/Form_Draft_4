package com.example.dell.form_draft_4;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NotWorking_Adapter extends ArrayAdapter<String> {

    private Context mContext;
    private int mResource;
    private int lastPosition=-1;

    static class ViewHolder {
        TextView nw_vehicleNumber;
        TextView nw_lastTime;
        TextView nw_lastAddress;
    }


    public NotWorking_Adapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        mContext= context;
        mResource= resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String veh_num = getItem(position);

        final View result;
        ViewHolder holder;

        if(convertView==null)
        {
            LayoutInflater testInflater = LayoutInflater.from(getContext());
            convertView = testInflater.inflate(mResource, parent, false);

            holder = new ViewHolder();
            holder.nw_vehicleNumber = (TextView) convertView.findViewById(R.id.nw_vehicleNumber);
            holder.nw_lastTime = (TextView) convertView.findViewById(R.id.nw_lastTime);
            holder.nw_lastAddress = (TextView) convertView.findViewById(R.id.nw_lastAddress);

            result=convertView;
            convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
            result=convertView;
        }
        Animation animation= AnimationUtils.loadAnimation(mContext,
                (position>lastPosition)?R.anim.load_down_anim:R.anim.load_up_anim);

        result.startAnimation(animation);
        lastPosition=position;

        holder.nw_vehicleNumber.setText(veh_num);
        //holder.nw_lastTime.setText("Test");
        //holder.nw_lastAddress.setText("Test 2");

        return (convertView);
    }
}

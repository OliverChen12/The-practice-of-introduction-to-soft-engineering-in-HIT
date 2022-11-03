package com.github.rosjava.android_remocons.rocon_remocon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rosjava.android_remocons.rocon_remocon.medicine_alert.DeskClockMainActivity;
import com.github.rosjava.android_remocons.rocon_remocon.motion_control.MotionControl;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by turtlebot on 18-3-24.
 */
public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.ViewHolder> {
    private List<FunctionItem> mFunctionList;



    public FunctionAdapter(List<FunctionItem> mFunctionList) {
        this.mFunctionList = mFunctionList;
    }

    //定义了内部类，构造函数里要传入View参数，它通常就是RecyclerView子项最外层的布局
    static class ViewHolder extends RecyclerView.ViewHolder {
        View functionView;
        ImageView functionImage;
        TextView functionName;

        public ViewHolder(View view) {
            super(view);
            functionView = view;
            functionImage = (ImageView) view.findViewById(R.id.function_image);
            functionName = (TextView) view.findViewById(R.id.function_name);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.function_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);


        holder.functionView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                FunctionItem functionItem = mFunctionList.get(position);

                Toast.makeText(view.getContext(), "you clicked view "+functionItem.getName(), Toast.LENGTH_SHORT).show();
                Context context = view.getContext();
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(context, MotionControl.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(context, DeskClockMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                    default:
                        break;
                }
                //Intent intent = new Intent(context, TestActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //context.startActivity(intent);

            }
        });
        holder.functionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                FunctionItem functionItem = mFunctionList.get(position);

                Toast.makeText(view.getContext(), "you clicked image "+functionItem.getName(), Toast.LENGTH_SHORT).show();
                Context context = view.getContext();
                Intent intent = new Intent(context, TestActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FunctionItem functionItem = mFunctionList.get(position);
        holder.functionImage.setImageResource(functionItem.getImageId());
        holder.functionName.setText(functionItem.getName());
    }

    @Override
    public int getItemCount() {
        return mFunctionList.size();
    }
}
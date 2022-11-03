package com.github.rosjava.android_remocons.rocon_remocon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    /** Notification ticker for the App */
    public static final String NOTIFICATION_TICKER = "ROS Control";
    /** Notification title for the App */
    public static final String NOTIFICATION_TITLE = "ROS Control";

    private List<FunctionItem> functionItemList =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFunctions(); //初始化功能数据
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.function_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FunctionAdapter adapter = new FunctionAdapter(functionItemList);
        recyclerView.setAdapter(adapter);

    }

    //initialize all function data
    private void initFunctions() {
        FunctionItem motionControl = new FunctionItem("移动控制", R.drawable.directional_arrow);
        functionItemList.add(motionControl);
        FunctionItem medicineAlert = new FunctionItem("服药提醒", R.drawable.directional_arrow);
        functionItemList.add(medicineAlert);

    }


}

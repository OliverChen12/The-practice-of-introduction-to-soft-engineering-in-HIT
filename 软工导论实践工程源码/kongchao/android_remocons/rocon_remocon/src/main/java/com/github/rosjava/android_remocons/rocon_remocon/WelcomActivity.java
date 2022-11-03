package com.github.rosjava.android_remocons.rocon_remocon;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wyj on 2018/5/30.
 */

public class WelcomActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //设置此界面为
        // 竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {
        TextView copyright = findViewById(R.id.copyright);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
            copyright.setText("Copyright © 2018 GSQ Team All rights reserved. ");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            //tv_version.setText("version");
        }
        //利用timer让此界面延迟3秒后跳转，timer有一个线程，该线程不断执行task
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //发送intent实现页面跳转，第一个参数为当前页面的context，第二个参数为要跳转的主页
                Intent intent = new Intent(WelcomActivity.this,Remocon.class);
                startActivity(intent);
                //跳转后关闭当前欢迎页面
                WelcomActivity.this.finish();
            }
        };
        //调度执行timerTask，第二个参数传入延迟时间（毫秒）
        timer.schedule(timerTask,3000);

    }
}

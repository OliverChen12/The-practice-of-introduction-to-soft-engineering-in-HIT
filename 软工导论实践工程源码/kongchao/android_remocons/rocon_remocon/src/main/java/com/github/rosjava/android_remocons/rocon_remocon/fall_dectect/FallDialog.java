package com.github.rosjava.android_remocons.rocon_remocon.fall_dectect;

/**
 * Created by wyj on 2018/5/7.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.rosjava.android_remocons.rocon_remocon.R;
import com.github.rosjava.android_remocons.rocon_remocon.Remocon;
import com.github.rosjava.android_remocons.rocon_remocon.WelcomActivity;

public class FallDialog extends AppCompatActivity {
    /*本类实现摔倒后弹出摔倒提示对话框的功能*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //生成一个对话框
        AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(this);
        alertdialogbuilder.setMessage("老人摔倒了");
        alertdialogbuilder.setPositiveButton("我知道了", click1);
        alertdialogbuilder.setNegativeButton("继续监测摔倒情况", click2);
        AlertDialog alertdialog1=alertdialogbuilder.create();
        alertdialog1.show();
        //setContentView(R.layout.fall_alert);
    }

    public void showdialog(View view)
    {
        //Toast.makeText(this,"clickme",Toast.LENGTH_LONG).show();

    }

    private DialogInterface.OnClickListener click1=new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface arg0,int arg1)
        {
            //响应点击返回摔倒检测的主界面
            Intent intent = new Intent(FallDialog.this,FallDetectedActivity.class);
            startActivity(intent);
            //android.os.Process.killProcess(android.os.Process.myPid());
        }
    };
    private DialogInterface.OnClickListener click2=new DialogInterface.OnClickListener()
    {
        //响应点击返回继续监听的界面
        @Override
        public void onClick(DialogInterface arg0,int arg1)
        {
            Intent intent = new Intent(FallDialog.this,NewListener.class);
            startActivity(intent);
            arg0.cancel();
        }
    };
}

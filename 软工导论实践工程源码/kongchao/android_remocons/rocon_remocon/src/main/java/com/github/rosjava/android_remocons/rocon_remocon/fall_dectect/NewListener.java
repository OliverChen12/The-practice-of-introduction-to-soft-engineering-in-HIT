package com.github.rosjava.android_remocons.rocon_remocon.fall_dectect;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.rosjava.android_remocons.common_tools.apps.RosAppActivity;
import com.github.rosjava.android_remocons.rocon_remocon.R;
import com.github.rosjava.android_remocons.rocon_remocon.Remocon;
import com.github.rosjava.android_remocons.rocon_remocon.WelcomActivity;

import org.ros.android.RosActivity;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.io.IOException;

/*本类实现了检测摔倒事件的发生*/
public class NewListener extends RosAppActivity
{
    private subscriber sc;
    boolean alertConfirm;

    public NewListener()
    {
        super("listener", "listener");
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        setDefaultMasterName(getString(R.string.default_robot));
        setDashboardResource(R.id.top_bar);
        setMainWindowResource(R.layout.listener);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor)
    {
        //super.init(nodeMainExecutor);
        try {
            //建立socket连接将机器人端与安卓端联系在一起，实现信息的沟通
            Thread.sleep(1000);
            java.net.Socket socket = new java.net.Socket(getMasterUri().getHost(), getMasterUri().getPort());
            java.net.InetAddress local_network_address = socket.getLocalAddress();
            socket.close();

            //声明一个新的subscriber对象
            sc = new subscriber();
            //配置网络
            NodeConfiguration nodeConfiguration =
                    NodeConfiguration.newPublic(local_network_address.getHostAddress(), getMasterUri());
            //处理sc的内容
            nodeMainExecutor.execute(sc, nodeConfiguration);
            Log.i("listener", "I heard msg from ubuntu123456 : \"" + sc.getAlertText() + "\"");
            alertConfirm = true;
            while(alertConfirm) {
                 //sc.getAlertText();
                Log.i("listener", "I heard msg from ubuntu : \"" + sc.getAlertText() + "\"");
                if (sc.getAlertText() != null) {
                    Log.e("listener", "收到了");
                    Intent intent = new Intent(NewListener.this,FallDialog.class);
                    startActivity(intent);
                    alertConfirm = false;
                }
            }
        } catch(InterruptedException e) {
            // Thread interruption
        } catch (IOException e) {
            // Socket problem
        }
    }
    private DialogInterface.OnClickListener click1=new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface arg0,int arg1)
        {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    };
    private DialogInterface.OnClickListener click2=new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface arg0,int arg1)
        {
            arg0.cancel();
        }
    };

    //重构父类函数
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(0,0,0,R.string.stop_app);
        return super.onCreateOptionsMenu(menu);
    }

    //重构父类函数
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case 0:
                finish();
                break;
        }
        return true;
    }
}

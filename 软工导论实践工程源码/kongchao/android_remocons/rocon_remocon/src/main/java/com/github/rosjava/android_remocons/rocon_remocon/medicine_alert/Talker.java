package com.github.rosjava.android_remocons.rocon_remocon.medicine_alert;


import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import org.ros.android.RosActivity;
import org.ros.android.view.RosTextView;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.io.IOException;


public class Talker extends RosActivity
{

    public static String RemindText = "欢迎使用空巢老人智能看护系统";
    public publisher mPublisher;

    public Talker()
    {
        super("Talker", "Talker");
        //测试本类是否被调用
        Log.e("Talker", "whatwrong?");

    }

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {

        //super.init(nodeMainExecutor);
        try {
            Thread.sleep(1000);
            //通过socket接口获取当前本地的网络地址
            java.net.Socket socket = new java.net.Socket(getMasterUri().getHost(), getMasterUri().getPort());
            java.net.InetAddress local_network_address = socket.getLocalAddress();
            socket.close();

            //新建一个publisher的实例用来发布语音提醒的内容
            mPublisher = new publisher(this.RemindText);
            //对节点进行配置
            NodeConfiguration nodeConfiguration =
                    NodeConfiguration.newPublic(local_network_address.getHostAddress(), getMasterUri());
            Log.e("Talker", "master uri [" + getMasterUri() + "]");
            //处理mPublisher节点的操作
            nodeMainExecutor.execute(mPublisher, nodeConfiguration);

        } catch(InterruptedException e) {  //对异常进行处理
            // Thread interruption
            Log.e("Talker", "sleep interrupted");
        } catch (IOException e) {
            // Socket problem
            Log.e("Talker", "socket error trying to get networking information from the master uri");
        }

    }
}




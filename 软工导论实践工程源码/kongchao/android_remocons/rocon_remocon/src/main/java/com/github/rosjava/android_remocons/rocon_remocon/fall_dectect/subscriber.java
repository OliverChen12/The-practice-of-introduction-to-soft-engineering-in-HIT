package com.github.rosjava.android_remocons.rocon_remocon.fall_dectect;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import org.ros.concurrent.CancellableLoop;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

public class subscriber extends AbstractNodeMain {
    /*本类实现了订阅摔倒检测信息的功能*/
    private static String TAG = "subscriber";

    public  String text = null;

    public subscriber() {
        //判断该类是否执行
        Log.i(TAG, " is running!");

    }
    //将提示的文本调出
    public String getAlertText(){
        return this.text;
    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("rosjava_tutorial_pubsub/listener");
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {
        //create subscriber

        //循环监听节点的信息
        connectedNode.executeCancellableLoop(new CancellableLoop() {
            @Override
            protected void setup() {
                /*Looper.prepare();
                Looper.loop();*/
            };

            @Override
            protected void loop() throws InterruptedException {
                long time = System.currentTimeMillis();
                if (time % 1000 == 0) {
                    //用于测试该节点运行了多长时间
                    Log.i(TAG, "ros_node run again after 1s");
                }
                Log.i(TAG, "I heard msg from ubuntu : \"" + text+ "\"");
            }
        });

    }
}
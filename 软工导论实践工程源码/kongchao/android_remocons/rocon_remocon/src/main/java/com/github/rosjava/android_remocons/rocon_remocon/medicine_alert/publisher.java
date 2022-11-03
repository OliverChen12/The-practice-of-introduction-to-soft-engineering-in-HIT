package com.github.rosjava.android_remocons.rocon_remocon.medicine_alert;


import android.util.Log;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

public class publisher extends AbstractNodeMain{

    private String remindText;
    public publisher(String text){
        remindText = text;
    }
    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("rosjava_tutorial_pubsub/talker");
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {


        //通过循环处理发布者的信息
        connectedNode.executeCancellableLoop(new CancellableLoop() {

            @Override
            protected void setup() {
                Log.i("talker","in");
            }

            int times = 0;

            //对loop函数进行重写，发布需要提醒事项的内容
            @Override
            public void loop() throws InterruptedException {


                if (times < 3) {
                    Log.i("talker","in");
                    Thread.sleep(250);
                }
                else {
                    //publisher.shutdown(20,TimeUnit.SECONDS);
                }
                times++;
            }
        });
    }
}
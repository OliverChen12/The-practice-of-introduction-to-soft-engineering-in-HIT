/*
 * Copyright (C) 2013 OSRF.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.rosjava.android_remocons.rocon_remocon.motion_control;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.github.rosjava.android_remocons.common_tools.apps.RosAppActivity;
import com.github.rosjava.android_remocons.rocon_remocon.R;

import org.ros.android.BitmapFromCompressedImage;
import org.ros.android.view.RosImageView;
import org.ros.android.view.VirtualJoystickView;
import org.ros.namespace.NameResolver;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.io.IOException;

/**
 * @author murase@jsk.imi.i.u-tokyo.ac.jp (Kazuto Murase)
 */
public class MotionControl extends RosAppActivity {
    private RosImageView<sensor_msgs.CompressedImage> cameraView;  //摄像头，相机视图
    private VirtualJoystickView virtualJoystickView;  //虚拟操纵杆视图
    private Button backButton;




    public MotionControl() {
        // The RosActivity constructor configures the notification title and ticker messages.
        super("android teleop", "android teleop");
        //this.ROBOT_INFO = ;
        //Log.e("MotionControl getUri", (String) ROBOT_INFO.getUri().getHost());
    }

    @SuppressWarnings("unchecked") //告诉编译器忽略指定的警告，不用在编译完成后出现警告信息。
    @Override
    public void onCreate(Bundle savedInstanceState) {

        setDashboardResource(R.id.top_bar);
        setMainWindowResource(R.layout.activity_motion_control);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_motion_control);

        //视频
        cameraView = (RosImageView<sensor_msgs.CompressedImage>) findViewById(R.id.motion_image);
        cameraView.setMessageType(sensor_msgs.CompressedImage._TYPE);
        cameraView.setMessageToBitmapCallable(new BitmapFromCompressedImage());
        //遥控杆
        virtualJoystickView = (VirtualJoystickView) findViewById(R.id.virtual_joystick);
        //返回按钮
        backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {

        super.init(nodeMainExecutor);

        try {
            //网络连接
            java.net.Socket socket = new java.net.Socket(getMasterUri().getHost(), getMasterUri().getPort());
            //java.net.Socket socket = new java.net.Socket(ROBOT_INFO.getUri().getHost(), ROBOT_INFO.getUri().getPort());
            java.net.InetAddress local_network_address = socket.getLocalAddress();
            socket.close();
            NodeConfiguration nodeConfiguration =
                    NodeConfiguration.newPublic(local_network_address.getHostAddress(), getMasterUri());

            //定义两个字符常量

            //Log.e("MCtest point-1", (String) ROBOT_INFO.getUri().getHost());

            String joyTopic = remaps.get(getString(R.string.joystick_topic));
            String camTopic = remaps.get(getString(R.string.camera_topic));

            NameResolver appNameSpace = getMasterNameSpace();
            joyTopic = appNameSpace.resolve(joyTopic).toString();
            camTopic = appNameSpace.resolve(camTopic).toString();

            //Log.e("MCtest point-3", (String) ROBOT_INFO.getUri().getHost());

            cameraView.setTopicName(camTopic);
            virtualJoystickView.setTopicName(joyTopic);

            //Log.e("MCtest point-2", (String) ROBOT_INFO.getUri().getHost());

            nodeMainExecutor.execute(cameraView, nodeConfiguration
                    .setNodeName("android/camera_view"));
            nodeMainExecutor.execute(virtualJoystickView,
                    nodeConfiguration.setNodeName("android/virtual_joystick"));

            //Log.e("MCtest point-4", (String) ROBOT_INFO.getUri().getHost());

        } catch (IOException e) {
            // Socket problem
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add(0,0,0,R.string.stop_app);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case 0:
                onDestroy();
                break;
        }
        return true;
    }
}
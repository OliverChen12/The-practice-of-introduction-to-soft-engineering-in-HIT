package com.github.rosjava.android_remocons .rocon_remocon.fall_dectect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.app.AlertDialog;

import com.github.rosjava.android_remocons.common_tools.apps.RosAppActivity;
import com.github.rosjava.android_remocons.rocon_remocon.R;

import org.ros.android.view.RosTextView;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.io.IOException;


public class listener extends RosAppActivity
{
    private Toast lastToast;
    private ConnectedNode node;
    private RosTextView<String> rosTextView;
    //private subscriber sb;

    public listener()
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
        //String chatterTopic = remaps.get(getString(R.string.chatter_topic));
        super.init(nodeMainExecutor);

        /*rosTextView = (RosTextView<std_msgs.String>) findViewById(R.id.text);
        rosTextView.setTopicName(getMasterNameSpace().resolve(chatterTopic).toString());
        rosTextView.setMessageType(std_msgs.String._TYPE);
        rosTextView.setMessageToStringCallable(new MessageCallable<String, std_msgs.String>() {
            @Override
            public java.lang.String call(std_msgs.String message) {
                Log.e("Listener", "received a message [" + message.getData() + "]");
                return message.getData();
            }
        });*/
        try {
            // Really horrible hack till I work out exactly the root cause and fix for
            // https://github.com/rosjava/android_remocons/issues/47
            Thread.sleep(1000);
            java.net.Socket socket = new java.net.Socket(getMasterUri().getHost(), getMasterUri().getPort());
            java.net.InetAddress local_network_address = socket.getLocalAddress();
            socket.close();
            Log.i("123", "I heard msg from ubuntu : \" \"");
           // sb = new subscriber();

            NodeConfiguration nodeConfiguration =
                    NodeConfiguration.newPublic(local_network_address.getHostAddress(), getMasterUri());
            //nodeMainExecutor.execute(sb, nodeConfiguration);

            /*boolean flag = true;
            while(flag){
                if(sb.getAlertText()==null) {
                    /*new AlertDialog.Builder(listener.this)
                            .setTitle("确认")
                            .setMessage("确定吗？")
                            .setPositiveButton("是", null)
                            .setNegativeButton("否", null)
                            .show();
                    flag = false;
                    Intent intent = new Intent(listener.this, NewListener.class);
                    startActivity(intent);
                }
            }*/


        } catch(InterruptedException e) {
            // Thread interruption
        } catch (IOException e) {
            // Socket problem
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(0,0,0,R.string.stop_app);
        return super.onCreateOptionsMenu(menu);
    }

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

//    /**
//     * Call Toast on UI thread.
//     * @param message Message to show on toast.
//     */
//    private void showToast(final String message)
//    {
//        runOnUiThread(new Runnable()
//        {
//            @Override
//            public void run() {
//                if (lastToast != null)
//                    lastToast.cancel();
//
//                lastToast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG);
//                lastToast.show();
//            }
//        });
//    }

}

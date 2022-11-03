package com.github.rosjava.android_remocons.rocon_remocon.send_inst;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rosjava.android_remocons.rocon_remocon.MasterChooser;
import com.github.rosjava.android_remocons.rocon_remocon.R;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SendInst extends AppCompatActivity {
    EditText textView = null;
    EditText ipText= null;

    //public String roscmd = "pwd";
    Button button = null;
    String port = "11312";
    String ip = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_inst);
        textView = (EditText)findViewById(R.id.text);
        button = (Button)findViewById(R.id.button);
        ipText = (EditText)findViewById(R.id.IPtext);

        //textView.setText(roscmd);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientTask clientTask = new ClientTask();
                //传入IP地址，端口号，以及要发送的内容
                clientTask.execute(ipText.getText().toString(),port,textView.getText().toString());
            }
        });

    }

    /*
    public void send(){
        ClientTask clientTask = new ClientTask();
        //传入IP地址，端口号，以及要发送的内容
        clientTask.execute(ipText.getText().toString(),port,textView.getText().toString());
    }
    public void sendClicked(View view){
        send();
    }
    */
    /*
    public void startSend(){
        ClientTask clientTask = new ClientTask();
        //传入IP地址，端口号，以及要发送的内容
        clientTask.execute(ipText.getText().toString(),port,textView.getText().toString());
    }
    */
    private class ClientTask extends AsyncTask<String,Void,String>
    {
        //ip = string[0] port = string[1] text_toSend = string[2]
        @Override
        protected String doInBackground(String... params) {
            try {
                Socket socket = new Socket(params[0],Integer.parseInt(params[1]));
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                printWriter.println(params[2]);
                printWriter.flush();    //记得刷新缓冲区
                socket.close();
            }catch (IOException e)
            {
                Log.d("network",e.getMessage());
            }
            return "OK";
        }
    }
}


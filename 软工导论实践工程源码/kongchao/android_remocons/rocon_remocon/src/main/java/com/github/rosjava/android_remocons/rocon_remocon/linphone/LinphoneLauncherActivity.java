/*
LinphoneLauncherActivity.java
Copyright (C) 2011  Belledonne Communications, Grenoble, France

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.github.rosjava.android_remocons.rocon_remocon.linphone;

import static android.content.Intent.ACTION_MAIN;

import com.github.rosjava.android_remocons.rocon_remocon.linphone.assistant.RemoteProvisioningActivity;
import org.linphone.mediastream.Version;
import com.github.rosjava.android_remocons.rocon_remocon.linphone.tutorials.TutorialLauncherActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.github.rosjava.android_remocons.rocon_remocon.R;
/**
 * 
 * Launch Linphone main activity when Service is ready.
 * 
 * @author Guillaume Beraudo
 *
 */
public class LinphoneLauncherActivity extends AppCompatActivity {

	private Handler mHandler;
	private ServiceWaitThread mThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Toast.makeText(LinphoneLauncherActivity.this, "onCreate", Toast.LENGTH_LONG).show();
		super.onCreate(savedInstanceState);
		
		// Hack to avoid to draw twice LinphoneActivity on tablets
        if (getResources().getBoolean(R.bool.orientation_portrait_only)) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		setContentView(R.layout.launch_screen);
        
		mHandler = new Handler();
		
		if (LinphoneService.isReady()) {
			onServiceReady();
			//Toast.makeText(LinphoneLauncherActivity.this, "Linphone Service  ready", Toast.LENGTH_LONG).show();
		} else {
			// start linphone as background  
			startService(new Intent(ACTION_MAIN).setClass(this, LinphoneService.class));
			mThread = new ServiceWaitThread();
			mThread.start();
			//Toast.makeText(LinphoneLauncherActivity.this, "Linphone Service not ready", Toast.LENGTH_LONG).show();
		}
	}

	protected void onServiceReady() {
		final Class<? extends Activity> classToStart;
		if (getResources().getBoolean(R.bool.show_tutorials_instead_of_app)) {
			classToStart = TutorialLauncherActivity.class;
		} else if (getResources().getBoolean(R.bool.display_sms_remote_provisioning_activity) && LinphonePreferences.instance().isFirstRemoteProvisioning()) {
			classToStart = RemoteProvisioningActivity.class;
		} else {
			classToStart = LinphoneActivity.class;
		}

		// We need LinphoneService to start bluetoothManager
		if (Version.sdkAboveOrEqual(Version.API11_HONEYCOMB_30)) {
			BluetoothManager.getInstance().initBluetooth();
		}
		
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent().setClass(LinphoneLauncherActivity.this, classToStart).setData(getIntent().getData()));
				finish();
			}
		}, 1000);
	}

	private class ServiceWaitThread extends Thread {
		public void run() {
			while (!LinphoneService.isReady()) {
				try {
					sleep(30);
				} catch (InterruptedException e) {
					throw new RuntimeException("waiting thread sleep() has been interrupted");
				}
			}
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					onServiceReady();
				}
			});
			mThread = null;
		}
	}
}



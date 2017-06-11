/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.androidthings.appward;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;

import com.androidthings.appward.sbmotors.SBMotors;
import com.androidthings.appward.sensors.UltrasoundSensor;

import java.io.IOException;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    SBMotors mSBMotors;
    private Handler mHandler = new Handler();
    private static final int INTERVAL_BETWEEN_STEPS_MS = 1000;

    UltrasoundSensor mUltrasoundSensor;
    private boolean mQuitRobot = false;

    private boolean mTurnFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        //BCM12, BCM13, BCM16, BCM17, BCM18, BCM19, BCM20, BCM21, BCM22, BCM23, BCM24, BCM25, BCM26, BCM27, BCM4, BCM5, BCM6

        mSBMotors = new SBMotors();

        try {
            mSBMotors.initializeTwoMotors(0);
            Log.d(TAG, "Ready for keyboard");
            //mHandler.post(mRunnable); //
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }


        try {
            mUltrasoundSensor = new UltrasoundSensor(new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if(!mQuitRobot) {
                        runAutoPilot(mUltrasoundSensor.getDistance());
                    }
                }
            });

            mUltrasoundSensor.register();

            Log.d(TAG, "Ready for UltrasoundSensor");

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Log.i("onKeyDown", "keyCode"+keyCode+ "KeyEvent"+event );

        try{
            switch (event.getKeyCode())
            {
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    mSBMotors.reverse(50);
                    break;

                case KeyEvent.KEYCODE_DPAD_UP:
                    mSBMotors.forward(50);

                    break;

                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    mSBMotors.right(50);
                    break;

                case KeyEvent.KEYCODE_DPAD_LEFT:
                    mSBMotors.left(50);

                    break;

                case KeyEvent.KEYCODE_B:
                    mSBMotors.stop(50);
                    break;

                case KeyEvent.KEYCODE_A:
                    mQuitRobot = false;
                    break;
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return super.onKeyDown(keyCode, event);
    }



    public void  runAutoPilot(double distance)
    {
        Log.d("AutoPilot", ""+distance);
        if( distance <= 20 )
        {
            try {

                mSBMotors.stop(50);

                mSBMotors.reverse(50);

                Thread.sleep(1500);

                if(mTurnFlag)
                {
                    mSBMotors.right(50)
                }
                else
                {
                    mSBMotors.left(50);
                }
                mTurnFlag = !mTurnFlag;

                Thread.sleep(1000);

                mSBMotors.forward(50);

            }catch ( Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        Log.i("onKeyUp", "keyCode"+keyCode+ "KeyEvent"+event );

        if( event.getKeyCode() == KeyEvent.KEYCODE_A)
        {
            return super.onKeyUp(keyCode, event);
        }

        try
        {
            mQuitRobot = true;
            mSBMotors.stop(50);
        }catch (Exception ex)
        {

        }

        return super.onKeyUp(keyCode, event);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        try {
            mSBMotors.deInitializeTwoMotors();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        try {
            mUltrasoundSensor.unregister();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }


    }


}

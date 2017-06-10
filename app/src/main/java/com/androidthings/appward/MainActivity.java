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
import android.util.Log;

import com.androidthings.appward.sbmotors.SBMotors;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;
import java.util.List;

/**
 * Skeleton of the main Android Things activity. Implement your device's logic
 * in this class.
 *
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 *
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 */
public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    SBMotors mSBMotors;
    private Handler mHandler = new Handler();
    private static final int INTERVAL_BETWEEN_STEPS_MS = 1000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        //BCM12, BCM13, BCM16, BCM17, BCM18, BCM19, BCM20, BCM21, BCM22, BCM23, BCM24, BCM25, BCM26, BCM27, BCM4, BCM5, BCM6

        mSBMotors = new SBMotors();

        try {
            mSBMotors.initializeTwoMotors(0);
            mHandler.post(mRunnable);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
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
    }

    int mTimes = 0;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try{
                mSBMotors.forward(50);
                if( mTimes < 100 ) {
                    mHandler.postDelayed(this, INTERVAL_BETWEEN_STEPS_MS);
                }
            } catch (IOException e) {
                Log.e(TAG, "Error on PeripheralIO API", e);
            }
            mTimes++;
        }
    };


}

package com.androidthings.appward.sensors;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.androidthings.appward.sbmotors.RPiPinMap;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;
import java.util.Observable;

//Source of code
//https://github.com/Danesz/AndroidThings-UltrasonicSensor/blob/master/app/src/main/java/com/danieldallos/androidthings/ultrasonicsensor/MainActivityNoCallback.java

//Changed by Imran Baig

public class UltrasoundSensor extends Observable {


    private static  String ECHO_PIN_NAME;
    private static  String TRIGGER_PIN_NAME;

    static {
        ECHO_PIN_NAME = RPiPinMap.getPinName(31);
        TRIGGER_PIN_NAME = RPiPinMap.getPinName(29);
    }


    private static final String TAG = "UltrasoundSensor";

    private Gpio mEcho;
    private Gpio mTrigger;

    final Handler mHandler;

    public UltrasoundSensor(Handler handler) {
        mHandler = handler;
    }

    private long mTime1, mTime2;
    private int mKeepBusy;
    private double mDistance = 0;


    public double getDistance() {
        return mDistance;
    }

    public void register() throws IOException {

        //Initialize PeripheralManagerService
        PeripheralManagerService service = new PeripheralManagerService();

        //List all available GPIOs
        Log.d(TAG, "Available GPIOs: " + service.getGpioList());

        try {
            // Create GPIO connection.
            mEcho = service.openGpio(ECHO_PIN_NAME);
            // Configure as an input.
            mEcho.setDirection(Gpio.DIRECTION_IN);
            // Enable edge trigger events.
            mEcho.setEdgeTriggerType(Gpio.EDGE_BOTH);
            // Set Active type to HIGH, then the HIGH events will be considered as TRUE
            mEcho.setActiveType(Gpio.ACTIVE_HIGH);

        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
            throw e;
        }

        try {
            // Create GPIO connection.
            mTrigger = service.openGpio(TRIGGER_PIN_NAME);

            // Configure as an output with default LOW (false) value.
            mTrigger.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
            throw e;
        }

       new Thread(){
            @Override
            public void run() {
                try {
                    while (true) {
                        readDistanceSync();
                        Thread.sleep(300);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        //ultrasonicTriggerHandler.post(triggerRunnable);

    }


    protected void readDistanceSync() throws IOException, InterruptedException {
        // Just to be sure, set the trigger first to false
        mTrigger.setValue(false);
        Thread.sleep(0, 2000);

        // Hold the trigger pin HIGH for at least 10 us
        mTrigger.setValue(true);
        Thread.sleep(0, 10000); //10 microsec

        // Reset the trigger pin
        mTrigger.setValue(false);

        // Wait for pulse on echo pin
        while (mEcho.getValue() == false) {
            //long t1 = System.nanoTime();
            //Log.d(TAG, "Echo has not arrived...");

            // keep the while loop busy
            mKeepBusy = 0;

            //long t2 = System.nanoTime();
            //Log.d(TAG, "diff 1: " + (t2-t1));
        }
        mTime1 = System.nanoTime();
        Log.i(TAG, "Echo ARRIVED!");

        // Wait for the end of the pulse on the ECHO pin
        while (mEcho.getValue() == true) {
            //long t1 = System.nanoTime();
            //Log.d(TAG, "Echo is still coming...");

            // keep the while loop busy
            mKeepBusy = 1;

            //long t2 = System.nanoTime();
            //Log.d(TAG, "diff 2: " + (t2-t1));
        }
        mTime2 = System.nanoTime();
        Log.i(TAG, "Echo ENDED!");

        // Measure how long the echo pin was held high (pulse width)
        long pulseWidth = mTime2 - mTime1;

        // Calculate distance in centimeters. The constants
        // are coming from the datasheet, and calculated from the assumed speed
        // of sound in air at sea level (~340 m/s).
        mDistance = (pulseWidth / 1000.0) / 58.23; //cm

        // or we could calculate it with the speed of the sound:
        //double distance = (pulseWidth / 1000000000.0) * 340.0 / 2.0 * 100.0;

        Log.i(TAG, "distance: " + mDistance + " cm");

        if (mHandler != null) {
            //mHandler.removeMessages(0);
            mHandler.sendEmptyMessage(0);
        }
    }

    public void unregister() throws IOException{

        if (mEcho != null) {
            try {
                mEcho.close();
            } catch (IOException e) {
                Log.e(TAG, "Error on PeripheralIO API", e);
                throw e;
            }
        }

        // Close the resource
        if (mTrigger != null) {
            try {
                mTrigger.close();
            } catch (IOException e) {
                Log.e(TAG, "Error on PeripheralIO API", e);
                throw e;
            }
        }
    }
}

package com.androidthings.appward.sbmotors;

import android.util.Log;

import com.androidthings.appward.MainActivity;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;
import com.google.android.things.pio.Pwm;

import java.io.IOException;
import java.util.List;

/**
 * Created by imranbaig on 06/06/2017.
 */

public class Motor {

    private static final String TAG = MainActivity.class.getSimpleName();

    final Config [] mConfigs;
    final Arrow mArrow;
    PeripheralManagerService mManager = new PeripheralManagerService();

    Gpio mSpeedGPIO;
    Gpio mGpioForward;
    Gpio mGpioReverse;
    Gpio mArrowPin;


    //Pwm mSpeedPWM;


    public Motor(Config config1, Config config2, Arrow arrow) {
        mConfigs = new Config[2];
        mConfigs[0] = config1;
        mConfigs[1] = config2;
        mArrow = arrow;
    }

    public Config[] getConfigs() {
        return mConfigs;
    }

    public void initialize(int configPos) throws IOException
    {

        try {


            Log.i(TAG, "List pwm" + mManager.getPwmList());


            Config config = mConfigs[configPos];



            mSpeedGPIO = mManager.openGpio(config.getPwmPinName());
            mGpioForward = mManager.openGpio(config.getForwardPinName());
            mGpioReverse = mManager.openGpio(config.getReversePinName());

            mArrowPin = mManager.openGpio(mArrow.getPinName());



            /*mSpeedPWM = mManager.openPwm(*//*config.getPwmPinName()*//*"PWM1");
            mSpeedPWM.setPwmFrequencyHz(50);
            mSpeedPWM.setPwmDutyCycle(0);*/

            configureOutput(mSpeedGPIO, 1);
            configureOutput(mGpioForward, 0);
            configureOutput(mGpioReverse, 0);
            configureOutput(mArrowPin, 0);
        }
        catch (Exception e)
        {
            Log.w(TAG, "Unable to access GPIOs", e);
            deInitialize();
            throw e;
        }finally {

        }
    }

    public void forward(int speed) throws IOException
    {
        try {
            mArrowPin.setValue(!mArrowPin.getValue());
            mGpioForward.setValue(true);
            //mSpeedPWM.setPwmDutyCycle(speed);
            mGpioReverse.setValue(false);
        }catch (Exception e)
        {
            Log.w(TAG, "Unable to go forward", e);
            deInitialize();
            throw e;
        }finally {

        }

    }

    public void reverse(int speed) throws IOException
    {

        try {
            //mSpeedPWM.setPwmDutyCycle(speed);
            mGpioForward.setValue(false);
            mGpioReverse.setValue(true);
        }
        catch (Exception e)
        {
            Log.w(TAG, "Unable to go reverse", e);
            deInitialize();
            throw e;
        }finally {

        }

    }

    public void stop() throws IOException
    {

        try {
            //mSpeedPWM.setPwmDutyCycle(0);
            mGpioForward.setValue(false);
            mGpioReverse.setValue(false);
        }
        catch (Exception e)
        {
            Log.w(TAG, "Unable to stop", e);
            deInitialize();
            throw e;
        }finally {

        }

    }

    public void configureOutput(Gpio gpio, int active) throws IOException {


        try {
            // Initialize the pin as a high output
            gpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_HIGH);
            // Low voltage is considered active
            gpio.setActiveType(Gpio.ACTIVE_HIGH);

            // Toggle the value to be LOW
            gpio.setValue(active==1);
        }
        catch (Exception e)
        {
            Log.w(TAG, "Unable to configureOutput", e);
            throw e;
        }
    }

    void closeGpio (Gpio gpio)
    {
        if (gpio != null) {
            try {
                gpio.close();
            } catch (IOException e) {
                Log.w(TAG, "Unable to close GPIO",  e);
            }
        }
    }

    public void deInitialize()
    {
        closeGpio(mSpeedGPIO);
        mSpeedGPIO = null;
        closeGpio(mGpioForward);
        mGpioForward = null;
        closeGpio(mGpioReverse);
        mGpioReverse = null;

        closeGpio(mArrowPin);
        mArrowPin = null;
    }
}

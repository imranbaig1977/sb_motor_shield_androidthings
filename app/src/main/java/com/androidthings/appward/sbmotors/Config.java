package com.androidthings.appward.sbmotors;

/**
 * Created by imranbaig on 06/06/2017.
 */

public class Config {

    final int mForwardPin;
    final int mReversePin;
    final int mPwmPin;

    public Config(int pwmPin , int forwardPin, int reversePin) {
        this.mForwardPin = forwardPin;
        this.mReversePin = reversePin;
        this.mPwmPin = pwmPin;
    }

    public int getForwardPin() {
        return mForwardPin;
    }

    public int getReversePin() {
        return mReversePin;
    }

    public int getPwmPin() {
        return mPwmPin;
    }


    public String getForwardPinName() {
        return RPiPinMap.getPinName(mForwardPin);
    }

    public String getReversePinName() {
        return  RPiPinMap.getPinName(mReversePin);
    }

    public String getPwmPinName() {
        return RPiPinMap.getPinName(mPwmPin);
    }
}

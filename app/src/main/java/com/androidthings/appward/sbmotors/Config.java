package com.androidthings.appward.sbmotors;

/**
 * Created by imranbaig on 06/06/2017.
 */

public class Config {

    final int forwardPin;
    final int reversePin;
    final int pwmPin;

    public Config(int pwmPin , int forwardPin, int reversePin) {
        this.forwardPin = forwardPin;
        this.reversePin = reversePin;
        this.pwmPin = pwmPin;
    }

    public int getForwardPin() {
        return forwardPin;
    }

    public int getReversePin() {
        return reversePin;
    }

    public int getPwmPin() {
        return pwmPin;
    }


    public String getForwardPinName() {
        return RPiPinMap.getPinName(forwardPin);
    }

    public String getReversePinName() {
        return  RPiPinMap.getPinName(reversePin);
    }

    public String getPwmPinName() {
        return RPiPinMap.getPinName(pwmPin);
    }
}

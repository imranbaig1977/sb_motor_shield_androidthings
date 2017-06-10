package com.androidthings.appward.sbmotors;

/**
 * Created by imranbaig on 06/06/2017.
 */

public class Arrow {

    final int mPinNumber;


    public Arrow(int pinNumber) {
        this.mPinNumber = pinNumber;
    }

    public String getPinName() {
       return RPiPinMap.getPinName(this.mPinNumber);
    }
}

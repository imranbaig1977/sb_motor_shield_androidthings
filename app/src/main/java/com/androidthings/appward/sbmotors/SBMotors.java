package com.androidthings.appward.sbmotors;

import com.androidthings.appward.sbmotors.Motor;

import java.io.IOException;

/**
 * Created by imranbaig on 06/06/2017.
 */

public class SBMotors {
    final Motor[] motors;

    public SBMotors() {
        this.motors = new Motor[4];

        motors[0] = new Motor(new Config(11,15,13), new Config(11, 13, 15), new Arrow(36));
        motors[1] = new Motor(new Config(22,16,18), new Config(22, 18, 16), new Arrow(37));
        motors[2] = new Motor(new Config(19,21,23), new Config(19, 23, 21), new Arrow(35));
        motors[3] = new Motor(new Config(32,24,26), new Config(32, 26, 24), new Arrow(33));
    }

    public void initialize(int motorIndex  /*0 to 3*/, int configIndex /*0 to 1*/ ) throws IOException
    {
        motors[motorIndex].initialize(configIndex);
    }

    public void initializeTwoMotors(int configIndex /*0 to 1*/ ) throws IOException
    {
        motors[0].initialize(configIndex);
        motors[1].initialize(configIndex);

    }

    public void deInitializeTwoMotors() throws IOException
    {
        motors[0].deInitialize();
        motors[1].deInitialize();
    }

    public void deInitialize(int motorIndex  /*0 to 3*/) throws IOException
    {
        motors[motorIndex].deInitialize();
    }

    public Motor getMotor(int index /*0 to 3*/){
        return motors[index];
    }

    public Motor getMotor1(){
        return motors[0];
    }
    public Motor getMotor2(){
        return motors[1];
    }
    public Motor getMotor3(){
        return motors[2];
    }
    public Motor getMotor4(){
        return motors[3];
    }

    public void forward(int speed) throws IOException
    {
        getMotor1().forward(speed);
        getMotor2().forward(speed);

    }

    public void reverse(int speed) throws IOException
    {
        getMotor1().reverse(speed);
        getMotor2().reverse(speed);
    }

    public void stop(int speed) throws IOException
    {
        getMotor1().stop();
        getMotor2().stop();
    }
}

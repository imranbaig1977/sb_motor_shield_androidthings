package com.androidthings.appward.sbmotors;

/**
 * Created by imranbaig on 06/06/2017.
 */

public class RPiPinMap {

    private static String[] pinMap;
     static
     {
        pinMap = new String[41];
        pinMap[0] = "";
        pinMap[1] = "";
        pinMap[2] = "";
        pinMap[3] = "BCM2";
        pinMap[4] = "";
        pinMap[5] = "BCM3";
        pinMap[6] = "";
        pinMap[7] = "BCM4";
        pinMap[8] = "BCM14";
        pinMap[9] = "";
        pinMap[10] = "BCM15";
        pinMap[11] = "BCM17";
        pinMap[12] = "BCM18";
        pinMap[13] = "BCM27";
        pinMap[14] = "";
        pinMap[15] = "BCM22";
        pinMap[16] = "BCM23";
        pinMap[17] = "";
        pinMap[18] = "BCM24";
        pinMap[19] = "BCM10";
        pinMap[20] = "";
        pinMap[21] = "BCM9";
        pinMap[22] = "BCM25";
        pinMap[23] = "BCM11";
        pinMap[24] = "BCM8";
        pinMap[25] = "";
        pinMap[26] = "BCM7";
        pinMap[27] = "BCM0";
        pinMap[28] = "BCM1";
        pinMap[29] = "BCM5";
        pinMap[30] = "";
        pinMap[31] = "";
        pinMap[32] = "BCM12";
        pinMap[33] = "BCM13";
        pinMap[34] = "";
        pinMap[35] = "BCM19";
        pinMap[36] = "BCM16";
        pinMap[37] = "BCM26";
        pinMap[38] = "BCM20";
        pinMap[39] = "";
        pinMap[40] = "BCM21";
    }


    public static String getPinName(int position /* 1 to 40*/)
    {
        return pinMap[position];
    }

}

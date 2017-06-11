package com.androidthings.appward.sbmotors;

/**
 * Created by imranbaig on 06/06/2017.
 */

/**
 * This class maps GPIO Pin numbering to BCM
 *
 * */

public class RPiPinMap {



    private static String[] sPinMap;
     static
     {
        sPinMap = new String[41];
        sPinMap[0] = "";
        sPinMap[1] = "";
        sPinMap[2] = "";
        sPinMap[3] = "BCM2";
        sPinMap[4] = "";
        sPinMap[5] = "BCM3";
        sPinMap[6] = "";
        sPinMap[7] = "BCM4";
        sPinMap[8] = "BCM14";
        sPinMap[9] = "";
        sPinMap[10] = "BCM15";
        sPinMap[11] = "BCM17";
        sPinMap[12] = "BCM18";
        sPinMap[13] = "BCM27";
        sPinMap[14] = "";
        sPinMap[15] = "BCM22";
        sPinMap[16] = "BCM23";
        sPinMap[17] = "";
        sPinMap[18] = "BCM24";
        sPinMap[19] = "BCM10";
        sPinMap[20] = "";
        sPinMap[21] = "BCM9";
        sPinMap[22] = "BCM25";
        sPinMap[23] = "BCM11";
        sPinMap[24] = "BCM8";
        sPinMap[25] = "";
        sPinMap[26] = "BCM7";
        sPinMap[27] = "BCM0";
        sPinMap[28] = "BCM1";
        sPinMap[29] = "BCM5";
        sPinMap[30] = "";
        sPinMap[31] = "BCM6";
        sPinMap[32] = "BCM12";
        sPinMap[33] = "BCM13";
        sPinMap[34] = "";
        sPinMap[35] = "BCM19";
        sPinMap[36] = "BCM16";
        sPinMap[37] = "BCM26";
        sPinMap[38] = "BCM20";
        sPinMap[39] = "";
        sPinMap[40] = "BCM21";
    }


    public static String getPinName(int position /* 1 to 40*/)
    {
        return sPinMap[position];
    }

}

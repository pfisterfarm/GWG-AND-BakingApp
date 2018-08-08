package com.pfisterfarm.bakingapp.utils;

public class helpers {

    public static String displayAmount(Double howMuch, String whatUnits) {
        String newHowMuch;
        String newUnits;

        newHowMuch = formatDouble(howMuch);
        newUnits = whatUnits;

        switch (whatUnits) {
            case "CUP":
                newUnits = "cup";
                if (howMuch > 1) {
                    newUnits += "s";
                }
                break;
            case "TBLSP":
                newUnits = "tbsp";
                break;
            case "TSP":
                newUnits = "tsp";
                break;
            case "K":
                newUnits = "kg";
                break;
            case "G":
                newUnits = "g";
                break;
            case "OZ":
                newUnits = "oz";
                break;
            case "UNIT":
                newUnits = "";
                break;
        }
        if (newUnits != "") {
            return newHowMuch + " " + newUnits + " ";
        } else {
            return newHowMuch + " ";
        }
    }

    public static String formatDouble(double d)
            // convert Double to String... omit .0 at end
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }
}

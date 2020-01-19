package com.mai.bombdefuse;

public class Settings {
    private static int[] key = new int[] {7,3,5};
    private static int codeLength = 3;
    private static int timerCounter = 60;

    public static String getKeyAsString() {
        return Coder.getInstance().codeToString(key);
    }

    public static int[] getKey () {
        return key;
    }

    public static int getCodeLength() {
        return codeLength;
    }

    public static int getTimerCounter() {
        return timerCounter;
    }
}

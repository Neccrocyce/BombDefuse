package com.mai.bombdefuse;

public class Settings {
    private static int[] key = new int[] {7,3,5,5,6,0};
    private static int codeLength = 7;
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

    public static void setKey(int[] key) {
        Settings.key = key;
    }
}

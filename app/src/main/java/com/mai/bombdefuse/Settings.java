package com.mai.bombdefuse;

public class Settings {
    private static int[] key = new int[] {7,3,5,5,6,0,8};
    private static int codeLength = 7;
    private static int timerDuration = 60;
    private static boolean sound = true;
    private static boolean soundExplosion = true;

    public static String getKeyAsString() {
        return Coder.getInstance().codeToString(key);
    }

    public static int[] getKey () {
        return key;
    }

    public static int getCodeLength() {
        return codeLength;
    }

    public static int getTimerDuration() {
        return timerDuration;
    }

    public static boolean isSound() {
        return sound;
    }

    public static boolean isSoundExplosion() {
        return soundExplosion;
    }

    public static void setKey (int[] key) {
        Settings.key = key;
    }
}

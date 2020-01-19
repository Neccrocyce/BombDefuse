package com.mai.bombdefuse;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

public class Timer2 implements Runnable {
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void run() {
        int time = Settings.getTimerCounter() * 1000;

        while (time > 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                break;
            }
            time = time - 10;
            final String finalTime = formatTime(time);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //BombActive.getTvTime().setText(finalTime);
                }
            });
        }

        if (time == 0) {
            handler.post(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }

    private String formatTime (int time) {
        int minutes = time / 60000;
        int seconds = (time - minutes * 60000) / 1000;
        int hundreths = (time % 1000) / 10;
        return (minutes < 10 ? "0" : "") + minutes + ":" + (seconds < 10 ? "0" : "") + seconds + ":" + (hundreths < 10 ? "0" : "") + hundreths;
    }
}

package com.mai.bombdefuse;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class TimerService extends Service {
    private Thread worker;

    private BombActive bombActive;

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        worker = new Thread(new Timer());
        worker.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy () {
        worker.interrupt();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public class Timer implements Runnable {
        Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void run() {
            int time = Settings.getTimerDuration() * 1000;
            setTvTime(formatTime(time));

            while (time > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
                time = time - 1000;
                setTvTime(formatTime(time));
            }

            if (time == 0) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }

        private void setTvTime (final String time) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    intent.setAction("com.mai.bombdefuse.TIMER");
                    intent.putExtra("time", time);
                    LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);
                }
            });

        }

        private String formatTime(int time) {
            int minutes = time / 60000;
            int seconds = (time - minutes * 60000) / 1000;
            int hundreths = (time % 1000) / 10;
            return (minutes < 10 ? "0" : "") + minutes + ":" + (seconds < 10 ? "0" : "") + seconds;
        }
    }
}

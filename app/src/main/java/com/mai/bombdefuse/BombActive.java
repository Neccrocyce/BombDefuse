package com.mai.bombdefuse;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class BombActive extends AppCompatActivity{
    private int[] code;
    private char[] codeDefuse = new char[Settings.getCodeLength()*2-1];
    private int indexCode = 0;

    private TextView tvTime;

    BroadcastReceiver br;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bomb_active);

        //receive code
        Intent receivedIntent = getIntent();
        if (receivedIntent != null && receivedIntent.hasExtra("code")) {
            code = receivedIntent.getIntArrayExtra("code");
        }
        else {
            Log.e(MainActivity.class.getSimpleName(), "Received no code; Set code to 7355608");
            code = new int[] {7,3,5,5,6,0,8};
        }

        //encode code
        int[] codeCrypted = Coder.getInstance().encode(code);

        ImageView qr_code = (ImageView) findViewById(R.id.ba_qr_code);
        qr_code.setImageBitmap(QRCodeGenerator.getInstance().generate(Coder.getInstance().codeToString(codeCrypted)));

        TextView qr_code_txt = (TextView) findViewById(R.id.ba_qr_code_txt);
        qr_code_txt.setText(Coder.getInstance().codeToCharArray(codeCrypted), 0, codeCrypted.length);

        //initialize enter code line
        flushCodeEntered();

        TextView tvCode = (TextView) findViewById(R.id.ba_txt_code_enter);
        tvCode.setText(codeDefuse, 0, codeDefuse.length);

        //set timer
        tvTime = (TextView) findViewById(R.id.ba_txt_timer);
        startService(new Intent(getBaseContext(), TimerService.class));

        //display user info
        Toast.makeText(getApplicationContext(), "Bomb has been activated", Toast.LENGTH_LONG).show();
    }

    public void timeExceeded () {
        stopService(new Intent (getBaseContext(), TimerService.class));
        Intent intent = new Intent(this, BombDetonated.class);
        intent.putExtra("time", tvTime.getText());
        startActivity(intent);
    }

    public void enterCode (View view) {
        char key = view.getContentDescription().charAt(0);
        if (key == 'E') {
            if (indexCode > codeDefuse.length && isCode()) {
                stopService(new Intent (getBaseContext(), TimerService.class));
                Intent intent = new Intent(this, BombDefused.class);
                intent.putExtra("time", tvTime.getText());
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "Wrong Code", Toast.LENGTH_SHORT).show();
                flushCodeEntered();
            }
        }
        else if (key == 'B') {
            if (indexCode > 1) {
                indexCode = indexCode - 2;
                codeDefuse[indexCode] = '_';
            }
        }
        else {
            if (indexCode < codeDefuse.length) {
                codeDefuse[indexCode] = key;
                indexCode = indexCode + 2;
            }
        }

        if (indexCode > -1) {
            TextView tvCode = (TextView) findViewById(R.id.ba_txt_code_enter);
            tvCode.setText(codeDefuse, 0, codeDefuse.length);
        }
    }

    private void flushCodeEntered () {
        for (int i = 0; i < codeDefuse.length; i = i+2) {
            codeDefuse[i] = '_';
        }

        for (int i = 1; i < codeDefuse.length; i = i+2) {
            codeDefuse[i] = ' ';
        }
        indexCode = 0;
    }

    private int[] getCode () {
        int[] codeNew = new int[Settings.getCodeLength()];
        for (int i = 0; i < codeDefuse.length; i = i+2) {
            codeNew[i/2] = Integer.parseInt("" + codeDefuse[i]);
        }
        return codeNew;
    }

    private boolean isCode () {
        int[] codeDefuseInt = getCode();
        boolean isCode = true;
        for (int i = 0; i < code.length; i++) {
            isCode &= codeDefuseInt[i] == code[i];
        }
        return isCode;
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onResume() {
        super.onResume();
        br = new TimeReceiver();
        final IntentFilter intentFilter = new IntentFilter("com.mai.bombdefuse.TIMER");
        LocalBroadcastManager.getInstance(this).registerReceiver(br, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (br != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(br);
        }
        br = null;
    }

    public class TimeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.hasExtra("time")) {
                 String time = intent.getStringExtra("time");
                 tvTime.setText(time);

                 if (time.equals("00:00")) {
                     timeExceeded();
                 }
            }
        }
    }



}

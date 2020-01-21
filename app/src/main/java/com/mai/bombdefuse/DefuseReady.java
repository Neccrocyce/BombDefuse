package com.mai.bombdefuse;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DefuseReady extends AppCompatActivity {
    Dialpad dialpad;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defuse_ready);

        dialpad = new Dialpad(Settings.getCodeLength());
    }

    public void enterCode (View view) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.c4_click);
        if (Settings.isSound()) {
            mp.start();
        }

        if (dialpad.isEnterKey(view)) {
            if (dialpad.reachedEndOfLine()) {
                Intent intent = new Intent(this, DefuseActive.class);
                intent.putExtra("code", dialpad.getCodeAsIntArray());
                startActivity(intent);
            }
        }
        else {
            dialpad.enterKey(view);
        }

        TextView tvCode = (TextView) findViewById(R.id.dr_txt);
        tvCode.setText(dialpad.getCode(), 0, dialpad.getCode().length);
    }

    public void gotoQRScanner(View view) {
        Intent intent = new Intent(this, DefuseQRCodeScanner.class);
        intent.putExtra("isSetup", false);
        startActivity(intent);
    }

    public void onBackPressed() {

    }
}

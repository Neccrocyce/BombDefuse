package com.mai.bombdefuse;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class BombReady extends AppCompatActivity {
    Dialpad dialpad;



    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bomb_ready);

        dialpad = new Dialpad(Settings.getCodeLength());

        TextView tvCode = (TextView) findViewById(R.id.br_txt);
        tvCode.setText(dialpad.getCode(), 0, dialpad.getCode().length);
    }

    public void enterCode (View view) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.c4_click);
        if (Settings.isSound()) {
            mp.start();
        }

        if (dialpad.isEnterKey(view)) {
            if (dialpad.reachedEndOfLine()) {
                Intent intent = new Intent(this, BombActive.class);
                intent.putExtra("code", dialpad.getCodeAsIntArray());
                startActivity(intent);
            }
        }
        else {
            dialpad.enterKey(view);
        }

        TextView tvCode = (TextView) findViewById(R.id.br_txt);
        tvCode.setText(dialpad.getCode(), 0, dialpad.getCode().length);

    }
}

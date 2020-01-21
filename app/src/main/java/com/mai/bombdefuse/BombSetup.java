package com.mai.bombdefuse;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class BombSetup extends Activity {
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bomb_setup);

        //create key
        int[] key = new int[Settings.getCodeLength()];
        for (int i = 0; i < key.length; i++) {
            key[i] = (int) (Math.random() * 10);
        }
        Settings.setKey(key);

        //create QR Code
        ImageView qr_code = (ImageView) findViewById(R.id.bs_qr_code);
        qr_code.setImageBitmap(QRCodeGenerator.getInstance().generate(Settings.getKeyAsString()));
    }

    public void gotoBombReady(View view) {
        startActivity(new Intent(this, BombReady.class));
    }





}

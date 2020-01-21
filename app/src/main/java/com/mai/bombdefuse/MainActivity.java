package com.mai.bombdefuse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void gotoBomb(View view) {
        startActivity(new Intent(this, BombSetup.class));
    }

    public void gotoDefuse(View view) {
        Intent intent = new Intent(this, DefuseQRCodeScanner.class);
        intent.putExtra("isSetup", true);
        startActivity(intent);
    }

    public void gotoSettings(View view) {

    }
}

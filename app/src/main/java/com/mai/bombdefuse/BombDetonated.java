package com.mai.bombdefuse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BombDetonated extends Activity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bomb_detonated);
    }

    public void gotoBombReady(View view) {
        startActivity(new Intent(this, BombReady.class));
    }

    public void gotoMainActivity(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }



    @Override
    public void onBackPressed() {

    }


}

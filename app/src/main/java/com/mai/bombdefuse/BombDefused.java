package com.mai.bombdefuse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BombDefused extends Activity {
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bomb_defused);

        //receive time
        Intent receivedIntent = getIntent();
        if (receivedIntent != null && receivedIntent.hasExtra("time")) {
            CharSequence time = receivedIntent.getCharSequenceExtra("time");
            TextView tvTime = (TextView) findViewById(R.id.bd_txt_timer);
            tvTime.setText(time);
        }

        Toast.makeText(getApplicationContext(), "Bomb has been defused", Toast.LENGTH_LONG).show();
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

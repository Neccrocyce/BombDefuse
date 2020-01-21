package com.mai.bombdefuse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DefuseActive extends Activity {

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defuse_active);

        int[] code = getIntent().getIntArrayExtra("code");

        TextView tv = (TextView) findViewById(R.id.da_txt_code);
        tv.setText(Coder.getInstance().codeToString(Coder.getInstance().decode(code)));
    }

    public void gotoDefuseReady(View view) {
        startActivity(new Intent(this, DefuseReady.class));
    }

    public void gotoMainActivity(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }



    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, DefuseReady.class));
    }

}

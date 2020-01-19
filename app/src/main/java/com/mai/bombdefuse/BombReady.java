package com.mai.bombdefuse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class BombReady extends AppCompatActivity {
    private char[] code = new char[Settings.getCodeLength()*2-1];
    private int indexCode = 0;



    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bomb_ready);

        //initialize code line
        for (int i = 0; i < code.length; i = i+2) {
            code[i] = '_';
        }

        for (int i = 1; i < code.length; i = i+2) {
            code[i] = ' ';
        }

        TextView tvCode = (TextView) findViewById(R.id.br_txt);
        tvCode.setText(code, 0, code.length);
    }

    public void enterCode (View view) {
        char key = view.getContentDescription().charAt(0);
        if (key == 'E') {
            if (indexCode >= code.length) {
                indexCode = -1;
                TextView tvCode = (TextView) findViewById(R.id.br_txt);
                tvCode.setText(new char[]{' '}, 0, 1);
                Intent intent = new Intent(this, BombActive.class);
                intent.putExtra("code", getCode());
                startActivity(intent);
            }
        }
        else if (key == 'B') {
            if (indexCode > 1) {
                indexCode = indexCode - 2;
                code[indexCode] = '_';
            }
        }
        else {
            if (indexCode < code.length) {
                code[indexCode] = key;
                indexCode = indexCode + 2;

            }
        }

        if (indexCode > -1) {
            TextView tvCode = (TextView) findViewById(R.id.br_txt);
            tvCode.setText(code, 0, code.length);
        }
    }

    private int[] getCode () {
        int[] codeNew = new int[Settings.getCodeLength()];
        for (int i = 0; i < code.length; i = i+2) {
            codeNew[i/2] = Integer.parseInt("" + code[i]);
        }
        return codeNew;
    }
}

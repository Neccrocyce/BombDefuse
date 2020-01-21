package com.mai.bombdefuse;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;

public class Dialpad {
    private char[] code;
    private int indexCode = 0;

    public Dialpad (int codeLength) {
        this.code = new char[codeLength*2-1];
        resetCode();
    }

    public void enterKey (View view) {

        char key = view.getContentDescription().charAt(0);
        switch (key) {
            case 'B':
                if (indexCode > 1) {
                    indexCode = indexCode - 2;
                    code[indexCode] = '_';
                }
                break;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                if (indexCode < code.length) {
                    code[indexCode] = key;
                    indexCode = indexCode + 2;

                }
                break;
            case 'E':
                break;
            default:
                Log.e(this.getClass().getSimpleName(), "Typed Key \"" + key + "\" does not exist");
        }
    }

    public void resetCode() {
        for (int i = 0; i < code.length; i = i+2) {
            code[i] = '_';
        }

        for (int i = 1; i < code.length; i = i+2) {
            code[i] = ' ';
        }
        indexCode = 0;
    }

    public boolean isEnterKey (View view) {
        return view.getContentDescription().charAt(0) == 'E';
    }

    public boolean reachedEndOfLine () {
        return indexCode >= code.length;
    }

    public char[] getCode() {
        return code;
    }

    public int[] getCodeAsIntArray () {
        int[] codeNew = new int[Settings.getCodeLength()];
        for (int i = 0; i < code.length; i = i+2) {
            codeNew[i/2] = Integer.parseInt("" + code[i]);
        }
        return codeNew;
    }

    public String getCodeAsString () {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < code.length; i = i+2) {
            sb.append(code[i]);
        }
        return sb.toString();
    }
}

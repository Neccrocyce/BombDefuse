package com.mai.bombdefuse;

public class Coder {
    public static Coder instance;

    private Coder () {

    }

    public static Coder getInstance() {
        if (instance == null) {
            instance = new Coder();
        }
        return instance;
    }

    public int[] encode (int[] code) {
        int[] codeCrypted = new int[code.length];
        for (int i = 0; i < code.length; i++) {
            codeCrypted[i] = (code[i] + Settings.getKey()[i]) % 10;
        }
        return codeCrypted;
    }

    public int[] decode (int[] code) {
        int[] codeDecrypted = new int[code.length];
        for (int i = 0; i < code.length; i++) {
            int keyInverted = 10 - Settings.getKey()[i];
            codeDecrypted[i] = (code[i] + keyInverted) % 10;
        }
        return codeDecrypted;
    }

    public String codeToString(int[] code) {
        StringBuilder codeNew = new StringBuilder();
        for (int c : code) {
            codeNew.append(c);
        }
        return codeNew.toString();
    }

    public char[] codeToCharArray (int[] code) {
        char[] codeNew = new char[code.length];
        for (int i = 0; i < code.length; i++) {
            codeNew[i] = (char) (code[i] + 48);
        }
        return codeNew;
    }
}

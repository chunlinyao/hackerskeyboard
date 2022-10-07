package org.pocketworkstation.pckeyboard;

import android.content.Context;
import android.view.KeyEvent;

import java.io.*;
import java.util.HashMap;

public class ScanCodeMapper {
    private static final String LABEL_PREFIX = "KEYCODE_";

    public static final int SCAN_CODE_UNKNOWN = 0;
    private static HashMap<Integer, Integer> map;

    static {
        map  = new HashMap<>();
        try(BufferedReader layoutFile = new BufferedReader(new InputStreamReader(ScanCodeMapper.class.getResourceAsStream("Generic.kl")))) {
            String row;
            while((row = layoutFile.readLine()) != null) {
                if (row.trim().isEmpty() || row.startsWith("#")
                        || row.startsWith("key usage")
                        || row.contains("FUNCTION")
                        || ! row.startsWith("key")) {
                    continue;
                }
                String[] fields = row.split("\\s+");
                int keyCode = KeyEvent.keyCodeFromString(LABEL_PREFIX + fields[2]);
                int scanCode = Integer.parseInt(fields[1]);
                if (keyCode != KeyEvent.KEYCODE_UNKNOWN) {
                    map.put(keyCode, scanCode);
                }
            };
        } catch (IOException e) {
            //
        }
    }

    static public int scanCodeFromKeyCode(int keyCode) {
        Integer scanCode = map.get(keyCode);
        if (scanCode == null) {
            return SCAN_CODE_UNKNOWN;
        } else {
            return scanCode;
        }
    }
}

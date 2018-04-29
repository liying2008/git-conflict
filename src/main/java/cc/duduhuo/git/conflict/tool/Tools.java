package cc.duduhuo.git.conflict.tool;

import java.awt.*;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/26 21:06
 * Description:
 * Remarks:
 * =======================================================
 */
public final class Tools {
    public static String color2HexString(Color color) {
        String r, g, b;
        StringBuilder su = new StringBuilder();
        r = Integer.toHexString(color.getRed());
        g = Integer.toHexString(color.getGreen());
        b = Integer.toHexString(color.getBlue());
        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;
        su.append("0x");
        su.append(r);
        su.append(g);
        su.append(b);
        // return like 0x000000
        return su.toString();
    }

    public static String int2HexString(int color) {
        StringBuilder s = new StringBuilder(Integer.toHexString(color));
        int count = 6 - s.length();
        for (int i = 0; i < count; i++) {
            s.insert(0, "0");
        }
        s.insert(0, "0x");
        return s.toString();
    }
}

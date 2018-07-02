package com.drawwdev.fakelobbyadvanced.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bukkit.ChatColor;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class StringUtils {

    public static String cc(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String hideText(String text) {
        Objects.requireNonNull(text, "text can not be null!");

        StringBuilder output = new StringBuilder();

        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        String hex = Hex.encodeHexString(bytes);

        for (char c : hex.toCharArray()) {
            output.append(ChatColor.COLOR_CHAR).append(c);
        }

        return output.toString();
    }

    public static String revealText(String text) {
        Objects.requireNonNull(text, "text can not be null!");

        if (text.isEmpty()) {
            return text;
        }

        char[] chars = text.toCharArray();

        char[] hexChars = new char[chars.length / 2];

        IntStream.range(0, chars.length)
                .filter(value -> value % 2 != 0)
                .forEach(value -> hexChars[value / 2] = chars[value]);

        try {
            return new String(Hex.decodeHex(hexChars), StandardCharsets.UTF_8);
        } catch (DecoderException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Couldn't decode text", e);
        }
    }

    public static String dateFormat(Date paramDate) {
        String str = "N/A";
        if (paramDate == null) {
            return str;
        }
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        str = localSimpleDateFormat.format(paramDate);
        return str;
    }

    public static String numberFormat(double paramDouble) {
        if (String.valueOf(paramDouble).endsWith(".0")) {
            return new DecimalFormat("#,###,###,###,###").format(paramDouble);
        }
        return new DecimalFormat("#,###,###,###,##0.00").format(paramDouble);
    }

    public static String setColour(String paramString) {
        if ((paramString == null) || (paramString.isEmpty())) {
            return null;
        }
        paramString = ChatColor.translateAlternateColorCodes('�', paramString);
        paramString = ChatColor.translateAlternateColorCodes('&', paramString);

        return paramString;
    }

    public static List<String> setColourList(List<String> oldlist) {
        List<String> newlist = new ArrayList<String>();
        for (String string : oldlist) {
            newlist.add(ChatColor.translateAlternateColorCodes('&', string));
        }
        return newlist;
    }

}

package de.jardateien.simpleparty.utils;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@UtilityClass
public class MultiColors {

    private final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
    private final String[] defaultColors = new String[] { "#000000", "#0000AA", "#00AA00", "#00AAAA", "#AA0000", "#AA00AA", "#FFAA00", "#AAAAAA", "#555555", "#5555FF", "#55FF55", "#55FFFF", "#FF5555", "#FF55FF", "#FFFF55", "#FFFFFF" } ;

    private List<String> fade(String hexColor1, String hexColor2, int steps) {
        var color1 = Color.decode(hexColor1);
        var color2 = Color.decode(hexColor2);

        var rStep = (color2.getRed() - color1.getRed()) - steps;
        var gStep = (color2.getGreen() - color1.getGreen()) - steps;
        var bStep = (color2.getBlue() - color1.getBlue()) - steps;

        List<String> colors = new ArrayList<>();

        for (int i = 1; i < steps; i++) {
            var r = (color1.getRed() + (i * rStep));
            var g = (color1.getGreen() + (i * gStep));
            var b = (color1.getBlue() + (i * bStep));

            var color = String.format("#%02x%02x%02x", r, g, b);
            if(!pattern.matcher(color).find()) continue;

            colors.add(color);
        }

        return colors;
    }

    public String translate(String input) {
        input = ChatColor.translateAlternateColorCodes('&', input);

        for (ChatColor color : ChatColor.values()) {
            var startEdit = String.format("<%s>", color.getName());
            var endEdit = String.format("</%s>", color.getName());
            if(!input.contains(startEdit) || !input.contains(endEdit)) continue;
            input = input.replace(startEdit, color.toString());
            input = input.replace(endEdit, ChatColor.RESET.toString());
        }

        while (input.contains("<color:") && input.contains("</color>")) {
            var start = input.indexOf("<color:");
            var hex = input.indexOf("#", start);
            var color = input.substring(hex, hex + 7);

            input = input.replaceFirst("<color:" + color + ">", "§x" + color);
            input = input.replaceFirst("</color>", ChatColor.RESET.toString());
        }

        while (input.contains("<fade:") && input.contains("</fade>")) {
            var start = input.indexOf("<fade:");
            var firstHex = input.indexOf("#", start);
            var secHex = input.indexOf("#", firstHex + 2);
            System.out.println(firstHex + " " + secHex);

            var color1 = input.substring(firstHex, firstHex + 7);
            var color2 = input.substring(secHex, secHex + 7);

            String msg = input.substring(input.indexOf(">", start) + 1, input.indexOf("</fade>"));
            input = input.replaceFirst(input.substring(start, input.indexOf(">", start) + 1), "");
            input = input.replaceFirst("</fade>", ChatColor.RESET.toString());

            var edit = new StringBuilder();
            var colors = fade(color1, color2, msg.length());
            var message = input.toCharArray();
            for (int i = 0; i < msg.length(); i++) {
                edit.append("§x").append(colors.get(i)).append(message[i]);
            }
            input = input.replace(msg, edit);
        }

        var match = pattern.matcher(input);
        while (match.find()) {
            var color = input.substring(match.start(), match.end());
            input = input.replace(color, ChatColor.of(color).toString());
            match = pattern.matcher(input);
        }

        return input;
    }

}

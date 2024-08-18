package de.jardateien.simpleparty.utils;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.awt.*;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@UtilityClass
public class ColorUtil {
    private final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
    private final String[] defaultColors = new String[] { "#000000", "#0000AA", "#00AA00", "#00AAAA", "#AA0000", "#AA00AA", "#FFAA00", "#AAAAAA", "#555555", "#5555FF", "#55FF55", "#55FFFF", "#FF5555", "#FF55FF", "#FFFF55", "#FFFFFF" } ;

    private List<String> fade(String hexColor1, String hexColor2, int steps) {
        var color1 = Color.decode(hexColor1);
        var color2 = Color.decode(hexColor2);

        var rStep = (color2.getRed() - color1.getRed()) - steps;
        var gStep = (color2.getGreen() - color1.getGreen()) - steps;
        var bStep = (color2.getBlue() - color1.getBlue()) - steps;

        List<String> colors = new ArrayList<>();

        for (int i = 0; i < steps; i++) {
            var r = (color1.getRed() + (i * rStep));
            var g = (color1.getGreen() + (i * gStep));
            var b = (color1.getBlue() + (i * bStep));
            colors.add(String.format("#%02x%02x%02x", r, g, b));
        }

        return colors;
    }

    private String closeColor(String hexColor) {
        var hexRed = Integer.parseInt(hexColor.substring(0,2), 16);
        var hexGreen = Integer.parseInt(hexColor.substring(2,4), 16);
        var hexBlue = Integer.parseInt(hexColor.substring(4,6), 16);

        var finalColor = "";
        var minDifference = Integer.MAX_VALUE;

        for (String defaultColor : defaultColors) {
            var defaultRed = Integer.parseInt(defaultColor.substring(0,2), 16);
            var defaultGreen = Integer.parseInt(defaultColor.substring(2,4), 16);
            var defaultBlue = Integer.parseInt(defaultColor.substring(4,6), 16);

            var difference = Math.abs(hexRed - defaultRed) + java.lang.Math.abs(hexGreen - defaultGreen) + Math.abs(hexBlue - defaultBlue);
            if(difference < minDifference) {
                minDifference = difference;
                finalColor = defaultColor;
            }
        }

        return finalColor;
    }

    public String translate(ProxiedPlayer player, String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);

        for (ChatColor color : ChatColor.values()) {
            var startEdit = String.format("<%s>", color.getName());
            var endEdit = String.format("</%s>", color.getName());
            if(!message.contains(startEdit) || !message.contains(endEdit)) continue;
            message = message.replace(startEdit, color.toString());
            message = message.replace(endEdit, ChatColor.RESET.toString());
        }

        while (message.contains("<color:") && message.contains("</color>")) {
            var start = message.indexOf("<color:");
            var hex = message.indexOf("#", start);
            var color = message.substring(hex, hex + 7);

            message = message.replaceFirst("<color:" + color + ">", color);
            message = message.replaceFirst("</color>", ChatColor.RESET.toString());
        }

        while (message.contains("<fade:") && message.contains("</fade>")) {
            var start = message.indexOf("<fade:");
            var firstHex = message.indexOf("#", start);
            var secHex = message.indexOf("#", firstHex + 2);

            var color1 = message.substring(firstHex, firstHex + 7);
            var color2 = message.substring(secHex, secHex + 7);

            String msg = message.substring(message.indexOf(">", start) + 1, message.indexOf("</fade>"));
            message = message.replaceFirst(message.substring(start, message.indexOf(">", start) + 1), "");
            message = message.replaceFirst("</fade>", ChatColor.RESET.toString());

            var edit = msg;
            var colors = fade(color1, color2, msg.length());
            for (int i = 0; i < msg.length(); i++) { edit = edit.substring(0, i * 8) + colors.get(i) + edit.substring(i * 8); }
            message = message.replace(msg, edit);
        }

        var match = pattern.matcher(message);
        while (match.find()) {
            var color = message.substring(match.start(), match.end());

            if(player != null && player.getPendingConnection().getVersion() < 735) {
                var closedColor = closeColor(color);
                message = message.replaceAll(color, closedColor);
                color = closedColor;
            }

            message = message.replace(color, ChatColor.of(color) + "");
            match = pattern.matcher(message);
        }

        return message;
    }

}
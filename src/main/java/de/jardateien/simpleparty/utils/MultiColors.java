package de.jardateien.simpleparty.utils;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

import java.util.regex.Pattern;

@UtilityClass
public class MultiColors {

    private final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
    private final String[] defaultColors = new String[] { "#000000", "#0000AA", "#00AA00", "#00AAAA", "#AA0000", "#AA00AA", "#FFAA00", "#AAAAAA", "#555555", "#5555FF", "#55FF55", "#55FFFF", "#FF5555", "#FF55FF", "#FFFF55", "#FFFFFF" } ;

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

            input = input.replaceFirst("<color:" + color + ">", color);
            input = input.replaceFirst("</color>", ChatColor.RESET.toString());
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

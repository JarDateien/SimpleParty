package de.jardateien.simpleparty.utils;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class Math {

    public int getRandom(final int max) { return new Random().nextInt(max + 1); }
    public int search(String[] args) {
        if(args.length <= 1) return 1;
        try { return java.lang.Math.min(Integer.parseInt(args[1]), 3); }
        catch (NumberFormatException exception) { return 1; }
    }


}

package com.benmohammad.reelz.model;

import java.util.regex.Pattern;

public class ColorScheme {

    public final Pattern pattern;
    public final int color;

    public ColorScheme(Pattern pattern, int color) {
        this.pattern = pattern;
        this.color = color;
    }
}

package com.utils.date;

/**
 * @author Nikolajs Arhipovs <nikolajs.arhipovs@lpb.lv>
 */
public final class Intervals {

    public static <T extends Number & Comparable> T between(T value, T min, T max) {
        return between(value, min, max, null);
    }

    public static <T extends Number & Comparable> T between(T value, T min, T max, T def) {

        if (value == null) {
            return def;
        }

        if (min != null && max != null && min.compareTo(max) > 0) {
            throw new IllegalArgumentException("Max must be bigger than min");
        }

        if (min != null && value.compareTo(min) < 0) {
            return min;
        }
        if (max != null && value.compareTo(max) > 0) {
            return max;
        }

        return value;

    }

}

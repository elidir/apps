package com.example.utils;

public class MathUtils {

    public static boolean inBounds(int value, int minValue, int maxValue) {
        return (value >= minValue && value <= maxValue);
    }

    public static boolean inBounds(long value, long minValue, long maxValue) {
        return (value >= minValue && value <= maxValue);
    }

    public static boolean inBounds(double value, double minValue, double maxValue) {
        return (value >= minValue && value <= maxValue);
    }

    private MathUtils() {
        throw new UnsupportedOperationException();
    }
}

package util;

public class PNUtils {
    public static int generateRandomNumber(int minValue, int maxValue) {
        return (int) Math.random() * (maxValue - minValue) + minValue;
    }
}


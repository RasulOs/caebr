package caebr.util;

public class NumberUtils {

    private NumberUtils() {}

    public static boolean approximatelyZero(Double d, double epsilon) {
        return Math.abs(d) < epsilon;
    }

    public static boolean approximatelyEqual(Double d1, Double d2, double epsilon) {
        return Math.abs(d1 - d2) < epsilon;
    }

    public static boolean approximatelyEqual(Double[] expected, Double[] actual, double epsilon) {
        for (int i = 0; i < expected.length; i++) {
            if (!approximatelyEqual(expected[i], actual[i], epsilon) ) {
                return false;
            }
        }

        return true;
    }

    public static boolean approximatelyEqual(Double[][] expected, Double[][] actual, double epsilon) {
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[0].length; j++) {
                if (!approximatelyEqual(expected[i][j], actual[i][j], epsilon) ) {
                    return false;
                }
            }
        }

        return true;
    }
}

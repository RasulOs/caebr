package caebr;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtils {

    public static void approximatelyEquals(Double expected, Double actual) {
        assertEquals(expected, actual, 0.001d);
    }

    public static void approximatelyEquals(Double[] expected, Double[] actual) {
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i], 0.001d);
        }
    }

    public static void approximatelyEquals(Double[][] expected, Double[][] actual) {
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[0].length; j++)
                assertEquals(expected[i][j], actual[i][j], 0.001d);
        }
    }

}

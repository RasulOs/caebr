package caebr;

import caebr.vector.StandardVector;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class StandardVectorTest {

    @Test
    public void testMultiplication1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, false);

        StandardVector<Integer> vector2 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

        assertArrayEquals( new Double[][] {{91d}},
                vector1.multiply(vector2).toMatrix());
    }

    @Test
    public void testMultiplication2() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

        StandardVector<Integer> vector2 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, false);

        assertArrayEquals(new Double[][] {
                {1d, 2d, 3d, 4d, 5d, 6d},
                {2d, 4d, 6d, 8d, 10d, 12d},
                {3d, 6d, 9d, 12d, 15d, 18d},
                {4d, 8d, 12d, 16d, 20d, 24d},
                {5d, 10d, 15d, 20d, 25d, 30d},
                {6d, 12d, 18d, 24d, 30d, 36d}
    }, vector1.multiply(vector2).toMatrix());

    }

    @Test
    public void testAddition1() {

            StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

            StandardVector<Integer> vector2 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

            assertArrayEquals(new Integer[] {2, 4, 6, 8, 10, 12}, vector1.add(vector2).toIntegerArray());
    }

    @Test
    public void testAddition2() {

            StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

            vector1.add(10);

            assertArrayEquals(new Integer[] {11, 12, 13, 14, 15, 16}, vector1.toIntegerArray());
    }

    @Test
    public void testAddition3() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

        assertThrows(IllegalArgumentException.class, () -> vector1.add(new Integer[] {10, 20, 30, 40, 50}));
    }

    @Test
    public void testSubtraction1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {10, 20, 30, 40, 50, 60}, true);

        StandardVector<Integer> vector2 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

        assertArrayEquals(new Integer[] {9, 18, 27, 36, 45, 54}, vector1.subtract(vector2).toIntegerArray());
    }

    @Test
    public void testSubtraction2() {

            StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {10, 20, 30, 40, 50, 60}, true);

            vector1.subtract(10);

            assertArrayEquals(new Integer[] {0, 10, 20, 30, 40, 50}, vector1.toIntegerArray());
    }

    @Test
    public void testSubtraction3() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

        assertThrows(IllegalArgumentException.class, () -> vector1.subtract(new Integer[] {10, 20, 30, 40, 50}));
    }

    @Test
    public void testSum() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

        assertEquals(21d, vector1.sum());
    }

    @Test
    public void testMean() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

        assertEquals(3.5d, vector1.mean());
    }

    @Test
    public void testMax() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0}, true);

        assertEquals(6d, vector1.max());
    }

    @Test
    public void testMin() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0}, true);

        assertEquals(0d, vector1.min());
    }

    @Test
    public void testMedian() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0}, true);

        assertEquals(3d, vector1.median());
    }

    @Test
    public void testDrop1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0}, true);

        assertArrayEquals(new Integer[] {1, 2, 3, 4, 5, 6}, vector1.drop().toIntegerArray());
    }

    @Test
    public void testDrop2() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0}, true);

        assertArrayEquals(new Integer[] {1, 2, 3, 4, 5, 0}, vector1.drop(5).toIntegerArray());
    }

    @Test
    public void testDrop3() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0}, true);

        assertArrayEquals(new Integer[] {1, 2, 3, 0}, vector1.drop(3, 6).toIntegerArray());
    }

    @Test
    public void testDrop4() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0}, true);

        assertArrayEquals(new Integer[] {1, 2, 3, 4, 5, 6, 0}, vector1.drop(0, 0).toIntegerArray());
    }

    @Test
    public void testDrop5() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0}, true);

        assertThrows(IllegalArgumentException.class, () -> vector1.drop(7, 7).toIntegerArray());
    }

    @Test
    public void testSet1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0}, true);

        assertArrayEquals(new Integer[] {1, 2, 3, 4, 5, 6, 10}, vector1.set(10, 6).toIntegerArray());
    }

    @Test
    public void testPut1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

        assertArrayEquals(new Integer[] {1, 2, 3, 4, 5, 6, 10}, vector1.put(10).toIntegerArray());
    }

    @Test
    public void testPut2() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

        assertArrayEquals(new Integer[] {1, 2, 3, 4, 5, 6, 10, 11, 12}, vector1.put(new Integer[] {10, 11, 12}).toIntegerArray());
    }

    @Test
    public void testSlice1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

        assertArrayEquals(new Integer[] {1, 2, 3}, vector1.slice(0, 3).toIntegerArray());
    }

    @Test
    public void testl0Norm() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0}, true);

        assertEquals(6, vector1.l0Norm());
    }

    @Test
    public void testl1Norm() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0, -10}, true);

        assertEquals(31d, vector1.l1Norm());
    }

    @Test
    public void testl2Norm() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0, -10}, true);

        approximatelyEquals(Math.sqrt(191), vector1.l2Norm());
    }

    @Test
    public void testlInfinityNorm() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {9, 1, 2, 3, 4, 5, 6, 0, -10}, true);

        assertEquals(10d, vector1.lInfinityNorm());
    }

    @Test
    public void testVariance() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5}, true);

        approximatelyEquals(2.5, vector1.variance());
    }

    private void approximatelyEquals(Double expected, Double actual) {
        assertEquals(expected, actual, 0.001d);
    }

    private void approximatelyEquals(Double[] expected, Double[] actual) {
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i], 0.001d);
        }
    }

    @Test
    public void testMinMaxNormalization1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5}, true);

        assertArrayEquals(new Double[] {0d, 0.25d, 0.5d, 0.75d, 1d}, vector1.minMaxNormalization(0, 1).toArray());
    }

    @Test
    public void testMinMaxNormalization2() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5}, true);

        assertArrayEquals(new Double[] {0d, 0.25d, 0.5d, 0.75d, 1d}, vector1.minMaxNormalization().toArray());
    }

    @Test
    public void testStandardDeviation() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5}, true);

        approximatelyEquals(Math.sqrt(2.5), vector1.standardDeviation());
    }

    @Test
    public void testZScoreStandardization() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5}, true);

        approximatelyEquals(new Double[] {-1.264d, -0.63247d, 0d, 0.6324d, 1.26494d}, vector1.zScoreStandardization().toArray());
    }

    @Test
    public void testDistinct() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 5, 5, 5, 1}, true);

        assertArrayEquals(new Double[] {1d, 2d, 3d, 4d, 5d}, vector1.distinct().toArray());
    }

    @Test
    public void testRange() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 5, 5, 5, 1}, true);

        assertEquals(4d, vector1.range());
    }

    @Test
    public void testSort() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {5, 4, 3, 2, 0, 1}, true);

        assertArrayEquals(new Double[] {0d, 1d, 2d, 3d, 4d, 5d}, vector1.sort().toArray());
    }

    @Test
    public void testReverse() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {5, 4, 3, 2, 0, 1}, true);

        assertArrayEquals(new Double[] {1d, 0d, 2d, 3d, 4d, 5d}, vector1.reverse().toArray());
    }

    @Test
    public void testMap() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {5, 4, 3, 2, 0, 1}, true);

        assertArrayEquals(new Double[] {10d, 8d, 6d, 4d, 0d, 2d}, vector1.map(x -> x * 2).toArray());
    }

    @Test
    public void testFilter() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {5, 4, 3, 2, 0, 1}, true);

        assertArrayEquals(new Double[] {5d, 4d}, vector1.filter(x -> x > 3).toArray());
    }

    @Test
    public void testReduce() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {5, 4, 3, 2, 0, 1}, true);

        assertEquals(15d, vector1.reduce((x, y) -> x + y));
    }

    @Test
    public void testMode() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {5, 4, 3, 2, 0, 1, 5, 5, 5, 5, 1}, true);

        assertIterableEquals(Arrays.asList(5d), vector1.mode());
    }

    @Test
    public void testTranspose() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {5, 4, 3, 2, 0, 1, 5, 5, 5, 5, 1}, true);

        assertEquals(11, vector1.transpose().getColumnNumber());
    }
}

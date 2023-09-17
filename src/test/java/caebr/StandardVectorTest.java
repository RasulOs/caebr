package caebr;

import caebr.util.NumberUtils;
import caebr.vector.StandardVector;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class StandardVectorTest {

    double epsilon = 0.001d;

    @Test
    void testMultiplication1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, false);

        StandardVector<Integer> vector2 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

        assertArrayEquals( new Double[][] {{91d}},
                vector1.multiply(vector2).toMatrix());
    }

    @Test
    void testMultiplication2() {

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
    void testMultiplication3() {
        Double[] vector1 = {1d, 2d, 3d, 4d, 5d, 6d};

        Double[] vector2 = {1d, 2d, 3d, 4d, 5d, 6d};

        assertArrayEquals(new Double[][] {
                {1d, 2d, 3d, 4d, 5d, 6d},
                {2d, 4d, 6d, 8d, 10d, 12d},
                {3d, 6d, 9d, 12d, 15d, 18d},
                {4d, 8d, 12d, 16d, 20d, 24d},
                {5d, 10d, 15d, 20d, 25d, 30d},
                {6d, 12d, 18d, 24d, 30d, 36d}
        }, StandardVector.multiply(vector1, vector2).toMatrix());
    }

    @Test
    void testMultiplication4() {
        Double[] vector1 = {1d, 2d, 3d, 4d, 5d, 6d};

        Double[] result = StandardVector.multiply(vector1, 10d);

        assertArrayEquals(new Double[] {10d, 20d, 30d, 40d, 50d, 60d}, result);
    }

    @Test
    void testAddition1() {

            StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

            StandardVector<Integer> vector2 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

            assertArrayEquals(new Integer[] {2, 4, 6, 8, 10, 12}, vector1.add(vector2).toIntegerArray());
    }

    @Test
    void testAddition2() {

            StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

            vector1.add(10);

            assertArrayEquals(new Integer[] {11, 12, 13, 14, 15, 16}, vector1.toIntegerArray());
    }

    @Test
    void testAddition3() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

        assertThrows(IllegalArgumentException.class, () -> vector1.add(new Integer[] {10, 20, 30, 40, 50}));
    }

    @Test
    void testAddition4() {
        Double[] vector1 = {1d, 2d, 3d, 4d, 5d, 6d};

        Double[] vector2 = {1d, 2d, 3d, 4d, 5d, 6d};

        assertArrayEquals(new Double[] {2d, 4d, 6d, 8d, 10d, 12d}, StandardVector.add(vector1, vector2));
    }

    @Test
    void testAddition5() {
        Double[] vector1 = {1d, 2d, 3d, 4d, 5d, 6d};

        assertArrayEquals(new Double[] {11d, 12d, 13d, 14d, 15d, 16d}, StandardVector.add(vector1, 10d));
    }

    @Test
    void testSubtraction1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {10, 20, 30, 40, 50, 60}, true);

        StandardVector<Integer> vector2 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

        assertArrayEquals(new Integer[] {9, 18, 27, 36, 45, 54}, vector1.subtract(vector2).toIntegerArray());
    }

    @Test
    void testSubtraction2() {

            StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {10, 20, 30, 40, 50, 60}, true);

            vector1.subtract(10);

            assertArrayEquals(new Integer[] {0, 10, 20, 30, 40, 50}, vector1.toIntegerArray());
    }

    @Test
    void testSubtraction3() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

        assertThrows(IllegalArgumentException.class, () -> vector1.subtract(new Integer[] {10, 20, 30, 40, 50}));
    }

    @Test
    void testSubtraction4() {
        Double[] vector1 = {10d, 20d, 30d, 40d, 50d, 60d};

        Double[] vector2 = {1d, 2d, 3d, 4d, 5d, 6d};

        assertArrayEquals(new Double[] {9d, 18d, 27d, 36d, 45d, 54d}, StandardVector.subtract(vector1, vector2));
    }

    @Test
    void testSubtraction5() {
        Double[] vector1 = {10d, 20d, 30d, 40d, 50d, 60d};

        assertArrayEquals(new Double[] {0d, 10d, 20d, 30d, 40d, 50d}, StandardVector.subtract(vector1, 10d));
    }

    @Test
    void testSum1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

        assertEquals(21d, vector1.sum());
    }

    @Test
    void testSum2() {
        Double[] vector1 = {1d, 2d, 3d, 4d, 5d, 6d};

        assertEquals(21d, StandardVector.sum(vector1));
    }


    @Test
    void testMean1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

        assertEquals(3.5d, vector1.mean());
    }

    @Test
    void testMean2() {
        Double[] vector1 = {1d, 2d, 3d, 4d, 5d, 6d};

        assertEquals(3.5d, StandardVector.mean(vector1));
    }

    @Test
    void testMax1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0}, true);

        assertEquals(6d, vector1.max());
    }

    @Test
    void testMax2() {
        Double[] vector1 = {1d, 2d, 3d, 4d, 5d, 6d, 0d};

        assertEquals(6d, StandardVector.max(vector1));
    }

    @Test
    void testMin1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0}, true);

        assertEquals(0d, vector1.min());
    }

    @Test
    void testMin2() {
        Double[] vector1 = {1d, 2d, 3d, 4d, 5d, 6d, 0d};

        assertEquals(0d, StandardVector.min(vector1));
    }

    @Test
    void testMedian1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0}, true);

        assertEquals(3d, vector1.median());
    }

    @Test
    void testMedian2() {
        Double[] vector1 = {1d, 2d, 3d, 4d, 5d, 6d, 0d};

        assertEquals(3d, StandardVector.median(vector1));
    }

    @Test
    void testDrop1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0}, true);

        assertArrayEquals(new Integer[] {1, 2, 3, 4, 5, 6}, vector1.drop().toIntegerArray());
    }

    @Test
    void testDrop2() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0}, true);

        assertArrayEquals(new Integer[] {1, 2, 3, 4, 5, 0}, vector1.drop(5).toIntegerArray());
    }

    @Test
    void testDrop3() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0}, true);

        assertArrayEquals(new Integer[] {1, 2, 3, 0}, vector1.drop(3, 6).toIntegerArray());
    }

    @Test
    void testDrop4() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0}, true);

        assertArrayEquals(new Integer[] {1, 2, 3, 4, 5, 6, 0}, vector1.drop(0, 0).toIntegerArray());
    }

    @Test
    void testDrop5() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0}, true);

        assertThrows(IllegalArgumentException.class, () -> vector1.drop(7, 7).toIntegerArray());
    }

    @Test
    void testDrop6() {
        Double[] vector = {1d, 2d, 3d, 4d, 5d, 6d, 0d};

        assertArrayEquals(new Double[] {1d, 2d, 3d, 4d, 5d, 6d}, StandardVector.drop(vector, 6));
    }

    @Test
    void testDrop7() {
        Double[] vector = {1d, 2d, 3d, 4d, 5d, 6d, 0d};

        assertArrayEquals(new Double[] {1d, 2d, 3d, 0d}, StandardVector.drop(vector, 3, 6));
    }

    @Test
    void testDrop8() {
        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0}, true);

        assertArrayEquals(new Integer[] {1, 2, 3}, vector1.drop(3, vector1.getRowNumber()).toIntegerArray());
    }

    @Test
    void testDrop9() {
        Double[] vector = {1d, 2d, 3d, 4d, 5d, 6d, 0d};

        assertArrayEquals(new Double[] {1d, 2d, 3d, 4d, 5d, 6d}, StandardVector.drop(vector));
    }

    @Test
    void testSet1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0}, true);

        assertArrayEquals(new Integer[] {1, 2, 3, 4, 5, 6, 10}, vector1.set(10, 6).toIntegerArray());
    }

    @Test
    void testPut1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

        assertArrayEquals(new Integer[] {1, 2, 3, 4, 5, 6, 10}, vector1.put(10).toIntegerArray());
    }

    @Test
    void testPut2() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

        assertArrayEquals(new Integer[] {1, 2, 3, 4, 5, 6, 10, 11, 12}, vector1.put(new Integer[] {10, 11, 12}).toIntegerArray());
    }

    @Test
    void testPut3() {
        Double[] vector1 = {1d, 2d, 3d, 4d, 5d, 6d};

        assertArrayEquals(new Double[] {1d, 2d, 3d, 4d, 5d, 6d, 10d, 11d, 12d},
                StandardVector.put(vector1, new Double[] {10d, 11d, 12d}));
    }

    @Test
    void testSlice1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6}, true);

        assertArrayEquals(new Integer[] {1, 2, 3}, vector1.slice(0, 3).toIntegerArray());
    }

    @Test
    void testSlice2() {
        Double[] vector1 = {1d, 2d, 3d, 4d, 5d, 6d};

        assertArrayEquals(new Double[] {1d, 2d, 3d}, StandardVector.slice(vector1, 0, 3));
    }

    @Test
    void testl0Norm1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0}, true);

        assertEquals(6, vector1.l0Norm());
    }

    @Test
    void testl0Norm2() {
        Double[] vector1 = {1d, 2d, 3d, 4d, 5d, 6d, 0d};

        assertEquals(6, StandardVector.l0Norm(vector1));

    }


    @Test
    void testl1Norm1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0, -10}, true);

        assertEquals(31d, vector1.l1Norm());
    }

    @Test
    void testl1Norm2() {
        Double[] vector1 = {1d, 2d, 3d, 4d, 5d, 6d, 0d, -10d};

        assertEquals(31d, StandardVector.l1Norm(vector1));
    }

    @Test
    void testL2Norm1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6, 0, -10}, true);

        assertTrue(NumberUtils.approximatelyEqual(Math.sqrt(191), vector1.l2Norm(), epsilon));
    }

    @Test
    void testL2Norm2() {
        Double[] vector1 = {1d, 2d, 3d, 4d, 5d, 6d, 0d, -10d};

        assertTrue(NumberUtils.approximatelyEqual(Math.sqrt(191), StandardVector.l2Norm(vector1), epsilon));
    }

    @Test
    void testLInfinityNorm1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {9, 1, 2, 3, 4, 5, 6, 0, -10}, true);

        assertEquals(10d, vector1.lInfinityNorm());
    }

    @Test
    void testLInfinityNorm2() {
        Double[] vector1 = {9d, 1d, 2d, 3d, 4d, 5d, 6d, 0d, -10d};

        assertEquals(10d, StandardVector.lInfinityNorm(vector1));
    }

    @Test
    void testVariance1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5}, true);

        assertTrue(NumberUtils.approximatelyEqual(2.5, vector1.variance(), epsilon));
    }

    @Test
    void testVariance2() {
        Double[] vector1 = {1d, 2d, 3d, 4d, 5d};

        assertTrue(NumberUtils.approximatelyEqual(2.5, StandardVector.variance(vector1), epsilon));
    }

    @Test
    void testMinMaxNormalization1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5}, true);

        assertArrayEquals(new Double[] {0d, 0.25d, 0.5d, 0.75d, 1d}, vector1.minMaxNormalization(0, 1).toArray());
    }

    @Test
    void testMinMaxNormalization2() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5}, true);

        assertArrayEquals(new Double[] {0d, 0.25d, 0.5d, 0.75d, 1d}, vector1.minMaxNormalization().toArray());
    }

    @Test
    void testMinMaxNormalization3() {
        Double[] vector1 = {1d, 2d, 3d, 4d, 5d};

        assertArrayEquals(new Double[] {0d, 0.25d, 0.5d, 0.75d, 1d}, StandardVector.minMaxNormalization(vector1, 0d, 1d));
    }

    @Test
    void testStandardDeviation1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5}, true);

        assertTrue(NumberUtils.approximatelyEqual(Math.sqrt(2.5), vector1.standardDeviation(), epsilon));
    }

    @Test
    void testStandardDeviation2() {
        Double[] vector1 = {1d, 2d, 3d, 4d, 5d};

        assertTrue(NumberUtils.approximatelyEqual(Math.sqrt(2.5),
                StandardVector.standardDeviation(vector1), epsilon));
    }

    @Test
    void testZScoreStandardization1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5}, true);

        assertTrue(NumberUtils.approximatelyEqual(new Double[] {-1.264d, -0.63247d, 0d, 0.6324d, 1.26494d}, vector1.zScoreStandardization().toArray(),
                epsilon));
    }

    @Test
    void testZScoreStandardization2() {
        Double[] vector1 = {1d, 2d, 3d, 4d, 5d};

        assertTrue(NumberUtils.approximatelyEqual(new Double[] {-1.264d, -0.63247d, 0d, 0.6324d, 1.26494d},
                StandardVector.zScoreStandardization(vector1), epsilon));
    }

    @Test
    void testDistinct1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 5, 5, 5, 1}, true);

        assertArrayEquals(new Double[] {1d, 2d, 3d, 4d, 5d}, vector1.distinct().toArray());
    }

    @Test
    void testDistinct2() {
        Double[] vector1 = {1d, 2d, 3d, 4d, 5d, 5d, 5d, 5d, 1d};

        assertArrayEquals(new Double[] {1d, 2d, 3d, 4d, 5d}, StandardVector.distinct(vector1).toArray());
    }

    @Test
    void testRange1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 5, 5, 5, 1}, true);

        assertEquals(4d, vector1.range());
    }

    @Test
    void testRange2() {
        Double[] vector1 = {1d, 2d, 3d, 4d, 5d, 5d, 5d, 5d, 1d};

        assertEquals(4d, StandardVector.range(vector1));
    }

    @Test
    void testSort1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {5, 4, 3, 2, 0, 1}, true);

        assertArrayEquals(new Double[] {0d, 1d, 2d, 3d, 4d, 5d}, vector1.sort(true).toArray());
    }

    @Test
    void testSort2() {
        Double[] vector1 = {5d, 4d, 3d, 2d, 0d, 1d};

        assertArrayEquals(new Double[] {0d, 1d, 2d, 3d, 4d, 5d}, StandardVector.sort(vector1, true));
    }

    @Test
    void testReverse1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {5, 4, 3, 2, 0, 1}, true);

        assertArrayEquals(new Double[] {1d, 0d, 2d, 3d, 4d, 5d}, vector1.reverse().toArray());
    }

    @Test
    void testReverse2() {
        Double[] vector1 = {5d, 4d, 3d, 2d, 0d, 1d};

        assertArrayEquals(new Double[] {1d, 0d, 2d, 3d, 4d, 5d}, StandardVector.reverse(vector1));
    }

    @Test
    void testMap1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {5, 4, 3, 2, 0, 1}, true);

        assertArrayEquals(new Double[] {10d, 8d, 6d, 4d, 0d, 2d}, vector1.map(x -> x * 2).toArray());
    }

    @Test
    void testMap2() {
        Double[] vector1 = {5d, 4d, 3d, 2d, 0d, 1d};

        assertArrayEquals(new Double[] {10d, 8d, 6d, 4d, 0d, 2d}, StandardVector.map(vector1, x -> x * 2));
    }

    @Test
    void testFilter1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {5, 4, 3, 2, 0, 1}, true);

        assertArrayEquals(new Double[] {5d, 4d}, vector1.filter(x -> x > 3).toArray());
    }

    @Test
    void testFilter2() {
        Double[] vector1 = {5d, 4d, 3d, 2d, 0d, 1d};

        assertArrayEquals(new Double[] {5d, 4d}, StandardVector.filter(vector1, x -> x > 3));
    }

    @Test
    void testReduce1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {5, 4, 3, 2, 0, 1}, true);

        assertEquals(15d, vector1.reduce((x, y) -> x + y));
    }

    @Test
    void testReduce2() {
        Double[] vector1 = {5d, 4d, 3d, 2d, 0d, 1d};

        assertEquals(15d, StandardVector.reduce(vector1, (x, y) -> x + y));
    }

    @Test
    void testMode1() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {5, 4, 3, 2, 0, 1, 5, 5, 5, 5, 1}, true);

        assertIterableEquals(Arrays.asList(5d), vector1.mode());
    }

    @Test
    void testMode2() {
        Double[] vector1 = {5d, 4d, 3d, 2d, 0d, 1d, 5d, 5d, 5d, 5d, 1d};

        assertIterableEquals(Arrays.asList(5d), StandardVector.mode(vector1));
    }

    @Test
    void testTranspose() {

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {5, 4, 3, 2, 0, 1, 5, 5, 5, 5, 1}, true);

        assertEquals(11, vector1.transpose().getColumnNumber());
    }
}

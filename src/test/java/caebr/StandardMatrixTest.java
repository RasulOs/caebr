package caebr;

import caebr.matrix.StandardMatrix;
import caebr.matrix.IMatrix;
import caebr.util.NumberUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class StandardMatrixTest {

    double epsilon = 0.001d;

    @Test
    void testMultiplication1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, 3d},
                {4d, 5d, 6d}
        });

        StandardMatrix<Double> matrix2 = new StandardMatrix<>(new Double[][]{
                {10d, 11d},
                {20d, 21d},
                {30d, 31d}
        });

        matrix1.multiply(matrix2);

        assertArrayEquals(new Double[][]{
                {140d, 146d},
                {320d, 335d}
        }, matrix1.toMatrix());

        assertEquals(2, matrix1.getRowNumber());
        assertEquals(2, matrix1.getColumnNumber());
    }

    @Test
    void testMultiplication2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d},
                {3d, 4d}
        });

        StandardMatrix<Double> matrix2 = new StandardMatrix<>(new Double[][]{
                {5d, 6d},
                {0d, 7d}
        });

        IMatrix<Double> matrix3 = matrix1.multiply(matrix2);

        assertArrayEquals(new Double[][]{
                {5d, 20d},
                {15d, 46d}
        }, matrix3.toMatrix());


    }

    @Test
    void testMultiplication3() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d},
                {3d, 4d},
                {5d, 6d}

        });

        StandardMatrix<Double> matrix2 = new StandardMatrix<>(new Double[][]{
                {5d, 6d},
                {0d, 7d},
                {8d, 9d}
        });

        assertThrows(IllegalArgumentException.class, () -> matrix1.multiply(matrix2));
    }

    @Test
    void testMultiplication4() {

        Double[][] matrix1 = {
                {1d, 2d, 3d},
                {4d, 5d, 6d}
        };

        Double[][] matrix2 = {
                {10d, 11d},
                {20d, 21d},
                {30d, 31d}
        };

        Double[][] matrix3 = StandardMatrix.multiply(matrix1, matrix2);

        assertArrayEquals(new Double[][]{
                {140d, 146d},
                {320d, 335d}
        }, matrix3);
    }

    @Test
    void testMultiplication5() {

        Double[][] matrix1 = {
                {1d, 2d, 3d},
                {4d, 5d, 6d}
        };

        Double[][] matrix2 = {
                {10d, 11d},
                {20d, 21d},
                {30d, 31d},
                {40d, 41d}
        };

        assertThrows(IllegalArgumentException.class, () ->
                StandardMatrix.multiply(matrix1, matrix2));
    }

    @Test
    void testAddition1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, 3d},
                {4d, 5d, 6d}
        });

        StandardMatrix<Double> matrix2 = new StandardMatrix<>(new Double[][]{
                {10d, 11d, 12d},
                {20d, 21d, 22d}
        });

        IMatrix<Double> matrix3 = matrix1.add(matrix2);

        assertArrayEquals(new Double[][]{
                {11d, 13d, 15d},
                {24d, 26d, 28d}
        }, matrix3.toMatrix());
    }

    @Test
    void testAddition2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d},
                {3d, 4d}
        });

        StandardMatrix<Double> matrix2 = new StandardMatrix<>(new Double[][]{
                {5d, 6d},
                {0d, 7d}
        });

        IMatrix<Double> matrix3 = matrix1.add(matrix2);

        assertArrayEquals(new Double[][]{
                {6d, 8d},
                {3d, 11d}
        }, matrix3.toMatrix());
    }

    @Test
    void testAddition3() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d},
                {3d, 4d},
                {5d, 6d}

        });

        StandardMatrix<Double> matrix2 = new StandardMatrix<>(new Double[][]{
                {5d, 6d},
                {0d, 7d}
        });

        assertThrows(IllegalArgumentException.class, () -> matrix1.add(matrix2));
    }

    @Test
    void testSubtraction1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, 3d},
                {4d, 5d, 6d}
        });

        StandardMatrix<Double> matrix2 = new StandardMatrix<>(new Double[][]{
                {10d, 11d, 12d},
                {20d, 21d, 22d}
        });

        IMatrix<Double> matrix3 = matrix1.subtract(matrix2);

        assertArrayEquals(new Double[][]{
                {-9d, -9d, -9d},
                {-16d, -16d, -16d}
        }, matrix3.toMatrix());
    }

    @Test
    void testSubtraction2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d},
                {3d, 4d}
        });

        StandardMatrix<Double> matrix2 = new StandardMatrix<>(new Double[][]{
                {5d, 6d},
                {0d, 7d}
        });

        IMatrix<Double> matrix3 = matrix1.subtract(matrix2);

        assertArrayEquals(new Double[][]{
                {-4d, -4d},
                {3d, -3d}
        }, matrix3.toMatrix());
    }

    @Test
    void testSubtraction3() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d},
                {3d, 4d},
                {5d, 6d}

        });

        StandardMatrix<Double> matrix2 = new StandardMatrix<>(new Double[][]{
                {5d, 6d},
                {0d, 7d}
        });

        assertThrows(IllegalArgumentException.class, () -> matrix1.subtract(matrix2));
    }

    @Test
    void testSum1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, 3d},
                {4d, 5d, 6d}
        });

        assertEquals(21d, matrix1.sum());
    }

    @Test
    void testSum2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, 3d},
                {4d, 5d, 6d}
        });

        assertEquals(5d, matrix1.sum(0));
    }

    @Test
    void testSum3() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, 3d},
                {4d, 5d, 6d}
        });

        assertEquals(16d, matrix1.sum(1, matrix1.getColumnNumber()));
    }

    @Test
    void testSum4() {

        Double[][] matrix1 = {
                {1d, 2d, 3d},
                {4d, 5d, 6d}
        };

        assertEquals(21d, StandardMatrix.sum(matrix1));
    }

    @Test
    void testSum5() {

        Double[][] matrix1 = {
                {1d, 2d, 3d},
                {4d, 5d, 6d}
        };

        assertThrows(IllegalArgumentException.class, () -> StandardMatrix.sum(matrix1, 0, 4));
    }

    @Test
    void testMean1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, 3d},
                {4d, 5d, 6d}
        });

        assertEquals(3.5d, matrix1.mean());
    }

    @Test
    void testMean2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, 3d},
                {4d, 5d, 6d}
        });

        assertEquals(2.5d, matrix1.mean(0));
    }

    @Test
    void testMean3() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, 3d},
                {4d, 5d, 6d}
        });

        assertEquals(4d, matrix1.mean(1, matrix1.getColumnNumber()));
    }

    @Test
    void testMean4() {

        Double[][] matrix1 = {
                {1d, 2d, 3d},
                {4d, 5d, 6d}
        };

        assertEquals(3.5d, StandardMatrix.mean(matrix1));

    }

    @Test
    void testMax1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, 3d},
                {4d, 5d, 6d}
        });

        assertEquals(6d, matrix1.max());
    }

    @Test
    void testMax2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, 3d, 4d},
                {4d, 5d, 6d, 7d}
        });

        assertEquals(4d, matrix1.max(0));
    }

    @Test
    void testMax3() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, 3d, 4d},
                {4d, 5d, 6d, 7d}
        });

        assertEquals(7d, matrix1.max(1, matrix1.getColumnNumber()));
    }

    @Test
    void testMax4() {
        Double[][] matrix1 = {
                {1d, 2d, 3d, 4d},
                {4d, 5d, 10d, 7d}
        };

        assertEquals(10d, StandardMatrix.max(matrix1));
    }

    @Test
    void testMin1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d},
                {4d, 5d, 6d}
        });

        assertEquals(-3d, matrix1.min());
    }

    @Test
    void testMin2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d, 4d},
                {4d, 5d, 6d, 7d}
        });

        assertEquals(1d, matrix1.min(0));
    }

    @Test
    void testMin3() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d, 4d},
                {4d, 5d, 6d, 7d}
        });

        assertEquals(-3d, matrix1.min(1, matrix1.getColumnNumber()));
    }

    @Test
    void testMin4() {

        Double[][] matrix1 = {
                {1d, 2d, -3d, 4d},
                {4d, 5d, 6d, 7d}
        };

        assertEquals(-3d, StandardMatrix.min(matrix1));
    }

    @Test
    void testMedian1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d},
                {4d, 5d, 6d},
                {7d, 8d, 9d},
                {10d, 11d, 12d}
        });

        assertEquals(6.5d, matrix1.median());
    }

    @Test
    void testMedian2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d, 4d},
                {4d, 5d, 6d, 7d}
        });

        assertEquals(2.5d, matrix1.median(0));
    }

    @Test
    void testMedian3() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d, 4d},
                {4d, 5d, 6d, 7d}
        });

        assertEquals(4.5d, matrix1.median(1, matrix1.getColumnNumber()));
    }

    @Test
    void testMedian4() {

        Double[][] matrix1 = {
                {1d, 2d, -3d, 4d},
                {4d, 5d, 6d, 7d}
        };

        assertEquals(4.5d, StandardMatrix.median(matrix1, 1, 4));
    }

    @Test
    void testMode1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d},
                {4d, 5d, 6d},
                {7d, 8d, 9d},
                {10d, 11d, 12d}
        });

        assertIterableEquals(Arrays.asList(), matrix1.mode());
    }

    @Test
    void testMode2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d, 4d},
                {4d, 5d, 6d, 7d}
        });

        assertIterableEquals(Arrays.asList(4d), matrix1.mode(0, matrix1.getColumnNumber()));
    }

    @Test
    void testMode3() {

            Double[][] matrix1 = {
                    {1d, 2d, -3d, 4d},
                    {4d, 5d, 4d, 7d}
            };

            assertIterableEquals(Arrays.asList(4d), StandardMatrix.mode(matrix1, 1, 4));
    }

    @Test
    void testVariance1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d},
                {4d, 5d, 6d},
                {7d, 8d, 9d},
                {10d, 11d, 12d}
        });

        assertTrue(NumberUtils.approximatelyEqual(19.818d, matrix1.variance(), epsilon));
    }

    @Test
    void testVariance2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d, 4d},
                {4d, 5d, 6d, 7d}
        });

        assertTrue(NumberUtils.approximatelyEqual(4.5d, matrix1.variance(0), epsilon));
    }

    @Test
    void testVariance3() {

        Double[][] matrix1 = {
                {1d, 2d, -3d, 4d},
                {4d, 5d, 6d, 7d}
        };

        assertTrue(NumberUtils.approximatelyEqual(4.5d,
                StandardMatrix.variance(matrix1, 0), epsilon));
    }

    @Test
    void testStandardDeviation1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d},
                {4d, 5d, 6d},
                {7d, 8d, 9d},
                {10d, 11d, 12d}
        });

        assertTrue(NumberUtils.approximatelyEqual(4.451d, matrix1.standardDeviation(), epsilon));
    }

    @Test
    void testStandardDeviation2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d, 4d},
                {4d, 5d, 6d, 7d}
        });

        assertTrue(NumberUtils.approximatelyEqual(2.121d, matrix1.standardDeviation(0), epsilon));
    }

    @Test
    void testStandardDeviation3() {

        Double[][] matrix1 = {
                {1d, 2d, -3d, 4d},
                {4d, 5d, 6d, 7d}
        };

        assertTrue(NumberUtils.approximatelyEqual(2.121d,
                StandardMatrix.standardDeviation(matrix1, 0), epsilon));
    }

    @Test
    void testRange1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d},
                {4d, 5d, 6d},
                {7d, 8d, 9d},
                {10d, 11d, 12d}
        });

        assertTrue(NumberUtils.approximatelyEqual(15d, matrix1.range(), epsilon));
    }

    @Test
    void testRange2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d, 4d},
                {4d, 5d, 6d, 7d}
        });

        assertTrue(NumberUtils.approximatelyEqual(3d, matrix1.range(0), epsilon));
    }

    @Test
    void testRange3() {

        Double[][] matrix1 = {
                {1d, 2d, -3d, 4d},
                {4d, 5d, 6d, 7d}
        };

        assertTrue(NumberUtils.approximatelyEqual(3d,
                StandardMatrix.range(matrix1, 0), epsilon));
    }

    @Test
    void testSort1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d},
                {0d, 5d, 6d},
                {7d, 8d, 9d},
                {10d, 11d, 12d}
        });

        matrix1.sort(0);

        assertArrayEquals(new Double[][]{
                {0d, 2d, -3d},
                {1d, 5d, 6d},
                {7d, 8d, 9d},
                {10d, 11d, 12d}
        }, matrix1.toMatrix());
    }

    @Test
    void testSort2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d},
                {0d, 1d, -6d},
                {7d, 8d, 9d},
                {10d, 11d, 12d}
        });

        matrix1.sort(0, matrix1.getColumnNumber());

        assertArrayEquals(new Double[][]{
                {0d, 1d, -6d},
                {1d, 2d, -3d},
                {7d, 8d, 9d},
                {10d, 11d, 12d}
        }, matrix1.toMatrix());
    }

    @Test
    void testSort3() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d},
                {0d, 1d, -6d},
                {7d, 8d, 9d},
                {10d, 11d, 12d}
        });

        matrix1.sort(0, matrix1.getColumnNumber(), false);

        assertArrayEquals(new Double[][]{
                {10d, 11d, 12d},
                {7d, 8d, 9d},
                {1d, 2d, -3d},
                {0d, 1d, -6d}
        }, matrix1.toMatrix());
    }

    @Test
    void testSort4() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d, 4d},
                {0d, 1d, -6d, 5d},
                {7d, 8d, 9d, 6d},
                {10d, 11d, 12d, 7d}
        });

        matrix1.sort(0, 2, false);

        assertArrayEquals(new Double[][]{
                {10d, 11d, -3d, 4d},
                {7d, 8d,   -6d, 5d},
                {1d, 2d,   9d, 6d},
                {0d, 1d,   12d, 7d}
        }, matrix1.toMatrix());
    }

    @Test
    void testSort5() {


        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d, 4d},
                {0d, 1d, -6d, 5d},
                {7d, 8d, 9d, 6d},
                {10d, 11d, 12d, 7d}
        });

        matrix1.sort();

        assertArrayEquals(new Double[][]{
                {0d, 1d, -6d, 4d},
                {1d, 2d, -3d, 5d},
                {7d, 8d, 9d, 6d},
                {10d, 11d, 12d, 7d}
        }, matrix1.toMatrix());
    }

    @Test
    void testSort6() {

        Double[][] matrix1 = {
                {1d, 2d, -3d, 4d},
                {0d, 1d, -6d, 5d},
                {7d, 8d, 9d, 6d},
                {10d, 11d, 12d, 7d}
        };

        StandardMatrix.sort(matrix1);

        assertArrayEquals(new Double[][]{
                {0d, 1d, -6d, 4d},
                {1d, 2d, -3d, 5d},
                {7d, 8d, 9d, 6d},
                {10d, 11d, 12d, 7d}
        }, matrix1);
    }

    @Test
    void testSortWithRowSwap1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d, 4d},
                {0d, 1d, -6d, 5d},
                {7d, 8d, 9d, 6d},
                {10d, 11d, 12d, 7d}
        });

        matrix1.sortWithRowSwap(0, true);

        assertArrayEquals(new Double[][]{
                {0d, 1d, -6d, 5d},
                {1d, 2d, -3d, 4d},
                {7d, 8d, 9d, 6d},
                {10d, 11d, 12d, 7d}
        }, matrix1.toMatrix());
    }

    @Test
    void testSortWithRowSwap2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d, 4d},
                {0d, 1d, -6d, 5d},
                {7d, 8d, 9d, 6d},
                {10d, 11d, 12d, 7d}
        });

        matrix1.sortWithRowSwap(0, false);

        assertArrayEquals(new Double[][]{
                {10d, 11d, 12d, 7d},
                {7d, 8d, 9d, 6d},
                {1d, 2d, -3d, 4d},
                {0d, 1d, -6d, 5d}
        }, matrix1.toMatrix());
    }

    @Test
    void testSortWithRowSwap3() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d, 4d},
                {0d, 1d, -6d, 5d},
                {7d, 8d, 9d, 6d},
                {10d, 11d, 12d, 7d}
        });

        assertThrows(IllegalArgumentException.class,
                () -> matrix1.sortWithRowSwap(4, false));
    }

    @Test
    void testSortWithRowSwap4() {

         Double[][] matrix1 = {
                {1d, 2d, -3d, 4d},
                {0d, 1d, -6d, 5d},
                {7d, 8d, 9d, 6d},
                {10d, 11d, 12d, 7d}
        };

        StandardMatrix.sortWithRowSwap(matrix1, 0, true);

        assertArrayEquals(new Double[][]{
                {0d, 1d, -6d, 5d},
                {1d, 2d, -3d, 4d},
                {7d, 8d, 9d, 6d},
                {10d, 11d, 12d, 7d}
        }, matrix1);
    }

    @Test
    void testMinMaxNormalization1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d},
                {0d, 1d, -6d},
                {7d, 8d, 9d},
                {10d, 11d, 12d}
        });

        matrix1.minMaxNormalization(0, 1, 0);

        assertArrayEquals(new Double[][]{
                {0.1d, 2d, -3d},
                {0d, 1d, -6d},
                {0.7d, 8d, 9d},
                {1d, 11d, 12d}
        }, matrix1.toMatrix());
    }

    @Test
    void testMinMaxNormalization2() {
        Double[][] matrix1 = {
                {1d, 2d, -3d},
                {0d, 1d, -6d},
                {7d, 8d, 9d},
                {10d, 11d, 12d}
        };

        StandardMatrix.minMaxNormalization(matrix1, 0, 1, 0);

        assertArrayEquals(new Double[][]{
                {0.1d, 2d, -3d},
                {0d, 1d, -6d},
                {0.7d, 8d, 9d},
                {1d, 11d, 12d}
        }, matrix1);
    }

    @Test
    void testZScoreStandardization1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  2d, -3d},
                {0d,  1d, -6d},
                {7d,  8d, 9d},
                {10d, 11d, 12d}
        });

        matrix1.zScoreStandardization(0);

        assertTrue(NumberUtils.approximatelyEqual(
                new Double[][]{
                        {-0.729,  2d, -3d},
                        {-0.938,  1d, -6d},
                        {0.521,   8d, 9d},
                        {1.147,   11d, 12d}
                }, matrix1.toMatrix(), epsilon));

    }

    @Test
    void testL0Norm1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  2d, -3d},
                {0d,  1d, -6d},
                {7d,  8d, 9d},
                {10d, 11d, 12d}
        });

        assertEquals(3, matrix1.l0Norm(0));
    }

    @Test
    void testL0Norm2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  2d, -3d},
                {0d,  1d, -6d},
                {7d,  8d, 9d},
                {10d, 11d, 12d}
        });

        assertThrows(IllegalArgumentException.class,
                () -> matrix1.l0Norm(matrix1.getColumnNumber()));
    }

    @Test
    void testL0Norm3() {
        Double[][] matrix1 = {
                {1d,  2d, -3d},
                {0d,  1d, -6d},
                {7d,  8d, 9d},
                {10d, 11d, 12d}
        };

        assertEquals(3, StandardMatrix.l0Norm(matrix1, 0));
    }



    @Test
    void testL1Norm1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  2d, -3d},
                {0d,  1d, -6d},
                {7d,  8d, 9d},
                {-10d, 11d, 12d}
        });

        assertEquals(18, matrix1.l1Norm(0));
    }

    @Test
    void testL1Norm2() {
        Double[][] matrix1 = {
                {1d,  2d, -3d},
                {0d,  1d, -6d},
                {7d,  8d, 9d},
                {-10d, 11d, 12d}
        };

        assertEquals(18, StandardMatrix.l1Norm(matrix1, 0));
    }

    @Test
    void testL2Norm1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  2d, -3d},
                {0d,  1d, -6d},
                {7d,  8d, 9d},
                {-10d, 11d, 12d}
        });

        assertTrue(NumberUtils.approximatelyEqual(Math.sqrt(150), matrix1.l2Norm(0), epsilon));
    }

    @Test
    void testL2Norm2() {
        Double[][] matrix1 = {
                {1d,  2d, -3d},
                {0d,  1d, -6d},
                {7d,  8d, 9d},
                {-10d, 11d, 12d}
        };

        assertTrue(NumberUtils.approximatelyEqual(Math.sqrt(150), StandardMatrix.l2Norm(matrix1, 0), epsilon));
    }

    @Test
    void testLInfinityNorm() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  2d, -3d},
                {0d,  1d, -6d},
                {9d,  8d, 9d},
                {-10d, 11d, 12d}
        });

        assertEquals(10d, matrix1.lInfinityNorm(0));
    }

    @Test
    void testLInfinityNorm2() {
        Double[][] matrix1 = {
                {1d,  2d, -3d},
                {0d,  1d, -6d},
                {9d,  8d, 9d},
                {-10d, 11d, 12d}
        };

        assertEquals(10d, StandardMatrix.lInfinityNorm(matrix1, 0));
    }

    @Test
    void testIsSquare1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  2d, -3d},
                {0d,  1d, -6d},
                {9d,  8d, 9d},
                {-10d, 11d, 12d}
        });

        assertFalse(matrix1.isSquare());
    }

    @Test
    void testIsSquare2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  2d, -3d},
                {0d,  1d, -6d},
                {9d,  8d, 9d}
        });

        assertTrue(matrix1.isSquare());
    }

    @Test
    void testIsSymmetric1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  2d, -3d},
                {2d,  1d, -6d},
                {9d,  8d, 9d},
                {-10d, 11d, 12d}
        });

        assertFalse(matrix1.isSymmetric());
    }

    @Test
    void testIsSymmetric2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {2d,  3d, 6d},
                {3d,  4d, 5d},
                {6d,  5d, 9d}
        });

        assertTrue(matrix1.isSymmetric());
    }

    @Test
    void testIsSymmetric3() {
        Double[][] matrix1 = {
                {2d,  3d, 6d},
                {3d,  4d, 5d},
                {6d,  5d, 9d}
        };

        assertTrue(StandardMatrix.isSymmetric(matrix1));
    }

    @Test
    void testIsIdentity1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  2d, -3d},
                {2d,  1d, -6d},
                {9d,  8d, 9d},
                {-10d, 11d, 12d}
        });

        assertFalse(matrix1.isIdentity());
    }

    @Test
    void testIsIdentity2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  0d, 0d},
                {0d,  1d, 0d},
                {0d,  0d, 1d}
        });

        assertTrue(matrix1.isIdentity());

    }

    @Test
    void testIsIdentity3() {
        Double[][] matrix1 = {
                {1d,  0d, 0d},
                {0d,  1d, 0d},
                {0d,  0d, 1d}
        };

        assertTrue(StandardMatrix.isIdentity(matrix1));
    }

    @Test
    void testIsDiagonal1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  2d, -3d},
                {2d,  1d, -6d},
                {9d,  8d, 9d},
                {-10d, 11d, 12d}
        });

        assertFalse(matrix1.isDiagonal());
    }

    @Test
    void testIsDiagonal2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {10d,  0d, 0d},
                {0d,  2d, 0d},
                {0d,  0d, 1d}
        });

        assertTrue(matrix1.isDiagonal());
    }

    @Test
    void testIsDiagonal3() {
        Double[][] matrix1 = {
                {10d,  0d, 0d},
                {0d,  2d, 0d},
                {0d,  0d, 1d}
        };

        assertTrue(StandardMatrix.isDiagonal(matrix1));
    }

    @Test
    void testIsAntiDiagonal1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  2d, -3d},
                {2d,  1d, -6d},
                {9d,  8d, 9d},
                {-10d, 11d, 12d}
        });

        assertFalse(matrix1.isAntiDiagonal());
    }

    @Test
    void testIsAntiDiagonal2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {0d,  0d, 1d},
                {0d,  2d, 0d},
                {10d,  0d, 0d}
        });

        assertTrue(matrix1.isAntiDiagonal());
    }

    @Test
    void testIsAntiDiagonal3() {
        Double[][] matrix1 = {
                {0d,  0d, 1d},
                {0d,  2d, 0d},
                {10d,  0d, 0d}
        };

        assertTrue(StandardMatrix.isAntiDiagonal(matrix1));
    }

    @Test
    void testDropColumn1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {0d,  0d, 1d},
                {0d,  2d, 0d},
                {10d,  0d, 0d}
        });

        matrix1.dropColumn(1);

        assertArrayEquals(new Double[][]{
                {0d,  1d},
                {0d,  0d},
                {10d,  0d}
        }, matrix1.toMatrix());
    }

    @Test
    void testDropColumn2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {0d,  0d, 1d},
                {0d,  2d, 0d},
                {10d,  0d, 0d}
        });

        matrix1.dropColumn();

        assertArrayEquals(new Double[][]{
                {0d,  0d},
                {0d,  2d},
                {10d,  0d}
        }, matrix1.toMatrix());
    }

    @Test
    void testDropColumn3() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {0d,  0d, 1d},
                {0d,  2d, 0d},
                {10d,  0d, 0d}
        });

        assertThrows(IllegalArgumentException.class, () -> matrix1.dropColumn(3));
    }

    @Test
    void testSetColumn1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {0d,  0d, 1d},
                {0d,  2d, 0d},
                {10d,  0d, 0d}
        });

        matrix1.setColumn(new Double[]{1d, 2d, 3d}, 1);

        assertArrayEquals(new Double[][]{
                {0d,  1d, 1d},
                {0d,  2d, 0d},
                {10d,  3d, 0d}
        }, matrix1.toMatrix());
    }

    @Test
    void testPutColumn() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {0d,  0d, 1d},
                {0d,  2d, 0d},
                {10d,  0d, 0d}
        });

        matrix1.putColumn(new Double[]{1d, 2d, 3d});

        assertArrayEquals(new Double[][]{
                {0d,  0d, 1d, 1d},
                {0d,  2d, 0d, 2d},
                {10d, 0d, 0d, 3d}
        }, matrix1.toMatrix());
    }

    @Test
    void testPopColumn1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {0d,  0d, 1d},
                {0d,  2d, 0d},
                {10d,  0d, 0d}
        });

        Double[] column = matrix1.popColumn(1);

        assertArrayEquals(new Double[]{0d, 2d, 0d}, column);
    }

    @Test
    void testPopColumn2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {0d,  0d, 1d},
                {0d,  2d, 0d},
                {10d,  0d, 0d}
        });

        Double[] column = matrix1.popColumn();

        assertArrayEquals(new Double[]{1d, 0d, 0d}, column);
    }

    @Test
    void testMap1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {0d,  0d, 1d},
                {0d,  -2d, 0d},
                {10d,  0d, 0d}
        });

        matrix1.map(0, matrix1.getColumnNumber(), x -> x * x);

        assertArrayEquals(new Double[][]{
                {0d,  0d, 1d},
                {0d,  4d, 0d},
                {100d,  0d, 0d}
        }, matrix1.toMatrix());
    }

    @Test
    void testMap2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {0d,  0d, 1d},
                {0d,  -2d, 0d},
                {10d,  0d, 0d}
        });

        matrix1.map(1, x -> x * x);

        assertArrayEquals(new Double[][]{
                {0d,  0d, 1d},
                {0d,  4d, 0d},
                {10d,  0d, 0d}
        }, matrix1.toMatrix());
    }

    @Test
    void testMap3() {
        Double[][] matrix1 = {
                {0d,  0d, 1d},
                {0d,  -2d, 0d},
                {10d,  0d, 0d}
        };

        StandardMatrix.map(matrix1, 1, x -> x * x);

        assertArrayEquals(new Double[][]{
                {0d,  0d, 1d},
                {0d,  4d, 0d},
                {10d,  0d, 0d}
        }, matrix1);
    }

    @Test
    void testMapRow1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {2d,  4d, 1d},
                {0d,  -2d, 0d},
                {10d,  0d, 0d}
        });

        matrix1.mapRow(0, x -> x * x);

        assertArrayEquals(new Double[][]{
                {4d,  16d, 1d},
                {0d,  -2d, 0d},
                {10d,  0d, 0d}
        }, matrix1.toMatrix());
    }

    @Test
    void testMapRow2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {2d,  4d, 1d},
                {0d,  -2d, 0d},
                {10d,  0d, 0d}
        });

        matrix1.mapRow(0, 1, x -> x * x);

        assertArrayEquals(new Double[][]{
                {4d,  16d, 1d},
                {0d,  -2d, 0d},
                {10d,  0d, 0d}
        }, matrix1.toMatrix());
    }

    @Test
    void testMapRow3() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {2d, 4d, 1d},
                {0d, -2d, 0d},
                {10d, 0d, 3d}
        });

        matrix1.mapRow(0, matrix1.getRowNumber(), x -> x * x);

        assertArrayEquals(new Double[][]{
                {4d, 16d, 1d},
                {0d, 4d, 0d},
                {100d, 0d, 9d}
        }, matrix1.toMatrix());
    }

    @Test
    void testMapRow4() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {2d, 4d, 1d},
                {0d, -2d, 0d},
                {10d, 0d, 3d}
        });

        assertThrows(IllegalArgumentException.class,
                () -> matrix1.mapRow(0, matrix1.getRowNumber() + 1, x -> x * x));
    }

    @Test
    void testMapRow5() {
        Double[][] matrix1 = {
                {2d, 4d, 1d},
                {0d, -2d, 0d},
                {10d, 0d, 3d}
        };

        StandardMatrix.mapRow(matrix1, 0, 3, x -> x * x);

        assertArrayEquals(new Double[][]{
                {4d, 16d, 1d},
                {0d, 4d, 0d},
                {100d, 0d, 9d}
        }, matrix1);
    }

    @Test
    void testReduce1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {0d,  0d, 1d},
                {2d,  -2d, 0d},
                {10d,  0d, 0d}
        });

        assertEquals(12d, matrix1.reduce(0, Double::sum));
    }

    @Test
    void testReduce2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {0d,  0d, 1d},
                {2d,  -2d, 0d},
                {10d,  0d, 0d}
        });

        assertEquals(11d, matrix1.reduce(0, matrix1.getColumnNumber(), Double::sum));
    }

    @Test
    void testReduce3() {
        Double[][] matrix1 = {
                {0d,  0d, 1d},
                {2d,  -2d, 0d},
                {10d,  0d, 0d}
        };

        assertEquals(12d, StandardMatrix.reduce(matrix1, 0, Double::sum));
    }

    @Test
    void testDistinct1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {0d,  0d, 1d},
                {2d,  -2d, 0d},
                {10d,  0d, 0d}
        });

        assertIterableEquals(Arrays.asList(0d, 2d, 10d, -2d, 1d), matrix1.distinct());
    }

    @Test
    void testDistinct2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {0d,  0d, 1d},
                {2d,  -2d, 0d},
                {10d,  0d, 0d}
        });

        assertIterableEquals(Arrays.asList(0d, 2d, 10d), matrix1.distinct(0));
    }

    @Test
    void testDistinct3() {
        Double[][] matrix1 = {
                {0d,  0d, 1d},
                {2d,  -2d, 0d},
                {10d,  0d, 0d}
        };

        assertIterableEquals(Arrays.asList(0d, 2d, 10d, -2d, 1d), StandardMatrix.distinct(matrix1));
    }

    @Test
    void testToList1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {0d,  0d, 1d},
                {2d,  -2d, 0d},
                {10d,  0d, 0d}
        });

        assertIterableEquals(Arrays.asList(0d, 2d, 10d, 0d, -2d, 0d, 1d, 0d, 0d), matrix1.toList());
    }

    @Test
    void testToList2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {0d,  0d, 1d},
                {2d,  -2d, 0d},
                {10d,  0d, 0d}
        });

        assertIterableEquals(Arrays.asList(0d, 2d, 10d), matrix1.toList(0));
    }

    @Test
    void testToList3() {

        Double[][] matrix1 = {
                {0d,  0d, 1d},
                {2d,  -2d, 0d},
                {10d,  0d, 0d}
        };

        assertIterableEquals(Arrays.asList(0d, 2d, 10d, 0d, -2d, 0d, 1d, 0d, 0d), StandardMatrix.toList(matrix1));
    }

    @Test
    void testIsUpperTriangular1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  2d, 3d},
                {0d,  5d, 6d},
                {0d,  0d, 9d}
        });

        assertTrue(matrix1.isUpperTriangular());
    }

    @Test
    void testIsUpperTriangular2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  2d, 3d},
                {0d,  5d, 6d},
                {0d,  0d, 9d},
                {0d,  0d, 0d}
        });

        assertFalse(matrix1.isUpperTriangular());
    }

    @Test
    void tesIsUpperTriangular3() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  2d, 3d},
                {0d,  5d, 6d},
                {0d,  0d, 0d}
        });

        assertTrue(matrix1.isUpperTriangular());
    }

    @Test
    void testIsLowerTriangular1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  0d, 0d},
                {2d,  5d, 0d},
                {3d,  6d, 9d}
        });

        assertTrue(matrix1.isLowerTriangular());
    }

    @Test
    void testIsLowerTriangular2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  0d, 0d},
                {2d,  5d, 0d},
                {3d,  6d, 9d},
                {0d,  0d, 0d}
        });

        assertFalse(matrix1.isLowerTriangular());
    }

    @Test
    void testIsLowerTriangular3() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {0d,  0d, 0d},
                {2d,  5d, 0d},
                {3d,  6d, 9d}
        });

        assertTrue(matrix1.isLowerTriangular());
    }

    @Test
    void testReplaceRow1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  0d, 0d},
                {2d,  5d, 0d},
                {3d,  6d, 9d}
        });

        matrix1.replaceRow(1, new Double[]{1d, 2d, 3d});

        assertArrayEquals(new Double[][]{
                {1d,  0d, 0d},
                {1d,  2d, 3d},
                {3d,  6d, 9d}
        }, matrix1.toMatrix());
    }

    @Test
    void testReplaceRow2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  0d, 0d},
                {2d,  5d, 0d},
                {3d,  6d, 9d}
        });

        assertThrows(IllegalArgumentException.class,
                () -> matrix1.replaceRow(3, new Double[]{1d, 2d, 3d}));
    }

    @Test
    void testReplaceRow3() {
        Double[][] matrix1 = {
                {1d,  0d, 0d},
                {2d,  5d, 0d},
                {3d,  6d, 9d}
        };

        StandardMatrix.replaceRow(matrix1, 1, new Double[]{1d, 2d, 3d});

        assertArrayEquals(new Double[][]{
                {1d,  0d, 0d},
                {1d,  2d, 3d},
                {3d,  6d, 9d}
        }, matrix1);
    }

    @Test
    void testSwapRows1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  0d, 0d},
                {2d,  5d, 0d},
                {3d,  6d, 9d}
        });

        matrix1.swapRows(0, 2);

        assertArrayEquals(new Double[][]{
                {3d,  6d, 9d},
                {2d,  5d, 0d},
                {1d,  0d, 0d}
        }, matrix1.toMatrix());
    }

    @Test
    void testSwapRows2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  0d, 0d},
                {2d,  5d, 0d},
                {3d,  6d, 9d}
        });

        assertThrows(IllegalArgumentException.class,
                () -> matrix1.swapRows(0, 3));
    }

    @Test
    void testDeterminant1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  0d, 0d},
                {2d,  5d, 0d},
                {3d,  6d, 9d}
        });

        assertEquals(45d, matrix1.determinant(), epsilon);
    }

    @Test
    void testDeterminant2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  0d, 0d},
                {2d,  5d, 0d},
                {3d,  6d, 9d},
                {0d,  0d, 0d}
        });

        assertThrows(IllegalArgumentException.class,
                () -> matrix1.determinant());
    }

    @Test
    void testDeterminant3() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {4d, 3d, 2d, 2d},
                {0d, 1d, -3d, 3d},
                {0d, -1d, 3d, 3d},
                {0d, 3d, 1d, 1d}
        });

        assertTrue(NumberUtils.approximatelyEqual(-240d, matrix1.determinant(), epsilon));
    }

    @Test
    void testDeterminant4() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {4d, 3d, 2d, 2d},
                {0d, 1d, -3d, 3d},
                {0d, -1d, 3d, 3d},
                {0d, 3d, 1d, 1d},
                {0d, 3d, 1d, 1d}
        });

        assertThrows(IllegalArgumentException.class,
                () -> matrix1.determinant());
    }

    @Test
    void testDeterminant5() {
        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {4d, -3d, 5d},
                {1d, 0d, 3d},
                {-1d, 5d, 2d}
        });

        assertTrue(NumberUtils.approximatelyEqual(-20d, matrix1.determinant(), epsilon));

    }

    @Test
    void testDeterminant6() {
        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 1d, 1d, -1d},
                {1d, 1d, -1d, 1d},
                {1d, -1d, 1d, 1d},
                {-1d, 1d, 1d,  1d}
        });

        assertTrue(NumberUtils.approximatelyEqual(-16d, matrix1.determinant(), epsilon));
    }

    @Test
    void testDeterminant7() {
        StandardMatrix<Integer> matrix1 = new StandardMatrix<>(new Integer[][]{
                {4, 2, 3, 9, 9},
                {-2, 4, 7, -7, -7},
                {2, 3, 11, 1, 1},
                {1, 1, 2, -3, -1},
                {1, 1, 2, 0, 1}
        });

        assertTrue(NumberUtils.approximatelyEqual(-142d, matrix1.determinant(), epsilon));
    }

    @Test
    void testAddRows1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, 3d},
                {4d, 5d, 6d},
                {7d, 8d, 9d}
        });

        matrix1.addRows(1, 2);

        assertArrayEquals(new Double[][]{
                {1d, 2d, 3d},
                {11d, 13d, 15d},
                {7d, 8d, 9d}
        }, matrix1.toMatrix());
    }

    @Test
    void testAddRows2() {

        Double[][] matrix1 = {
                {1d, 2d, 3d},
                {4d, 5d, 6d},
                {7d, 8d, 9d}
        };

        StandardMatrix.addRows(matrix1, 1, 2);

        assertArrayEquals(new Double[][]{
                {1d, 2d, 3d},
                {11d, 13d, 15d},
                {7d, 8d, 9d}
        }, matrix1);
    }

    @Test
    void testAddRows3() {

        Double[][] matrix1 = {
                {1d, 2d, 3d},
                {4d, 5d, 6d},
                {7d, 8d, 9d}
        };

        assertThrows(IllegalArgumentException.class,
                () -> StandardMatrix.addRows(matrix1, 3, 2));
    }

    @Test
    void testMultiplyRows1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, 3d},
                {4d, 5d, 6d},
                {7d, 8d, 9d}
        });

        matrix1.multiplyRows(1, 2);

        assertArrayEquals(new Double[][]{
                {1d, 2d, 3d},
                {28d, 40d, 54d},
                {7d, 8d, 9d}
        }, matrix1.toMatrix());
    }

    @Test
    void testMultiplyRows2() {

        Double[][] matrix1 = {
                {1d, 2d, 3d},
                {4d, 5d, 6d},
                {7d, 8d, 9d}
        };

        StandardMatrix.multiplyRows(matrix1, 1, 2);

        assertArrayEquals(new Double[][]{
                {1d, 2d, 3d},
                {28d, 40d, 54d},
                {7d, 8d, 9d}
        }, matrix1);
    }

    @Test
    void testMultiplyRows3() {

        Double[][] matrix1 = {
                {1d, 2d, 3d},
                {4d, 5d, 6d},
                {7d, 8d, 9d}
        };

        assertThrows(IllegalArgumentException.class,
                () -> StandardMatrix.multiplyRows(matrix1, 3, 2));
    }

    @Test
    void testAdd1() {
        Double[][] matrix1 = {
                {1d, 2d, 3d},
                {4d, 5d, 6d}
        };

        Double[][] matrix2 = {
                {1d, 2d, 3d},
                {4d, 5d, 6d}
        };

        Double[][] result = StandardMatrix.add(matrix1, matrix2);

        assertArrayEquals(new Double[][]{
                {2d, 4d, 6d},
                {8d, 10d, 12d}
        }, result);
    }

    @Test
    void testAdd2() {
        Double[][] matrix1 = {
                {1d, 2d, 3d},
                {4d, 5d, 6d}
        };

        Double[][] matrix2 = {
                {1d, 2d, 3d}
        };

        assertThrows(IllegalArgumentException.class,
                () -> StandardMatrix.add(matrix1, matrix2));
    }

    @Test
    void testSubtract1() {
        Double[][] matrix1 = {
                {1d, 2d, 3d},
                {4d, 5d, 6d}
        };

        Double[][] matrix2 = {
                {1d, 2d, 3d},
                {4d, 5d, 6d}
        };

        Double[][] result = StandardMatrix.subtract(matrix1, matrix2);

        assertArrayEquals(new Double[][]{
                {0d, 0d, 0d},
                {0d, 0d, 0d}
        }, result);
    }

    @Test
    void testSubtract2() {
        Double[][] matrix1 = {
                {1d, 2d, 3d},
                {4d, 5d, 6d}
        };

        Double[][] matrix2 = {
                {1d, 2d, 3d}
        };

        assertThrows(IllegalArgumentException.class,
                () -> StandardMatrix.subtract(matrix1, matrix2));
    }
}


package caebr;

import caebr.matrix.StandardMatrix;
import caebr.matrix.IMatrix;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class StandardMatrixTest {

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
    void testVariance1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d},
                {4d, 5d, 6d},
                {7d, 8d, 9d},
                {10d, 11d, 12d}
        });

        TestUtils.approximatelyEquals(19.818d, matrix1.variance());
    }

    @Test
    void testVariance2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d, 4d},
                {4d, 5d, 6d, 7d}
        });

        TestUtils.approximatelyEquals(4.5d, matrix1.variance(0));
    }

    @Test
    void testStandardDeviation1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d},
                {4d, 5d, 6d},
                {7d, 8d, 9d},
                {10d, 11d, 12d}
        });

        TestUtils.approximatelyEquals(4.451d, matrix1.standardDeviation());
    }

    @Test
    void testStandardDeviation2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d, 4d},
                {4d, 5d, 6d, 7d}
        });

        TestUtils.approximatelyEquals(2.121d, matrix1.standardDeviation(0));
    }

    @Test
    void testRange1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d},
                {4d, 5d, 6d},
                {7d, 8d, 9d},
                {10d, 11d, 12d}
        });

        TestUtils.approximatelyEquals(15d, matrix1.range());
    }

    @Test
    void testRange2() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, -3d, 4d},
                {4d, 5d, 6d, 7d}
        });

        TestUtils.approximatelyEquals(3d, matrix1.range(0));
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
    void testZScoreStandardization1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  2d, -3d},
                {0d,  1d, -6d},
                {7d,  8d, 9d},
                {10d, 11d, 12d}
        });

        matrix1.zScoreStandardization(0);

        TestUtils.approximatelyEquals(
                new Double[][]{
                        {-0.729,  2d, -3d},
                        {-0.938,  1d, -6d},
                        {0.521,   8d, 9d},
                        {1.147,   11d, 12d}
                }, matrix1.toMatrix());

    }

    @Test
    void testL0Norm() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  2d, -3d},
                {0d,  1d, -6d},
                {7d,  8d, 9d},
                {10d, 11d, 12d}
        });

        assertEquals(3, matrix1.l0Norm(0));
    }

    @Test
    void testL1Norm() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  2d, -3d},
                {0d,  1d, -6d},
                {7d,  8d, 9d},
                {-10d, 11d, 12d}
        });

        assertEquals(18, matrix1.l1Norm(0));
    }

    @Test
    void testL2Norm() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d,  2d, -3d},
                {0d,  1d, -6d},
                {7d,  8d, 9d},
                {-10d, 11d, 12d}
        });

        TestUtils.approximatelyEquals(Math.sqrt(150), matrix1.l2Norm(0));
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
}


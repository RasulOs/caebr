package caebr;

import caebr.matrix.StandardMatrix;
import caebr.matrix.IMatrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class StandardMatrixTest {

    @Test
    public void testMultiplication1() {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 2d, 3d},
                {4d, 5d, 6d}
        });

        StandardMatrix<Double> matrix2 = new StandardMatrix<>(new Double[][]{
                {10d, 11d},
                {20d, 21d},
                {30d, 31d}
        });

        IMatrix<Double> matrix3 = matrix1.multiply(matrix2);


        assertArrayEquals(new Double[][]{
                {140d, 146d},
                {320d, 335d}
        }, matrix1.toMatrix());

    }



}

package caebr;

import caebr.matrix.StandardMatrix;
import caebr.vector.StandardVector;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {1d, 1d, -1d},
                {1d, 2d, 0d},
                {-1d, 0d, 5d}
        });

        System.out.println(matrix1.l0Norm(0));
        System.out.println(matrix1.l0Norm(2));

        System.out.println(matrix1.isSymmetric());
        System.out.println(matrix1.isIdentity());
        System.out.println(matrix1.isDiagonal());
        System.out.println(matrix1.isAntiDiagonal());

        System.out.println(Arrays.toString(matrix1.toIntegerArray(0, 3)));

        System.out.println(Arrays.toString(matrix1.popColumn()));
        System.out.println(Arrays.toString(matrix1.popColumn()));
        System.out.println(matrix1);

        StandardMatrix<Double> matrix2 = new StandardMatrix<>(new Double[][]{
                {1d, 1d, -1d},
                {1d, 2d, 0d},
                {-1d, 0d, 5d}
        });

        System.out.println(matrix1.transpose().multiply(matrix2));





    }
}

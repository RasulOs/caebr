import matrix.StandardMatrix;
import vector.StandardVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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





//        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6});
//
//        System.out.println(vector1);
//
//        System.out.println(vector1.getRowNumber());
//        System.out.println(vector1.getColumnNumber());
//
//
//        vector1.drop(2, 4);
//        System.out.println(vector1);
//
//        System.out.println(vector1.l0Norm());
//
//        vector1.filter(x -> x > 5);
//        System.out.println(vector1);
//
//        vector1.put(new Integer[] {7, 8, 9, 10, 11});
//
//        System.out.println(vector1);
//
//
//        System.out.println(vector1.subtract(10));


    }
}

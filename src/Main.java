import matrix.StandardMatrix;
import vector.StandardVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

//        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
//                {-10d, 2d, 3d},
//                {4d, 5d, 6d},
//                {7d, 8d, 9d}
//        });
//
//        System.out.println(matrix1.l0Norm(0));
//        System.out.println(matrix1.l1Norm(0));
//        System.out.println(matrix1.l2Norm(0));
//        System.out.println(matrix1.lInfinityNorm(0));
//
//        System.out.println(matrix1);
//        System.out.println(
//                matrix1.setColumn(new Double[] {1.0, 2.0, 3.0}, 2)
//        );
//
//        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 3, 1, 0});
//
//        System.out.println(vector1.distinct());
//
//        System.out.println(matrix1.isIdentity() + "\n");
//
//        StandardMatrix<Integer> matrix2 = new StandardMatrix<>(new Integer[][]
//                        {{1, 0, 0},
//                        {0, 1, 0},
//                        {0, 0, 1}});
//
//        System.out.println(matrix2.isIdentity());
//        System.out.println(matrix2.isDiagonal());
//        System.out.println(matrix2.isAntiDiagonal());
//        System.out.println("isSymmetric: " + matrix2.isSymmetric() + "\n");
//
//        StandardMatrix<Integer> matrix3 = new StandardMatrix<>(new Integer[][]
//                        {{0, 0, 1},
//                        {0, 1, 0},
//                        {1, 0, 0}});
//
//        System.out.println(matrix3.isIdentity());
//        System.out.println(matrix3.isDiagonal());
//        System.out.println(matrix3.isAntiDiagonal());
//        System.out.println("isSymmetric: " + matrix3.isSymmetric() + "\n");
//
//
//        StandardMatrix<Integer> matrix4 = new StandardMatrix<>(new Integer[][]
//                        {{1, 3, 6},
//                        {3, 4, 5},
//                        {6, 5, 9}});
//
//        System.out.println(matrix4.isSymmetric());

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 4, 5, 6});

        System.out.println(vector1);

        System.out.println(vector1.getRowNumber());
        System.out.println(vector1.getColumnNumber());


        vector1.drop(2, 4);
        System.out.println(vector1);

        System.out.println(vector1.l0Norm());

        vector1.filter(x -> x > 5);
        System.out.println(vector1);

        vector1.put(new Integer[] {7, 8, 9, 10, 11});

        System.out.println(vector1);


        System.out.println(vector1.subtract(10));


    }
}

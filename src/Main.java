import matrix.StandardMatrix;
import vector.StandardVector;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        StandardMatrix<Double> matrix1 = new StandardMatrix<>(new Double[][]{
                {-10d, 2d, 3d},
                {4d, 5d, 6d},
                {7d, 8d, 9d}
        });

        System.out.println(matrix1.l0Norm(0));
        System.out.println(matrix1.l1Norm(0));
        System.out.println(matrix1.l2Norm(0));
        System.out.println(matrix1.lInfinityNorm(0));

        System.out.println(matrix1);
        System.out.println(
                matrix1.setColumn(new Double[] {1.0, 2.0, 3.0}, 2)
        );

        StandardVector<Integer> vector1 = new StandardVector<>(new Integer[] {1, 2, 3, 3, 1, 0});

        System.out.println(vector1.distinct());

        System.out.println(matrix1.isIdentity());

        StandardMatrix<Integer> matrix2 = new StandardMatrix<>(new Integer[][]
                        {{1, 0, 0},
                        {0, 1, 0},
                        {0, 0, 1}});

        System.out.println(matrix2.isIdentity());

    }
}

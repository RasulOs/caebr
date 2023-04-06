import matrix.StandardMatrix;
import vector.StandardVector;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        StandardMatrix<Integer> matrix1 = new StandardMatrix<>(new Integer[][]{
                {-10, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });

        System.out.println(matrix1.l0Norm(0));
        System.out.println(matrix1.l1Norm(0));
        System.out.println(matrix1.l2Norm(0));
        System.out.println(matrix1.lInfinityNorm(0));
    }
}

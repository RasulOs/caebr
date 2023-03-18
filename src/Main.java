import matrix.BasicMatrix;
import vector.BasicVector;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        BasicVector<Integer> vector = new BasicVector<Integer>(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});

        System.out.println(vector.toString());

        vector.add(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});

        System.out.println(vector.toString());

        vector.subtract(new Integer[] {2, 6, 0, 2003, 3123, 30, 7, 89, 91, -100});

        System.out.println(vector.toString());

        System.out.println(Arrays.toString(vector.toArray()));
        System.out.println(Arrays.toString(vector.toIntegerArray()));
        System.out.println(Arrays.toString(vector.toLongArray()));
        System.out.println(Arrays.toString(vector.toShortArray()));
        System.out.println(Arrays.toString(vector.toByteArray()));

        System.out.println(
                Arrays.toString(
                        vector
                                .slice(0, 7)
                                .toArray()
                )
        );

        System.out.println(
                Arrays.toString(
                        vector
                                .minMaxNormalization(0, 1)
                                .toArray()
                )
        );

        BasicVector<Double> vector2 =
                new BasicVector<Double>(new Double[] {1.0, 1.0, 20.0, 3.0,  4.0, 5.0, 6.0, 7.0, 8.0, 100.0, 321.00, 9.0, 10.0, 10.0, 10.0});


        System.out.println(vector2.standardDeviation());
        System.out.println(vector2.variance());
        System.out.println(vector2.zScoreStandardization().toString());

        System.out.println("\n");

        BasicMatrix<Integer> matrix = new BasicMatrix<Integer>(new Integer[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });

        System.out.println(
                matrix.dot(new Integer[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        }) .toString());

    }
}

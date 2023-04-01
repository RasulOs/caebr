import matrix.StandardMatrix;
import vector.StandardVector;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        StandardVector<Integer> vector = new StandardVector<Integer>(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});

        System.out.println(vector.l1Norm());
        System.out.println(vector.sum());
        System.out.println(vector.l2Norm());
        System.out.println(vector.lInfinityNorm());

        System.out.println(vector.toString());
        System.out.println(
                vector
                .map(d -> d * 2)
                .toString());


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

        StandardVector<Double> vector2 =
                new StandardVector<Double>(new Double[] {1.0, 1.0, 20.0, 3.0,  4.0, 5.0, 6.0, 7.0, 8.0, 100.0, 321.00, 9.0, 10.0, 10.0, 10.0});


        System.out.println(vector2.standardDeviation());
        System.out.println(vector2.variance());
        System.out.println(vector2.zScoreStandardization().toString());


        System.out.println("\n");

        StandardMatrix<Integer> matrix = new StandardMatrix<Integer>(new Integer[][]{
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

        System.out.println();

        System.out.println(
                "max 0, columnNumber: " + matrix.max(0, matrix.getColumnNumber()) + "\n" +
                        "min 0, 2: " + matrix.min(0, 2) + "\n" +
                        "mean 0:  " + matrix.mean(0) + "\n" +
                        "sum 0, columnNumber: " + matrix.sum(0, matrix.getColumnNumber()) + "\n" +
                        "range 0, columnNumber: " + matrix.range(0, matrix.getColumnNumber()) + "\n" +
                        "isSquare: " + matrix.isSquare() + "\n"
        );

        System.out.println();

        System.out.println(Arrays.toString(matrix.toArray(0, 2)));

        StandardMatrix<Integer> matrix2 = new StandardMatrix<>(new Integer[][] {
                {1, 2, 3},
                {1, 5, 6},
                {7, 8, 9}
        });

        System.out.println(matrix2.mode());

        System.out.println(matrix2.variance());

        System.out.println(matrix2.standardDeviation());

        System.out.println(matrix2.median());

        System.out.println(matrix2);
        System.out.println(matrix2.dropColumn());
        System.out.println(matrix2.putColumn(new Integer[] {99, 77, 100}));
        System.out.println(matrix2.setColumn(new Integer[] {0, 9, 0}, 0));

        System.out.println(matrix2.sort(0, matrix2.getColumnNumber()));

        System.out.println();

        System.out.println(matrix2);
        System.out.println(matrix2.standardDeviation(1));
//        System.out.println(matrix2.zScoreStandardization());
        System.out.println(matrix2.minMaxNormalization(2));
        System.out.println(matrix2.transpose());

        System.out.println(
                matrix2
                        .map(1, matrix2.getColumnNumber(), d -> d * 2)
        );

    }
}

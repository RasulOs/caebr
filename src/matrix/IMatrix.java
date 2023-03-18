package matrix;

import java.util.List;

public interface IMatrix<T extends Number> {

    IMatrix<T> add(T[][] matrix);

    IMatrix<T> subtract(T[][] matrix);

    IMatrix<T> dot(T[][] matrix);

    IMatrix<T> add(IMatrix<T> iMatrix);

    IMatrix<T> subtract(IMatrix<T> iMatrix);

    IMatrix<T> transpose();

    IMatrix<T> dot(IMatrix<T> iMatrix);

    Double sum();

    Double mean();

    Double max();

    Double min();

    Double median();

    List<Double> mode();

    Double variance();

    Double standardDeviation();

    Double range();

    IMatrix<T> sort();

    IMatrix<T> minMaxNormalization(long min, long max);

    IMatrix<T> minMaxNormalization();

    IMatrix<T> zScoreStandardization();

    int getRowNumber();

    int getColumnNumber();

    boolean isSquare();

    boolean isSymmetric();

    boolean isIdentity();

    boolean isDiagonal();

    boolean isAntiDiagonal();

    Double determinant();

    IMatrix<T> inverse();

}

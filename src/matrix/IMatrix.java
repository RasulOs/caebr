package matrix;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;

public interface IMatrix<T extends Number> {

    IMatrix<T> add(T[][] matrix);

    IMatrix<T> subtract(T[][] matrix);

    IMatrix<T> add(IMatrix<T> iMatrix);

    IMatrix<T> subtract(IMatrix<T> iMatrix);

    IMatrix<T> transpose();

    IMatrix<T> multiply(T[][] matrix);

    IMatrix<T> multiply(IMatrix<T> iMatrix);

    IMatrix<T> multiply(T number);

    Double sum();

    Double sum(int column);

    Double sum(int fromColumn, int toColumn);

    Double mean();

    Double mean(int column);

    Double mean(int fromColumn, int toColumn);

    Double max();

    Double max(int column);

    Double max(int fromColumn, int toColumn);

    Double min();

    Double min(int column);

    Double min(int fromColumn, int toColumn);

    Double median();

    Double median(int column);

    Double median(int fromColumn, int toColumn);

    List<Double> mode();

    List<Double> mode(int column);

    List<Double> mode(int fromColumn, int toColumn);

    Double variance();

    Double variance(int column);

    Double variance(int fromColumn, int toColumn);

    Double standardDeviation();

    Double standardDeviation(int column);

    Double standardDeviation(int fromColumn, int toColumn);

    Double range();

    Double range(int column);

    Double range(int fromColumn, int toColumn);

    IMatrix<T> sort(int column);

    IMatrix<T> sort(int fromColumn, int toColumn);

    IMatrix<T> minMaxNormalization(long min, long max, int column);

    IMatrix<T> minMaxNormalization(long min, long max, int fromColumn, int toColumn);

    IMatrix<T> minMaxNormalization();

    IMatrix<T> minMaxNormalization(int column);

    IMatrix<T> minMaxNormalization(int fromColumn, int toColumn);

    IMatrix<T> zScoreStandardization();

    IMatrix<T> zScoreStandardization(int column);

    IMatrix<T> zScoreStandardization(int fromColumn, int toColumn);

    /* A total number of non-zero elements in a vector */
    Integer l0Norm(int column);

    /* Manhattan Distance */
    Double l1Norm(int column);

    /* Euclidean Distance */
    Double l2Norm(int column);

    /* Max absolute value of the vector */
    Double lInfinityNorm(int column);

    int getRowNumber();

    int getColumnNumber();

    boolean isSquare();

    boolean isSymmetric();

    boolean isIdentity();

    boolean isDiagonal();

    boolean isAntiDiagonal();

    Double determinant();

    IMatrix<T> inverse();

    IMatrix<T> dropColumn(int column);

    IMatrix<T> dropColumn();

    IMatrix<T> setColumn(T[] column, int index);

    IMatrix<T> putColumn(T[] column);

    Double[] popColumn(int index);

    Double[] popColumn();

    IMatrix<T> map(Function<Double, Double> function);

    IMatrix<T> map(int column, Function<Double, Double> function);

    IMatrix<T> map(int fromColumn, int toColumn, Function<Double, Double> function);

    Double reduce(int column, BinaryOperator<Double> accumulator);

    Double reduce(int column, Double identity, BinaryOperator<Double> accumulator);

    Double reduce(int fromColumn, int toColumn, BinaryOperator<Double> accumulator);

    Double reduce(int fromColumn, int toColumn, Double identity, BinaryOperator<Double> accumulator);

    void forEach(int column, Consumer<Double> consumer);

    Double[][] toMatrix();

    Double[] toArray(int column);

    Double[] toArray(int fromColumn, int toColumn);

    Integer[][] toIntegerMatrix();

    Integer[] toIntegerArray(int column);

    Integer[] toIntegerArray(int fromColumn, int toColumn);

    Float[][] toFloatMatrix();

    Float[] toFloatArray(int column);

    Float[] toFloatArray(int fromColumn, int toColumn);

    Byte[][] toByteMatrix();

    Byte[] toByteArray(int column);

    Byte[] toByteArray(int fromColumn, int toColumn);

    Short[][] toShortMatrix();

    Short[] toShortArray(int column);

    Short[] toShortArray(int fromColumn, int toColumn);

    Long[][] toLongMatrix();

    Long[] toLongArray(int column);

    Long[] toLongArray(int fromColumn, int toColumn);
}

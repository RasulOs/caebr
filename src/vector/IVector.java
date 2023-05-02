package vector;

import matrix.IMatrix;

import java.util.List;
import java.util.NavigableMap;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface IVector<T extends Number> {

    IVector<T> add(T[] vector);

    IVector<T> add(T[] b, boolean isVertical);

    IVector<T> subtract(T[] vector);

    IVector<T> subtract(T[] b, boolean isVertical);

    IMatrix<Double> multiply(T[] vector);

    IMatrix<Double> multiply(T[] vector, boolean isVertical);

    IMatrix<Double> multiply(IVector<T> iVector);

    IVector<T> multiply(T number);

    IVector<T> put(T number);

    IVector<T> set(T number, int index);

    IVector<T> drop(int index);

    IVector<T> drop(int fromIndex, int toIndex);

    IVector<T> drop();

    Double pop(int index);

    Double pop();

    IVector<T> add(IVector<T> iVector);

    IVector<T> subtract(IVector<T> iVector);

    IVector<T> transpose();

    Double sum();

    Double mean();

    Double max();

    Double min();

    Double median();

    List<Double> mode();

    Double variance();

    Double standardDeviation();

    List<Double> distinct();

    Double range();

    IVector<T> sort();

    IVector<T> reverse();

    IVector<T> shuffle();

    IVector<T> slice(int start, int end);

    IVector<T> slice(int start);

    IVector<T> minMaxNormalization(long min, long max);

    IVector<T> minMaxNormalization();

    IVector<T> zScoreStandardization();

    /* A total number of non-zero elements in a vector */
    Integer l0Norm();

    /* Manhattan Distance */
    Double l1Norm();

   /* Euclidean Distance */
    Double l2Norm();

    /* Max absolute value of the vector */
    Double lInfinityNorm();

    IVector<T> map(Function<Double, Double> function);

    IVector<T> filter(Predicate<Double> predicate);

    Double reduce(BinaryOperator<Double> accumulator);

    Double reduce(Double identity, BinaryOperator<Double> accumulator);

    void forEach(Consumer<Double> consumer);

    int getRowNumber();

    int getColumnNumber();

    boolean isVertical();

    Integer[] toIntegerArray();

    Long[] toLongArray();

    Short[] toShortArray();

    Byte[] toByteArray();

    Double[] toArray();

    Float[] toFloatArray();
}
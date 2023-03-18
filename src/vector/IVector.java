package vector;

import matrix.IMatrix;

import java.util.List;

public interface IVector<T extends Number> {

    IVector<T> add(T[] vector);

    IVector<T> add(T[] b, boolean isVertical);

    IVector<T> subtract(T[] vector);

    IVector<T> subtract(T[] b, boolean isVertical);

    IMatrix<T> dot(T[] vector);

    IMatrix<T> dot(T[] vector, boolean isVertical);

    IVector<T> add(IVector<T> iVector);

    IVector<T> subtract(IVector<T> iVector);

    IVector<T> transpose();

    IMatrix<T> dot(IVector<T> iVector);

    Double sum();

    Double mean();

    Double max();

    Double min();

    Double median();

    List<Double> mode();

    Double variance();

    Double standardDeviation();

    Double range();

    IVector<T> sort();

    IVector<T> reverse();

    IVector<T> shuffle();

    IVector<T> slice(int start, int end);

    IVector<T> slice(int start);

    IVector<T> minMaxNormalization(long min, long max);

    IVector<T> minMaxNormalization();

    IVector<T> zScoreStandardization();

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
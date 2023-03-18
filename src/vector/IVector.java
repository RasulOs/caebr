package vector;

import java.util.List;

public interface IVector<T extends Number> {

    IVector<T> add(T[] vector);

    IVector<T> subtract(T[] vector);

    IMatrix<T> dot(T[] vector);


    IVector<T> add(IVector<T> IVector);

    IVector<T> subtract(IVector<T> IVector);

    IVector<T> transpose();

    IMatrix<T> dot(IVector<T> IVector);

    IVector<T> inverse();

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
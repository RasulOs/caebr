package vector;

import java.util.List;

public abstract class IVector<T extends Number> {

    abstract public IVector<T> add(T[] vector);

    abstract public IVector<T> subtract(T[] vector);

    abstract public IVector<T> dot(T[] vector);


    abstract public IVector<T> add(IVector<T> IVector);

    abstract public IVector<T> subtract(IVector<T> IVector);

    abstract public IVector<T> transpose();

    abstract public IVector<T> dot(IVector<T> IVector);

    abstract public IVector<T> inverse();

    abstract public Double sum();

    abstract public Double mean();

    abstract public Double max();

    abstract public Double min();

    abstract public Double median();

    abstract public List<Double> mode();

    abstract public Double variance();

    abstract public Double standardDeviation();

    abstract public Double range();

    abstract public IVector<T> sort();

    abstract public IVector<T> reverse();

    abstract public IVector<T> shuffle();

    abstract public IVector<T> slice(int start, int end);

    abstract public IVector<T> slice(int start);

    abstract public IVector<T> minMaxNormalization(long min, long max);

    abstract public IVector<T> minMaxNormalization();

    abstract public IVector<T> zScoreStandardization();

    abstract public int getRowNumber();

    abstract public int getColumnNumber();

    abstract public boolean isVertical();

    abstract public Integer[] toIntegerArray();

    abstract public Long[] toLongArray();

    abstract public Short[] toShortArray();

    abstract public Byte[] toByteArray();

    abstract public Double[] toArray();

    abstract public Float[] toFloatArray();
}
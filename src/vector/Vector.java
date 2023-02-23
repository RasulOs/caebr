package vector;

public abstract class Vector<T extends Number> {

    abstract Vector<T> add(T[] vector);

    abstract Vector<T> subtract(T[] vector);

    abstract Vector<T> dot(T[] vector);


    abstract Vector<T> add(Vector<T> vector);

    abstract Vector<T> subtract(Vector<T> vector);

    abstract Vector<T> transpose();

    abstract Vector<T> dot(Vector<T> vector);

    abstract Vector<T> inverse();
}

package matrix;

public interface IMatrix<T extends Number> {

    IMatrix<T> add(T[][] matrix);

    IMatrix<T> subtract(T[][] matrix);

    IMatrix<T> dot(T[][] matrix);

    IMatrix<T> add(IMatrix<T> iMatrix);

    IMatrix<T> subtract(IMatrix<T> iMatrix);

    IMatrix<T> transpose();

    IMatrix<T> dot(IMatrix<T> iMatrix);
}

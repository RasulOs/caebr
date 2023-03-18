package matrix;

import java.util.List;

public class BasicMatrix<T extends Number> implements IMatrix<T> {

    private Double[][] currentMatrix;

    private int rowNumber;
    private int columnNumber;

    public BasicMatrix(T[][] matrix) {

        if (matrix == null)
            throw new IllegalArgumentException("Matrix cannot be null");

        if (isMatrixEmpty(matrix))
            throw new IllegalArgumentException("Matrix cannot be empty");

        this.currentMatrix = transformToDoubleMatrix(matrix);

        if (isMatrixJagged(this.currentMatrix))
            throw new IllegalArgumentException("Matrix cannot be jagged");


        this.rowNumber = matrix.length;
        this.columnNumber = matrix[0].length;
    }

    private Double[][] transformToDoubleMatrix(T[][] matrix) {
        Double[][] doubleMatrix = new Double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                doubleMatrix[i][j] = matrix[i][j].doubleValue();
            }
        }
        return doubleMatrix;
    }

    private boolean isMatrixEmpty(T[][] matrix) {
        return matrix.length == 0 || matrix[0].length == 0;
    }

    private boolean isMatrixJagged(Double[][] matrix) {
        int columns = matrix[0].length;

        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length != columns)
                return true;
        }

        return false;
    }

    private void checkMatrixDimensions(IMatrix<T> iMatrix) {
        if (this.rowNumber != iMatrix.getRowNumber() || this.columnNumber != iMatrix.getColumnNumber())
            throw new IllegalArgumentException("Matrix dimensions must be equal");
    }

    private void checkMatrixDimensions(T[][] matrix) {
        if (this.rowNumber != matrix.length || this.columnNumber != matrix[0].length)
            throw new IllegalArgumentException("Matrix dimensions must be equal");
    }

    @Override
    public IMatrix<T> add(T[][] matrix) {
        return null;
    }

    @Override
    public IMatrix<T> subtract(T[][] matrix) {
        return null;
    }

    @Override
    public IMatrix<T> dot(T[][] matrix) {
        return null;
    }

    @Override
    public IMatrix<T> add(IMatrix<T> iMatrix) {
        return null;
    }

    @Override
    public IMatrix<T> subtract(IMatrix<T> iMatrix) {
        return null;
    }

    @Override
    public IMatrix<T> transpose() {
        return null;
    }

    @Override
    public IMatrix<T> dot(IMatrix<T> iMatrix) {
        return null;
    }

    @Override
    public Double sum() {
        return null;
    }

    @Override
    public Double mean() {
        return null;
    }

    @Override
    public Double max() {
        return null;
    }

    @Override
    public Double min() {
        return null;
    }

    @Override
    public Double median() {
        return null;
    }

    @Override
    public List<Double> mode() {
        return null;
    }

    @Override
    public Double variance() {
        return null;
    }

    @Override
    public Double standardDeviation() {
        return null;
    }

    @Override
    public Double range() {
        return null;
    }

    @Override
    public IMatrix<T> sort() {
        return null;
    }

    @Override
    public IMatrix<T> minMaxNormalization(long min, long max) {
        return null;
    }

    @Override
    public IMatrix<T> minMaxNormalization() {
        return null;
    }

    @Override
    public IMatrix<T> zScoreStandardization() {
        return null;
    }

    @Override
    public int getRowNumber() {
        return 0;
    }

    @Override
    public int getColumnNumber() {
        return 0;
    }

    @Override
    public boolean isSquare() {
        return false;
    }

    @Override
    public boolean isSymmetric() {
        return false;
    }

    @Override
    public boolean isIdentity() {
        return false;
    }

    @Override
    public boolean isDiagonal() {
        return false;
    }

    @Override
    public boolean isAntiDiagonal() {
        return false;
    }

    @Override
    public Double determinant() {
        return null;
    }

    @Override
    public IMatrix<T> inverse() {
        return null;
    }

    private String content(Double[][] matrix) {
        StringBuilder sb = new StringBuilder();
        int rows = matrix.length;
        int columns = matrix[0].length;

        sb.append(String.format("Matrix has %d rows and %d columns. Matrix:\n[\n", rows, columns));

        for (int i = 0; i < matrix.length; i++) {
            sb.append("[");
            for (int j = 0; j < matrix[0].length; j++) {
                sb.append(matrix[i][j]);

                if (j < matrix[0].length - 1)
                    sb.append(", ");
            }
            sb.append("]");
            sb.append("\n");
        }
        sb.append("]");
        return sb.toString();
    }
}

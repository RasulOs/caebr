package matrix;

import java.util.Arrays;
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

    private boolean isMatrixEmpty(IMatrix<T> iMatrix) {
        return iMatrix.getRowNumber() == 0 || iMatrix.getColumnNumber() == 0;
    }

    private boolean isMatrixJagged(Double[][] matrix) {
        int columns = matrix[0].length;

        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length != columns)
                return true;
        }

        return false;
    }

    private boolean isMatrixJagged(IMatrix<T> iMatrix) {
        return isMatrixJagged(iMatrix.toMatrix());
    }

    private void checkMatrix(IMatrix<T> iMatrix) {
        if (iMatrix == null)
            throw new IllegalArgumentException("Matrix cannot be null");

        if (isMatrixEmpty(iMatrix))
            throw new IllegalArgumentException("Matrix cannot be empty");

        if (isMatrixJagged(iMatrix))
            throw new IllegalArgumentException("Matrix cannot be jagged");
    }

    private void checkMatrix(T[][] matrix) {
        if (matrix == null)
            throw new IllegalArgumentException("Matrix cannot be null");

        if (isMatrixEmpty(matrix))
            throw new IllegalArgumentException("Matrix cannot be empty");

        if (isMatrixJagged(transformToDoubleMatrix(matrix)))
            throw new IllegalArgumentException("Matrix cannot be jagged");
    }

    private void checkMatrixDimensionsForSymmetricity(IMatrix<T> iMatrix) {
        if (this.rowNumber != iMatrix.getRowNumber() || this.columnNumber != iMatrix.getColumnNumber())
            throw new IllegalArgumentException("Matrix dimensions must be equal");
    }

    private void checkMatrixDimensionsForSymmetricity(T[][] matrix) {
        if (this.rowNumber != matrix.length || this.columnNumber != matrix[0].length)
            throw new IllegalArgumentException("Matrix dimensions must be equal");
    }

    private void checkMatrixDimensionsForMultiplication(IMatrix<T> iMatrix) {
        if (this.columnNumber != iMatrix.getRowNumber())
            throw new IllegalArgumentException(String
                    .format("Current matrix column number must be equal to the given matrix row number. " +
                            "Current matrix column number: %d, given matrix row number: %d",
                            this.columnNumber, iMatrix.getRowNumber()));
    }

    private void checkMatrixDimensionsForMultiplication(T[][] matrix) {
        if (this.columnNumber != matrix.length)
            throw new IllegalArgumentException(String
                    .format("Current matrix column number must be equal to the given matrix row number. " +
                            "Current matrix column number: %d, given matrix row number: %d",
                            this.columnNumber, matrix.length));
    }

    @Override
    public IMatrix<T> add(T[][] matrix) {

        checkMatrix(matrix);

        checkMatrixDimensionsForSymmetricity(matrix);

        Double[][] result = new Double[this.rowNumber][this.columnNumber];

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = 0; j < this.columnNumber; j++) {
                result[i][j] = this.currentMatrix[i][j] + matrix[i][j].doubleValue();
            }
        }

        currentMatrix = result;

        return this;
    }

    @Override
    public IMatrix<T> subtract(T[][] matrix) {

        checkMatrix(matrix);

        checkMatrixDimensionsForSymmetricity(matrix);

        Double[][] result = new Double[this.rowNumber][this.columnNumber];

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = 0; j < this.columnNumber; j++) {
                result[i][j] = this.currentMatrix[i][j] - matrix[i][j].doubleValue();
            }
        }

        currentMatrix = result;

        return this;
    }

    @Override
    public IMatrix<T> dot(T[][] matrix) {

        checkMatrix(matrix);

        checkMatrixDimensionsForMultiplication(matrix);

        Double[][] resultMatrix = new Double[this.rowNumber][matrix[0].length];

        double sum = 0.0;

        for (int i = 0; i < currentMatrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                for (int k = 0; k < currentMatrix[0].length; k++) {
                    sum += currentMatrix[i][k] * matrix[k][j].doubleValue();
                }

                resultMatrix[i][j] = sum;
                sum = 0.0;
            }
        }

        currentMatrix = resultMatrix;

        return this;
    }

    @Override
    public IMatrix<T> add(IMatrix<T> iMatrix) {
        add((T[][]) iMatrix.toMatrix());

        return this;
    }

    @Override
    public IMatrix<T> subtract(IMatrix<T> iMatrix) {
        subtract((T[][]) iMatrix.toMatrix());

        return this;
    }

    @Override
    public IMatrix<T> transpose() {
        // TODO
        return null;
    }

    @Override
    public IMatrix<T> dot(IMatrix<T> iMatrix) {
        dot((T[][]) iMatrix.toMatrix());

        return this;
    }

    @Override
    public Double sum() {
        // TODO
        return null;
    }

    @Override
    public Double mean() {

        // TODO
        return null;
    }

    @Override
    public Double max() {

        // TODO
        return null;
    }

    @Override
    public Double min() {
        // TODO
        return null;
    }

    @Override
    public Double median() {
        // TODO
        return null;
    }

    @Override
    public List<Double> mode() {
        // TODO
        return null;
    }

    @Override
    public Double variance() {
        // TODO
        return null;
    }

    @Override
    public Double standardDeviation() {

        // TODO
        return null;
    }

    @Override
    public Double range() {
        // TODO
        return null;
    }

    @Override
    public IMatrix<T> sort() {
        // TODO
        return null;
    }

    @Override
    public IMatrix<T> minMaxNormalization(long min, long max) {
        // TODO
        return null;
    }

    @Override
    public IMatrix<T> minMaxNormalization() {
        // TODO
        return null;
    }

    @Override
    public IMatrix<T> zScoreStandardization() {
        // TODO
        return null;
    }

    @Override
    public int getRowNumber() {
        return this.rowNumber;
    }

    @Override
    public int getColumnNumber() {
        return this.columnNumber;
    }

    @Override
    public boolean isSquare() {

        return this.rowNumber == this.columnNumber;
    }

    @Override
    public boolean isSymmetric() {

        // TODO
        return false;
    }

    @Override
    public boolean isIdentity() {

        // TODO
        return false;
    }

    @Override
    public boolean isDiagonal() {
        // TODO
        return false;
    }

    @Override
    public boolean isAntiDiagonal() {
        // TODO
        return false;
    }

    @Override
    public Double determinant() {
        // TODO
        return null;
    }

    @Override
    public IMatrix<T> inverse() {
        // TODO
        return null;
    }

    private String content() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Matrix has %d rows and %d columns. Matrix:\n[\n",
                this.rowNumber, this.columnNumber));

        for (int i = 0; i < currentMatrix.length; i++) {
            sb.append("[");
            for (int j = 0; j < currentMatrix[0].length; j++) {
                sb.append(currentMatrix[i][j]);

                if (j < currentMatrix[0].length - 1)
                    sb.append(", ");
            }
            sb.append("]");
            sb.append("\n");
        }

        sb.append("]");

        return sb.toString();
    }

    @Override
    public Double[][] toMatrix() {

        return this.currentMatrix;
    }

    @Override
    public Integer[][] toIntegerMatrix() {

        Integer[][] result = new Integer[this.rowNumber][this.columnNumber];

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = 0; j < this.columnNumber; j++) {
                result[i][j] = this.currentMatrix[i][j].intValue();
            }
        }

        return result;
    }

    @Override
    public Float[][] toFloatMatrix() {

        Float[][] result = new Float[this.rowNumber][this.columnNumber];

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = 0; j < this.columnNumber; j++) {
                result[i][j] = this.currentMatrix[i][j].floatValue();
            }
        }

        return result;
    }

    @Override
    public Byte[][] toByteMatrix() {

        Byte[][] result = new Byte[this.rowNumber][this.columnNumber];

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = 0; j < this.columnNumber; j++) {
                result[i][j] = this.currentMatrix[i][j].byteValue();
            }
        }

        return result;
    }

    @Override
    public Short[][] toShortMatrix() {

        Short[][] result = new Short[this.rowNumber][this.columnNumber];

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = 0; j < this.columnNumber; j++) {
                result[i][j] = this.currentMatrix[i][j].shortValue();
            }
        }

        return result;
    }

    @Override
    public Long[][] toLongMatrix() {

        Long[][] result = new Long[this.rowNumber][this.columnNumber];

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = 0; j < this.columnNumber; j++) {
                result[i][j] = this.currentMatrix[i][j].longValue();
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return "BasicMatrix {\n" +
                content() + "\n" +
                '}';
    }
}
